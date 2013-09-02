package edu.ncku.eddy;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Start {

	private static final int PREFERRED_HEIGHT = 800;
	private static final int PREFERRED_WEIGHT = 600;

	public static void main(String[] args) {
		
		//¹CÀ¸µøµ¡
		JFrame mainFrame = new JFrame();
		Dimension preferredSize = new Dimension(PREFERRED_HEIGHT, PREFERRED_WEIGHT);
		mainFrame.setSize(preferredSize);
		mainFrame.setTitle("Tetris");
				
		GUI guiManagerGui = new GUI(mainFrame);
		guiManagerGui.initGUI();
		
		
		
		
		mainFrame.setVisible(true);
	}

}
