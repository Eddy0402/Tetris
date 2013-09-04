package edu.ncku.eddy;

import java.io.ObjectInputStream.GetField;

import javax.swing.JFrame;

import Randomizer.Randomizer;
import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.RotationMethod;

public class GameEngine {

	private Field gameField;
	private Piece currentPiece;
	private Randomizer randomizer;
		
	private boolean gameRunning;
	private Thread gameThread;
	
	public boolean shouldRedraw=true;
	
	public GameEngine(Field gameField){
		this.gameField = gameField;
	}
	
	public void start(){
		gameField.reset();
		
		
		gameThread = new GameThread(Start.mainFrame,this);
		gameRunning=true;
		gameThread.run();
		
	}
	
	public void stop(){
		
		if(gameThread != null && gameThread.isAlive()){
			gameThread.interrupt();
		}	
		gameRunning=false;
	}
	
	public void pause(){
		//TODO:¼È½w
	}
	
	public void moveLeft(){
		
	}
	
	public void moveRight(){
		
	}
	
	public void rotatePiece(RotationMethod rotationMethod){
		
	}
	
	public void hardDrop(){
		
	}
	
	public void softDrop(){
		
	}
	
	public void drop(){
		
	}
	
	public void lockPiece(){
		Block[] blocksToLock = currentPiece.getBlocks();
		//TODO:copy
		currentPiece = randomizer.getNewPiece();
	}
	
	public Field GetField(){
		return this.gameField;
	}
	
	public class GameThread extends Thread{
		
		private JFrame window;
		private GameEngine targetEngine;
		private GameAreaDisplay display;
		Controller keyController;
		
		public GameThread(JFrame window,GameEngine targetEngine){
			this.window = window;
			this.targetEngine = targetEngine;
			this.display = new GameAreaDisplay(targetEngine);
		}
		
		@Override
		public void run() {
			keyController = new Controller(window, targetEngine);
			keyController.startListener();
			
			while (targetEngine.gameRunning) {
				if (targetEngine.shouldRedraw){
					this.display.draw();
					targetEngine.shouldRedraw = false;
				}
				targetEngine.gameRunning=false;
			}
		}

		@Override
		public void interrupt() {
			super.interrupt();
			keyController.stopListener();
		}		
	}
}
