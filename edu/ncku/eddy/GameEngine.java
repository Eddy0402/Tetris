package edu.ncku.eddy;

import javax.swing.JFrame;

import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;

public class GameEngine {

	private Field gameField;
	private Piece currentPiece;
		
	private Thread gameThread;
	
	public GameEngine(Field gameField){
		this.gameField = gameField;
	}
	
	public void start(){
		gameField.reset();		
		
		gameThread = new GameThread(Start.mainFrame,this);
		gameThread.run();
	}
	
	public void stop(){
		
		if(gameThread != null && gameThread.isAlive()){
			gameThread.interrupt();
		}		
	}
	
	public void pause(){
		//TODO:¼È½w
	}
	
	public class GameThread extends Thread{
		
		private JFrame window;
		private GameEngine targetEngine;
		Controller keyController;
		
		public GameThread(JFrame window,GameEngine targetEngine){
			this.window = window;
			this.targetEngine = targetEngine;
		}
		
		@Override
		public void run() {
			keyController = new Controller(window, targetEngine);
			keyController.startListener();
		}

		@Override
		public void interrupt() {
			super.interrupt();
			keyController.stopListener();
		}		
	}
}
