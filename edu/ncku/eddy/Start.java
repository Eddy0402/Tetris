package edu.ncku.eddy;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.ncku.eddy.game.component.Field;

public class Start {

	private static final int PREFERRED_HEIGHT = 800;
	private static final int PREFERRED_WEIGHT = 600;
	
	public static JFrame mainFrame;

	public static void main(String[] args) {
		
		//¹CÀ¸µøµ¡
		mainFrame = new JFrame();
		Dimension preferredSize = new Dimension(PREFERRED_HEIGHT, PREFERRED_WEIGHT);
		mainFrame.setSize(preferredSize);
		mainFrame.setTitle("Tetris");
				
		GUI guiManager = new GUI(mainFrame);
		guiManager.initGUI();
		
		Field gameField = new Field();
		GameEngine gameEngine = new GameEngine(gameField);
		
		gameEngine.start();
		
		
		mainFrame.setVisible(true);
	}

}
