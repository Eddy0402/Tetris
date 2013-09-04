package edu.ncku.eddy;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.ncku.eddy.game.component.Field;

public class Start {

	private static final int PREFERRED_HEIGHT = 800;
	private static final int PREFERRED_WEIGHT = 600;
	
	public static JFrame mainFrame;

	public static GameEngine gameEngine;
	public static Field gameField;
	
	public static void main(String[] args) {
		
		//¹CÀ¸µøµ¡
		mainFrame = new JFrame();
		Dimension preferredSize = new Dimension(PREFERRED_HEIGHT, PREFERRED_WEIGHT);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(preferredSize);
		mainFrame.setTitle("Tetris");
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
				
		gameField = new Field();
		gameEngine = new GameEngine(gameField);
		
		GUI guiManager = new GUI(mainFrame);
		guiManager.initGUI();
		

	}

}
