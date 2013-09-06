package edu.ncku.eddy;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.util.Config;

public class Display extends Canvas {

	private static final int LEFT = 68;
	private static final int TOP = 83;

	private int line;
	private int col;

	private GameEngine gameEngine;

	public Display(GameEngine gameEngine) {
		this.gameEngine = gameEngine;

		setSize(320, 480);
	}

	public void update() {

		Graphics offgc;
		Image offscreen = null;
		Dimension d = getSize();

		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();

		paint(offgc);

		this.getGraphics().drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		// Background
		drawBackground(g.create());

		// if playing
		if (gameEngine.isGameRunning()) {

			// draw blocks

			line = 0;
			for (Block[] blockline : gameEngine.GetField().getblocks()) {
				col = 0;
				for (Block block : blockline) {
					int positionX = LEFT + col * 16;
					int positionY = TOP + 304 - line * 16;

					if (line < 20) {
						if (block.getBlockType() != BlockType.None) {
							drawBlock(g.create(), block.getBlockType(), positionX, positionY, 1);
						}
					}
					col++;
				}
				line++;
			}

			// draw ghost
			if (Config.enableGhost) {
				Piece currentGhostPiece = gameEngine.getCurrentPiece().getGhost();
				for (BlockMovingPosition blockpPosition : currentGhostPiece.getBlocks()) {
					if (blockpPosition.line < 20) {
						int positionX = LEFT + blockpPosition.col * 16;
						int positionY = TOP + 304 - blockpPosition.line * 16;

						BlockType type = convertToBlockType(currentGhostPiece.getType());

						drawBlock(g.create(), type, positionX, positionY, .3f);
					}
				}
			}

			// draw piece
			Piece currentPiece = gameEngine.getCurrentPiece();
			for (BlockMovingPosition blockpPosition : currentPiece.getBlocks()) {
				if (blockpPosition.line < 20) {
					int positionX = LEFT + blockpPosition.col * 16;
					int positionY = TOP + 304 - blockpPosition.line * 16;

					BlockType type = convertToBlockType(currentPiece.getType());

					drawBlock(g.create(), type, positionX, positionY, .8f);
				}
			}

			// draw hold
			Piece holdPiece = gameEngine.getHoldPiece();
			if (holdPiece != null) {
				for (BlockMovingPosition blockpPosition : holdPiece.getBlocks()) {
					int positionX = LEFT - 110 + blockpPosition.col * 16;
					int positionY = TOP + 350 - blockpPosition.line * 16;

					BlockType type = convertToBlockType(holdPiece.getType());

					drawBlock(g.create(), type, positionX, positionY, .8f);
				}

			}

			// draw nextPieces
			int nextIndex = 0;
			for (Piece nextPiece : gameEngine.getRandomizer().getNextPieces()) {
				for (BlockMovingPosition blockpPosition : nextPiece.getBlocks()) {
					int positionX = LEFT + 190 + blockpPosition.col * 16;
					int positionY = TOP + 35 - blockpPosition.line * 16
							+ nextIndex * 55;

					BlockType type = convertToBlockType(nextPiece.getType());

					// °¾²¾
					if (type != BlockType.O && type != BlockType.I) {
						positionX = positionX + 8;
					}

					if (type == BlockType.I) {
						positionY = positionY - 8;
					}

					drawBlock(g.create(), type, positionX, positionY, .8f);
				}
				nextIndex++;
			}

		}

		// Hint
		if (gameEngine.gameReady || gameEngine.gameGo || gameEngine.gameStop
				|| gameEngine.gameOver) {
			Graphics2D g2d = (Graphics2D) g.create();

			if (gameEngine.gameReady || gameEngine.gameGo) {
				int nextIndex = 0;
				for (Piece nextPiece : gameEngine.getRandomizer().getNextPieces()) {
					for (BlockMovingPosition blockpPosition : nextPiece.getBlocks()) {
						int positionX = LEFT + 190 + blockpPosition.col * 16;
						int positionY = TOP + 35 - blockpPosition.line * 16
								+ nextIndex * 55;

						BlockType type = convertToBlockType(nextPiece.getType());

						// °¾²¾
						if (type != BlockType.O && type != BlockType.I) {
							positionX = positionX + 8;
						}

						if (type == BlockType.I) {
							positionY = positionY - 8;
						}

						drawBlock(g.create(), type, positionX, positionY, .8f);
					}
					nextIndex++;
				}
			}

			if (gameEngine.gameReady) {
				g2d.translate(110, 250);
				g2d.setColor(Color.gray);
				g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
				g2d.drawString("Ready?", 0, 0);
			}

			if (gameEngine.gameGo) {
				g2d.translate(125, 250);
				g2d.setColor(Color.gray);
				g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
				g2d.drawString("Go!", 0, 0);
			}

			if (gameEngine.gameStop) {
				g2d.translate(75, 250);
				g2d.setColor(Color.white);
				g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
				g2d.drawString("Stopped. press Enter to restart.", 0, 0);
			}

			if (gameEngine.gameOver) {
				g2d.translate(75, 250);
				g2d.setColor(Color.white);
				g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
				g2d.drawString("Game Over! press Enter to restart.", 0, 0);
			}
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

			Image bgImage = null;
			try {
				bgImage = ImageIO.read(new File("res/" + type.ordinal()
						+ ".png"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
			g2d.setComposite(alpha);

			g2d.drawImage(bgImage, null, null);
		}

	}

	private void drawBackground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.translate(0, 0);
		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("res/back0.png"));
		} catch (Exception ex) {
		}

		g2d.drawImage(bgImage, null, null);

		Graphics2D g2d2 = (Graphics2D) g.create();

		g2d2.translate(LEFT, TOP);

		Image fieldImage = null;
		try {
			fieldImage = ImageIO.read(new File("res/bg.png"));
		} catch (Exception ex) {
		}

		g2d2.drawImage(fieldImage, null, null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9020789431082872086L;

}
