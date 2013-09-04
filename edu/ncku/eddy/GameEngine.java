package edu.ncku.eddy;

import java.util.ArrayList;

import javax.swing.JFrame;

import edu.ncku.eddy.game.Randomizer.Randomizer;
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
	
	private Controller keyController;
	
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
		gameThread.start();
		
		keyController = new Controller(Start.mainFrame, this);
		keyController.startListener();
	}
	
	public void stop(){
		
		if(gameThread != null && gameThread.isAlive()){
			gameThread.interrupt();
		}	
		gameRunning=false;
		keyController.stopListener();
	}
	
	public void pause(){
		//TODO:暫緩
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
	
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	public class GameDisplayThread extends Thread{
		
		private JFrame window;
		private GameEngine targetEngine;
		private GameAreaDisplay display;
		private long lastTick;
		
		private int tickCount = 0;
		private long startTime;
		
		public GameDisplayThread(JFrame window,GameEngine targetEngine){
			this.window = window;
			this.targetEngine = targetEngine;
			this.display = new GameAreaDisplay(targetEngine);
		}
		
		@Override
		public void run() {
			
			startTime = System.currentTimeMillis();
						
			do{				
				tick();
				if (targetEngine.shouldRedraw){
					this.display.draw();
					targetEngine.shouldRedraw = false;
				}
			}	while (targetEngine.isGameRunning()) ;
		}

		@Override
		public void interrupt() {
			super.interrupt();

		}		
		
		
		
		public void tick(){
			tickCount++;
			//TODO:隨時間下降/lock的動作
			//若有改變：
			//targetEngine.shouldRedraw = true;
			
			
			//經過tick總數tickCount
			//經過時間System.currentTimeMillis()-startTime
			
			//100fps
			lastTick = System.currentTimeMillis();
			while (System.currentTimeMillis() - lastTick < 10 ){
				try {
					Thread.sleep(1);
				} catch (Exception e) {	}				
			}					
		}
	}
}
