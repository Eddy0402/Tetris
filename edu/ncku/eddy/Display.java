package edu.ncku.eddy;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;

public class Display extends Canvas {

	private int line;
	private int col;

	public Display(GameEngine gameEngine) {
		setSize(800, 600);
	}

	@Override
	public void paint(Graphics g) {

		// initialize
		g.setColor(Color.black);
		g.drawRect(0, 0, 800, 600);
		drawBackground(g.create());

		// if playing
		line = 0;
		if (Launcher.gameEngine.isGameRunning()) {
			// draw blocks
			for (Block[] blockline : Launcher.gameEngine.GetField().getblocks()) {
				col = 0;
				for (Block block : blockline) {
					int positionX = 200 + col * 16;
					int positionY = 520 - line * 16;

					if (block.getBlockType() != BlockType.None) {
						drawBlock(g.create(), block.getBlockType(), positionX, positionY, true);
					}
					col++;
				}
				line++;
			}

			// draw piece
			Piece currentPiece = Launcher.gameEngine.getCurrentPiece();
			for (BlockMovingPosition blockpPosition : currentPiece.getBlocks()) {
				int positionX = 200 + blockpPosition.col * 16;
				int positionY = 520 - blockpPosition.line * 16;

				BlockType type = null;
				switch (currentPiece.getType()) {
				case I:
					type = BlockType.I;
					break;
				case J:
					type = BlockType.J;
					break;
				case L:
					type = BlockType.L;
					break;
				case O:
					type = BlockType.O;
					break;
				case S:
					type = BlockType.S;
					break;
				case T:
					type = BlockType.T;
					break;
				case Z:
					type = BlockType.Z;
					break;
				default:
					break;
				}

				drawBlock(g.create(), type, positionX, positionY, false);
				System.out.println(positionX + "," +positionY);

			}

			// draw score or other things
		}
	}

	private void drawBlock(Graphics g, BlockType type, int x, int y, boolean isLocked) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.translate(x, y);

		if (type != BlockType.None) {
			
			Image bgImage = null;
			try {
				bgImage = ImageIO.read(new File("res/" + type.ordinal()	+ ".png"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// 透明度，尚未解決if
			// (isLocked)g2d.setComposite(AlphaComposite.getInstance(0, 0.8f));

			g2d.drawImage(bgImage, null, null);			
		}


	}

	private void drawBackground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(200, 200);

		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("res/bg.png"));
		} catch (Exception ex) {
		}

		g2d.drawImage(bgImage, null, null);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -9020789431082872086L;

}
