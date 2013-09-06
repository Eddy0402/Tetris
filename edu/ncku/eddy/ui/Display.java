package edu.ncku.eddy.ui;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.io.File;

import javax.imageio.ImageIO;

import edu.ncku.eddy.component.Block;
import edu.ncku.eddy.component.Piece;
import edu.ncku.eddy.component.Block.BlockType;
import edu.ncku.eddy.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.game.GameEngine;
import edu.ncku.eddy.game.GameEngine.GameState;
import edu.ncku.eddy.util.Config;

public class Display extends Canvas {

	private static final int LEFT = 68;
	private static final int TOP = 83;

	private int line;
	private int col;

	private GameEngine gameEngine;

	//buffer image
	private Image offScreenStaticBlock;
	
	//background image
	static Image bgImage;
	static Image fieldImage;
	
	static Image blockImage[] = new Image[9];
		
	static{
		try {
			fieldImage = ImageIO.read(new File("res/bg.png"));
			bgImage = ImageIO.read(new File("res/back0.png"));
			blockImage[0] = ImageIO.read(new File("res/0.png"));
			blockImage[1] = ImageIO.read(new File("res/1.png"));
			blockImage[2] = ImageIO.read(new File("res/2.png"));
			blockImage[3] = ImageIO.read(new File("res/3.png"));
			blockImage[4] = ImageIO.read(new File("res/4.png"));
			blockImage[5] = ImageIO.read(new File("res/5.png"));
			blockImage[6] = ImageIO.read(new File("res/6.png"));
			blockImage[8] = ImageIO.read(new File("res/8.png"));
		} catch (Exception ex) {
		}

	}
	
	public Display(GameEngine gameEngine) {
		this.gameEngine = gameEngine;

		setSize(320, 480);
	}

