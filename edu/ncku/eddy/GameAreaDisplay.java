package edu.ncku.eddy;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

import org.omg.CosNaming._BindingIteratorImplBase;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece;

public class GameAreaDisplay {
	private Block[][] blocks;
	private Piece currentPiece;

	private int line;
	private int col;

	public GameAreaDisplay(GameEngine gameEngine) {
		this.blocks = gameEngine.GetField().getblocks();
	}

	public void draw() {
		line = 1;

		for (Block[] blockLine : this.blocks) {
			
			col = 1;
			
			for (Block block : blockLine) {

				int positionX = 100 + col * 20;
				int positionY = 500 - line * 20;

				System.out.println(col + "," + line +": " + positionX + "," + positionY);
				BlockComponent blockComponent = new BlockComponent(block, positionX, positionY);

				Start.mainFrame.add(blockComponent);				
				col++;
			}
			line++;
		}

		Start.mainFrame.repaint();
	}

	public class BlockComponent extends JButton {

		private Color bColor;
		private int x, y;

		public BlockComponent(Block block, int x, int y) {
			super();
			switch (block.getBlockType()) {
			case None:
				bColor = Color.BLACK;
				break;			
			case I:
				bColor = new Color(0, 255, 255);
				break;
			case J:
				bColor = Color.BLUE;
				break;
			case L:
				bColor = Color.ORANGE;
				break;
			case O:
				bColor = Color.YELLOW;
				break;
			case S:
				bColor = Color.GREEN;
				break;
			case T:
				bColor = new Color(153, 0, 255);
				break;
			case Z:
				bColor = Color.RED;
				break;
			case garbage:
				bColor = Color.LIGHT_GRAY;
				break;
			default:
				bColor = Color.BLACK;
				break;
			}

			this.x = x;
			this.y = y;

			this.setBounds(x, y, 20, 20);
			this.setBackground(bColor);
		}


		private static final long serialVersionUID = 1L;

	}

}
