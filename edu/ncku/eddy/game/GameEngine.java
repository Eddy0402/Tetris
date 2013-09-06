package edu.ncku.eddy.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.component.Block;
import edu.ncku.eddy.component.Field;
import edu.ncku.eddy.component.Piece;
import edu.ncku.eddy.component.Block.BlockType;
import edu.ncku.eddy.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.component.Piece.RotationMethod;
import edu.ncku.eddy.component.Piece.Type;
import edu.ncku.eddy.util.TestOutput;

public class GameEngine {

	private Field gameField;
	private Piece currentPiece;
	private Piece holdPiece;
	private Randomizer randomizer;

	public Randomizer getRandomizer() {
		return randomizer;
	}

	// 遊戲狀態
	public enum GameState {
		Initial, Ready, Go, Playing, Stopped, GameOver
	}

	private GameState gameState;

	public GameState getGameState() {
		return gameState;
	}

	private boolean shouldRedraw;
	public boolean shouldRepaintField = true;
	private Thread gameThread;

	// drop失敗兩次則lock
	private int lockCount = 0;

	// 目前方塊
	private int pieceIndex;

	// 統計資料
	private int usedPieceCount;
	private int clearedLine;

	private int holdPieceIndex;

	public int getPieceIndex() {
		return pieceIndex;
	}

	public GameEngine() {
		this.gameField = new Field();

		this.gameState = GameState.Initial;
	}

	public void startGame() {

		gameField.reset();

		// 初始化各數據
		holdPiece = null;
		pieceIndex = 0;
		clearedLine = 0;
		usedPieceCount = 0;
		currentPiece = null;

		// 種子碼
		long seed = new Random().nextLong();
		randomizer = new Randomizer(seed);

		gameThread = new GameDisplayThread(this);
		gameThread.start();

	}

	private void getNewPiece() {
		pieceIndex++;
		currentPiece = randomizer.getNewPiece();
		if (!currentPiece.canAppear()) {
			gameOver();
		}
	}

	public void gameOver() {
		gameState = GameState.GameOver;
		Launcher.gameDisplay.update();
		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
		}
	}

	public void stopGame() {
		gameState = GameState.Stopped;
		Launcher.gameDisplay.update();

		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
		}
	}

	public void pause() {
		// TODO:尚未實作
	}

	public boolean moveLeft() {
		if (currentPiece.moveLeft()){
			shouldRedraw = true;
			return true;
		}
		return false;
	}

	public boolean moveRight() {
		if (currentPiece.moveRight()) {
			shouldRedraw = true;
			return true;
		}
		return false;
	}

	public void rotatePiece(RotationMethod rotationMethod) {
		if (currentPiece.rotatePiece(rotationMethod)) {
			currentPiece.regenerateGhostPiece();
			shouldRedraw = true;
		}
	}

	public void hold() {
		if (holdPiece == null) {
			holdPiece = Piece.createPiece(currentPiece.getType(), this.randomizer.NEW_BLOCK_POSITION_X, this.randomizer.NEW_BLOCK_POSITION_Y);
			getNewPiece();
			holdPieceIndex = pieceIndex;
			shouldRedraw = true;
		} else {

			if (holdPieceIndex != pieceIndex) {
				Piece tempPiece = currentPiece;
				currentPiece = holdPiece;
				currentPiece.regenerateGhostPiece();
				if (!currentPiece.canAppear()) {
					gameOver();
				}
				holdPiece = Piece.createPiece(tempPiece.getType(), this.randomizer.NEW_BLOCK_POSITION_X, this.randomizer.NEW_BLOCK_POSITION_Y);
				shouldRedraw = true;
				holdPieceIndex = pieceIndex;
			}
		}
	}

	public void hardDrop() {
		do {

		} while (currentPiece.moveDown());
		lockPiece();
		shouldRedraw = true;
	}

	public boolean softDrop(){
		if (currentPiece.moveDown()) {
			shouldRedraw = true;
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean drop() {

		if (currentPiece.moveDown()) {
			shouldRedraw = true;
			return true;
		} else {
			lockCount++;
			if (lockCount > 1) {
				lockPiece();
				lockCount = 0;
			}			
			return false;
		}		
	}

	public void moveDown() {
		if (currentPiece.moveDown()) {
			shouldRedraw = true;
		}

	}

	public void lockPiece() {
		shouldRepaintField = true;
		usedPieceCount++;
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
		clearedLine = clearedLine + gameField.checkLineClear();
		getNewPiece();
	}

	public Field GetField() {
		return this.gameField;
	}

	public Piece getCurrentPiece() {
		return currentPiece;
	}

	public Piece getHoldPiece() {
		return holdPiece;
	}

	public class GameDisplayThread extends Thread {

		private GameEngine targetEngine;

		private long lastDrop = 0; // 上次drop的tick
		private long tickCount; // 總共跑過幾個tick
		private long startTimeMillis;
		private long lastTimeMillis;

		private String time40L;
		private String time;

		public GameDisplayThread(GameEngine targetEngine) {
			this.targetEngine = targetEngine;
		}

		@Override
		public void run() {

			time40L = "";
			tickCount = 0;
			shouldRedraw = true;

			// Ready? 階段
			gameState = GameState.Ready;
			Launcher.gameDisplay.update();
			try {
				Thread.sleep(800);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Go! 階段
			gameState = GameState.Go;
			Launcher.gameDisplay.update();
			try {
				Thread.sleep(800);
			} catch (Exception e) {
				e.printStackTrace();
			}

			gameState = GameState.Playing;
			getNewPiece();
			shouldRepaintField = true;
			Launcher.gameDisplay.update();

			startTimeMillis = System.currentTimeMillis();
			// 遊戲迴圈
			do {
				tick();
			} while (gameState == GameState.Playing);

		}

		@Override
		public void interrupt() {
			super.interrupt();

		}

		public void tick() {
			tickCount++;

			// 每0.5秒下降一次
			if (tickCount - lastDrop > 50) {
				targetEngine.drop();
				TestOutput.sysout("drop tick");
				lastDrop = tickCount;
			}

			// 更新遊戲畫面
			if (targetEngine.shouldRedraw) {
				Launcher.gameDisplay.update();
				shouldRedraw = false;
			}

			// 顯示遊戲數據
			SimpleDateFormat df = new SimpleDateFormat("mm:ss.SSS");
			long timeMill = System.currentTimeMillis() - startTimeMillis;
			time = df.format(new Date(timeMill));
			double pps = (float) usedPieceCount / timeMill * 1000;
			double ppm = pps * 60;
			double lpm = (float) clearedLine / timeMill * 60000;
			if (clearedLine > 40 && time40L.equals(""))
				time40L = time;

			Launcher.scoreDisplay.update(time, usedPieceCount, clearedLine, pps, ppm, lpm, time40L);

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