	public void update() {

		Graphics offgc;
		Dimension d = getSize();

		//緩存區
		Image offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();

		paint(offgc);

		//畫出緩存
		this.getGraphics().drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		// Background
		drawBackground(g.create());

		GameState gameState = gameEngine.getGameState();

		// 方塊半透明度
		float alphaStaticBlock;
		float alphaPieceBlock;
		float alphaGhostBlock;
		switch (gameState) {
		case Stopped:
			alphaStaticBlock = .5f;
			alphaPieceBlock = .3f;
			alphaGhostBlock = 0.2f;
			break;
		case GameOver:
			alphaStaticBlock = .5f;
			alphaPieceBlock = .3f;
			alphaGhostBlock = 0.2f;
			break;
		default:
			alphaStaticBlock = 1f;
			alphaPieceBlock = .8f;
			alphaGhostBlock = 0.3f;
			break;
		}

		// 若遊戲被執行過，且非Ready?Go階段，繪製場內固定方塊
		if (gameState != GameState.Initial && gameState != GameState.Ready
				&& gameState != GameState.Go) {

			// 若未更動場地，使用緩存Graphic繪製，增加效能
			if (offScreenStaticBlock == null || gameEngine.shouldRepaintField) {
				Dimension d = getSize();
				Graphics offgcStaticBlock = g.create();
				offScreenStaticBlock = ((Graphics2D) offgcStaticBlock).getDeviceConfiguration().createCompatibleImage(d.width, d.height, Transparency.TRANSLUCENT);
				offgcStaticBlock.dispose();
				offgcStaticBlock = offScreenStaticBlock.getGraphics();
				
				line = 0;
				for (Block[] blockline : gameEngine.GetField().getblocks()) {
					col = 0;
					for (Block block : blockline) {
						int positionX = LEFT + col * 16;
						int positionY = TOP + 304 - line * 16;

						if (line < 20) {
							if (block.getBlockType() != BlockType.None) {
								drawBlock(offgcStaticBlock.create(), block.getBlockType(), positionX, positionY, alphaStaticBlock);
							}
						}
						col++;
					}
					line++;
				}
				gameEngine.shouldRepaintField = false;
			}
			g.drawImage(offScreenStaticBlock, 0, 0, this);
		}

		// 繪製Piece
		Piece currentPiece = gameEngine.getCurrentPiece();
		if (currentPiece != null) {
			for (BlockMovingPosition blockpPosition : currentPiece.getBlocks()) {
				if (blockpPosition.line < 20) {
					int positionX = LEFT + blockpPosition.col * 16;
					int positionY = TOP + 304 - blockpPosition.line * 16;

					BlockType type = convertToBlockType(currentPiece.getType());

					drawBlock(g.create(), type, positionX, positionY, alphaPieceBlock);
				}
			}
		}

		// 繪製Ghost Piece
		if (currentPiece != null) {
			if (Config.enableGhost) {
				Piece currentGhostPiece = gameEngine.getCurrentPiece().getGhost();
				for (BlockMovingPosition blockpPosition : currentGhostPiece.getBlocks()) {
					if (blockpPosition.line < 20) {
						int positionX = LEFT + blockpPosition.col * 16;
						int positionY = TOP + 304 - blockpPosition.line * 16;

						BlockType type = convertToBlockType(currentGhostPiece.getType());

						drawBlock(g.create(), type, positionX, positionY, alphaGhostBlock);
					}
				}
			}
		}

		// 繪製 Hold Piece
		Piece holdPiece = gameEngine.getHoldPiece();
		if (holdPiece != null) {
			for (BlockMovingPosition blockpPosition : holdPiece.getBlocks()) {
				int positionX = LEFT - 110 + blockpPosition.col * 16;
				int positionY = TOP + 350 - blockpPosition.line * 16;

				BlockType type = convertToBlockType(holdPiece.getType());

				drawBlock(g.create(), type, positionX, positionY, alphaPieceBlock);
			}

		}

		// 繪製Next Pieces

		int nextIndex = 0;
		if (gameState != GameState.Initial) {
			Piece[] nextPieces = gameEngine.getRandomizer().getNextPieces();
			for (Piece nextPiece : nextPieces) {
				for (BlockMovingPosition blockpPosition : nextPiece.getBlocks()) {
					int positionX = LEFT + 190 + blockpPosition.col * 16;
					int positionY = TOP + 35 - blockpPosition.line * 16
							+ nextIndex * 55;

					BlockType type = convertToBlockType(nextPiece.getType());

					// 偏移
					if (type != BlockType.O && type != BlockType.I) {
						positionX = positionX + 8;
					}

					if (type == BlockType.I) {
						positionY = positionY - 8;
					}

					drawBlock(g.create(), type, positionX, positionY, alphaPieceBlock);
				}
				nextIndex++;
			}
		}

		// game hint
		Graphics2D g2d = (Graphics2D) g.create();

		switch (gameState) {
		case Ready:
			g2d.translate(110, 250);
			g2d.setColor(Color.gray);
			g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
			g2d.drawString("Ready?", 0, 0);
		case Go:
			g2d.translate(125, 250);
			g2d.setColor(Color.gray);
			g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
			g2d.drawString("Go!", 0, 0);
		case Stopped:
			g2d.translate(75, 250);
			g2d.setColor(Color.white);
			g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
			g2d.drawString("Stopped. press Enter to restart.", 0, 0);
		case GameOver:
			g2d.translate(75, 250);
			g2d.setColor(Color.white);
			g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
			g2d.drawString("Game Over! press Enter to restart.", 0, 0);
		default:
			break;
		}

	}

	private BlockType convertToBlockType(Piece.Type pieceType) {
		switch (pieceType) {
		case I:
			return BlockType.I;
		case J:
			return BlockType.J;
		case L:
			return BlockType.L;
		case O:
			return BlockType.O;
		case S:
			return BlockType.S;
		case T:
			return BlockType.T;
		case Z:
			return BlockType.Z;
		}
		return null;
	}

	private void drawBlock(Graphics g, BlockType type, int x, int y, float alphaValue) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(x, y);

		if (type != BlockType.None) {

			Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
			g2d.setComposite(alpha);

			g2d.drawImage(blockImage[type.ordinal()], null, null);
		}

	}

	private void drawBackground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.drawImage(bgImage, null, null);

		Graphics2D g2d2 = (Graphics2D) g.create();

		g2d2.translate(LEFT, TOP);

		g2d2.drawImage(fieldImage, null, null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9020789431082872086L;

}
