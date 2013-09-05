package edu.ncku.eddy;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class Launcher {

	private static final int PREFERRED_HEIGHT = 480;
	private static final int PREFERRED_WEIGHT = 640;
	
	public static Frame mainFrame;
	public static Controller keyController;
	public static GameEngine gameEngine;
	public static Display gameDisplay;
	public static ScoreDisplay scoreDisplay;
	
	public static void main(String[] args) {
		
		//¹CÀ¸µøµ¡
		mainFrame = new Frame();
		Dimension preferredSize = new Dimension(PREFERRED_WEIGHT,PREFERRED_HEIGHT);
		mainFrame.setSize(preferredSize);
		mainFrame.setResizable(false);
		mainFrame.setTitle("Tetris");
		
		mainFrame.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
			     System.exit(0);
			    }
		});
		
		Panel panel = new Panel(null);
		mainFrame.add(panel);

		mainFrame.setVisible(true);
		
		gameEngine = new GameEngine();
		
		gameDisplay = new Display(gameEngine);
		gameDisplay.setBounds(0, 0,320, 480);
		panel.add(gameDisplay);
		
		scoreDisplay = new ScoreDisplay(gameEngine);
		scoreDisplay.setBounds(320, 0,320, 480);
		panel.add(scoreDisplay);
		
	
		keyController = new Controller(Launcher.mainFrame,gameEngine );
		keyController.startListener();
		
		
		
		JOptionPane.showMessageDialog(null, "Press Enter to start.");
				
	}


}
