package edu.ncku.eddy;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;

@Deprecated
public class GameAreaDisplay extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1629244987457004028L;
	private Piece currentPiece;
	private Field field;

	private int line;
	private int col;

	public GameAreaDisplay(GameEngine gameEngine) {
		this.field = gameEngine.GetField();
	}

	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.translate(200, 200);
		g.setColor(Color.black);
		g.fillRect(5, 5, 100, 100);
		g.drawLine(100,100,150,150);
	}
	
	/*
	public void draw() {
		line = 0;

		BlockActionListener blockActionListener = new BlockActionListener(field);

		blockComponents = new BlockComponent[20][10];

		for (Block[] blockLine : this.field.getblocks()) {

			col = 0;

			for (Block block : blockLine) {

				int positionX = 100 + col * 20;
				int positionY = 500 - line * 20;

				
				col++;
			}
			line++;
		}

		if (currentPiece != null) {
			for (BlockMovingPosition blockpPosition : currentPiece.getBlocks()) {
				int positionX = 100 + blockpPosition.X;
				int positionY = 500 - blockpPosition.Y;

				BlockComponent blockComponent = new BlockComponent(currentPiece, positionX, positionY);
				Start.mainFrame.add(blockComponent);
			}
		}

		Start.mainFrame.repaint();
	}
	*/

}