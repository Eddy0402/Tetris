package edu.ncku.eddy;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Field;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.game.component.Piece.RotationMethod;
import edu.ncku.eddy.game.component.Piece.Type;
import edu.ncku.eddy.util.Randomizer;
import edu.ncku.eddy.util.TestOutput;

public class GameEngine {

	private Field gameField;
	private Piece currentPiece;
	private Randomizer randomizer;

	private boolean gameRunning = false;
	protected boolean shouldRedraw;

	private Thread gameThread;


	public GameEngine() {
		this.gameField = new Field();
	}

	public void startGame() {
		gameField.reset();

		// TODO:測試期間先用固定數測試
		long seed = 155165516;

		randomizer = new Randomizer(seed);
		currentPiece = randomizer.getNewPiece();

		gameThread = new GameDisplayThread(this);
		gameRunning = true;
		gameThread.start();

	}

	public void gameOver(){
		stopGame();
	}
	public void stopGame() {

		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
		}
		gameRunning = false;
		Launcher.keyController.stopListener();
	}

	public void pause() {
		// TODO:暫緩
	}

	public void moveLeft() {
		currentPiece.moveLeft();
	}

	public void moveRight() {
		currentPiece.moveRight();
	}

	public void rotatePiece(RotationMethod rotationMethod) {
		currentPiece.rotatePiece(rotationMethod);
	}

	public void hardDrop() {
		do {
			currentPiece.moveDown();
		} while (!currentPiece.moveDown());
	}

	public void drop() {
		TestOutput.sysout("drop()");
		if(currentPiece.moveDown()){
			shouldRedraw = true;
		}else{
			lockPiece();
		}
	}

	public void lockPiece() {
		for (BlockMovingPosition blockMovingPosition : currentPiece.getBlocks()) {
			if (blockMovingPosition.line > 19){
				gameOver();	
				return;
			}
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

	public Field GetField() {
		return this.gameField;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public Piece getCurrentPiece() {
		return currentPiece;
	}

	public class GameDisplayThread extends Thread {

		private GameEngine targetEngine;
		private Display display;

		private long lastDrop = 0; // 上次drop的tick
		private long tickCount; // 總共跑過幾個tick
		private long startTimeMillis;
		private long lastTimeMillis;

		public GameDisplayThread(GameEngine targetEngine) {
			this.targetEngine = targetEngine;
			this.display = Launcher.gameDisplay;
		}

		@Override
		public void run() {

			tickCount = 0;
			shouldRedraw = true;
			startTimeMillis = System.currentTimeMillis();

			// 遊戲迴圈
			do {
				tick();
				if (targetEngine.shouldRedraw) {
					this.display.repaint();
					targetEngine.shouldRedraw = false;
				}
			} while (targetEngine.isGameRunning());

		}

		@Override
		public void interrupt() {
			super.interrupt();

		}

		public void tick() {
			tickCount++;

			if (tickCount - lastDrop > 10) {
				targetEngine.drop();
				TestOutput.sysout("drop tick");
				lastDrop = tickCount;
			}

			// 若有改變：
			// targetEngine.shouldRedraw = true;

			// 經過tick總數tickCount
			// 經過時間System.currentTimeMillis()-startTime

			// 目標速度為100 ticks / sec
			lastTimeMillis = System.currentTimeMillis();
			while (System.currentTimeMillis() - lastTimeMillis < 10) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
			}
		}
	}
}
