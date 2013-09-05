package edu.ncku.eddy;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.util.TestOutput;

public class Display extends Canvas {

	private static final int LEFT = 68;
	private static final int TOP = 83;

	private int line;
	private int col;

	public Display(GameEngine gameEngine) {
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

		// initialize
		drawBackground(g.create());

		// if playing
		line = 0;
		if (Launcher.gameEngine.isGameRunning()) {
			// draw blocks
			for (Block[] blockline : Launcher.gameEngine.GetField().getblocks()) {
				col = 0;
				for (Block block : blockline) {
					int positionX = LEFT + col * 16;
					int positionY = TOP + 304 - line * 16;

					if (line < 20) {
						if (block.getBlockType() != BlockType.None) {
							drawBlock(g.create(), block.getBlockType(), positionX, positionY, true);
						}
					}
					col++;
				}
				line++;
			}

			// draw piece
			Piece currentPiece = Launcher.gameEngine.getCurrentPiece();
			for (BlockMovingPosition blockpPosition : currentPiece.getBlocks()) {
				if (blockpPosition.line < 20) {
					int positionX = LEFT + blockpPosition.col * 16;
					int positionY = TOP + 304 - blockpPosition.line * 16;

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
					TestOutput.sysout(positionX + "," + positionY);
				}
			}

		}

	}

	private void drawBlock(Graphics g, BlockType type, int x, int y, boolean isLocked) {
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

			// 透明度，尚未解決if
			// (isLocked)g2d.setComposite(AlphaComposite.getInstance(0, 0.8f));

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
