package edu.ncku.eddy;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import Randomizer.Randomizer;
import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.game.component.Piece.RotationMethod;
import edu.ncku.eddy.game.component.Piece.Type;

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
		int seed = 155165516;
		randomizer = new Randomizer(seed);
		currentPiece = randomizer.getNewPiece();
				
		gameThread = new GameDisplayThread(Start.mainFrame,this);
		gameRunning=true;
		gameThread.run();
		Controller keyController = new Controller(Start.mainFrame, this);
		keyController.startListener();
		
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
		ArrayList<BlockMovingPosition> BlockMovingPositions = currentPiece.getBlocks();
		for (BlockMovingPosition blockMovingPosition : BlockMovingPositions) {
			Type pieceType = currentPiece.getType();
			BlockType blockType;
			switch (pieceType) {
			case I:
				blockType = BlockType.I;
				break;
			case J:
				blockType = BlockType.J;
				break;
			case L:
				blockType = BlockType.L;
				break;				
			case O:
				blockType = BlockType.O;
				break;
			case S:
				blockType = BlockType.S;
				break;
			case T:
				blockType = BlockType.T;
				break;
			case Z:
				blockType = BlockType.Z;
				break;
			default:
				blockType = BlockType.None;
				break;
			}
			Block[][] blocks = gameField.getblocks();
			
			blocks[blockMovingPosition.X][blockMovingPosition.Y] = new Block(blockType);
		}
		currentPiece = randomizer.getNewPiece();
	}
	
	public Field GetField(){
		return this.gameField;
	}
	
	
	
	public class GameDisplayThread extends Thread{
		
		private JFrame window;
		private GameEngine targetEngine;
		private GameAreaDisplay display;		
		
		public GameDisplayThread(JFrame window,GameEngine targetEngine){
			this.window = window;
			this.targetEngine = targetEngine;
			this.display = new GameAreaDisplay(targetEngine);
		}
		
		@Override
		public void run() {
			
			
			do{
				
				if (targetEngine.shouldRedraw){
					this.display.draw();
					targetEngine.shouldRedraw = false;
				}
				targetEngine.gameRunning=false;
			}	while (targetEngine.gameRunning) ;
		}

		@Override
		public void interrupt() {
			super.interrupt();
			keyController.stopListener();
		}		
		
		public void tick(){
			long start = System.nanoTime();
			
		}
	}
}
