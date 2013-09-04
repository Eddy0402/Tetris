package edu.ncku.eddy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Launcher {

	private static final int PREFERRED_HEIGHT = 800;
	private static final int PREFERRED_WEIGHT = 600;
	
	public static Frame mainFrame;
	public static Controller keyController;
	public static GameEngine gameEngine;
	public static Display gameDisplay;
	
	public static void main(String[] args) {
		
		//¹CÀ¸µøµ¡
		mainFrame = new Frame();
		Dimension preferredSize = new Dimension(PREFERRED_HEIGHT, PREFERRED_WEIGHT);
		mainFrame.setSize(preferredSize);
		mainFrame.setTitle("Tetris");
		
		mainFrame.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
			     System.exit(0);
			    }
		});
		
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setBackground(Color.black);
		
		gameEngine = new GameEngine();
		
		gameDisplay = new Display(gameEngine);
		mainFrame.add(gameDisplay);
		
		keyController = new Controller(Launcher.mainFrame,gameEngine );
		keyController.startListener();
				
		/*
		GUI guiManager = new GUI(mainFrame);
		guiManager.initGUI();
		*/

	}


}
