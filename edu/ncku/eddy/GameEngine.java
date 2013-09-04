package edu.ncku.eddy;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.game.component.Piece.RotationMethod;
import edu.ncku.eddy.game.component.Piece.Type;
import edu.ncku.eddy.util.Randomizer;

public class GameEngine {

	private Field gameField;
	private Piece currentPiece;
	private Randomizer randomizer;
		
	private boolean gameRunning=false;
	
	private Thread gameThread;
	
	public boolean shouldRedraw=true;
	
	private Controller keyController;
	
	public GameEngine(){
		this.gameField =  new Field();
	}
	
	public void startGame(){
		gameField.reset();
		
		//TODO:測試期間先用固定數測試
		long seed = 155165516;
		
		randomizer = new Randomizer(seed);
		currentPiece = randomizer.getNewPiece();
				
		gameThread = new GameDisplayThread(this);
		gameRunning=true;
		gameThread.start();
						
	}
	
	public void stopGame(){
		
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
		currentPiece.moveLeft();
	}
	
	public void moveRight(){
		currentPiece.moveRight();
	}
	
	public void rotatePiece(RotationMethod rotationMethod){
		currentPiece.rotatePiece(rotationMethod);
	}
	
	public void hardDrop(){
		do{
		currentPiece.moveDown();
		}while(!currentPiece.moveDown());
	}
	
	public void drop(){
		currentPiece.moveDown();
	}
	
	public void lockPiece(){
		for (BlockMovingPosition blockMovingPosition : currentPiece.getBlocks()) {
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
			
			blocks[blockMovingPosition.line][blockMovingPosition.col] = new Block(blockType);
		}
		currentPiece = randomizer.getNewPiece();
	}
	
	public Field GetField(){
		return this.gameField;
	}
	
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	public Piece getCurrentPiece(){
		return currentPiece;
	}
	
	public class GameDisplayThread extends Thread{
		
		private GameEngine targetEngine;
		private Display display;
		private long lastTick;
		
		//private int tickCount = 0;
		//private long startTime;
		
		public GameDisplayThread(GameEngine targetEngine){
			this.targetEngine = targetEngine;
			this.display = Launcher.gameDisplay;
			
			shouldRedraw = true;
		}
		
		@Override
		public void run() {
			
			//startTime = System.currentTimeMillis();
						
			do{				
				tick();
				if (targetEngine.shouldRedraw){
					this.display.repaint();
					targetEngine.shouldRedraw = false;
				}
			}while (targetEngine.isGameRunning()) ;
			
		}

		@Override
		public void interrupt() {
			super.interrupt();

		}		
		
		
		
		public void tick(){
			//tickCount++;
						
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
