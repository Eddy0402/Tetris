package edu.ncku.eddy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

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
	private Piece holdPiece;
	private Randomizer randomizer;

	public Randomizer getRandomizer() {
		return randomizer;
	}

	private boolean gameRunning = false;
	private boolean shouldRedraw;

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
	}

	public void startGame() {
		gameField.reset();

		// 種子碼
		long seed = new Random().nextLong();

		pieceIndex = 0;
		clearedLine = 0;
		usedPieceCount = 0;

		randomizer = new Randomizer(seed);
		getNewPiece();

		gameThread = new GameDisplayThread(this);
		gameRunning = true;
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
		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
		}
		gameRunning = false;
		JOptionPane.showMessageDialog(null, "Game Over! Press Enter to restart.");
	}

	public void stopGame() {
		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
		}
		gameRunning = false;
		JOptionPane.showMessageDialog(null, "Game Stoped! Press Enter to restart.");
	}

	public void pause() {
		// TODO:暫緩
	}

	public void moveLeft() {
		if (currentPiece.moveLeft())
			shouldRedraw = true;
	}

	public void moveRight() {
		if (currentPiece.moveRight())
			shouldRedraw = true;
	}

	public void rotatePiece(RotationMethod rotationMethod) {
		if (currentPiece.rotatePiece(rotationMethod))
			shouldRedraw = true;
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

	public void drop() {

		if (currentPiece.moveDown()) {
			shouldRedraw = true;
		} else {
			lockCount++;
			if (lockCount > 1) {
				lockPiece();
				lockCount = 0;
			}
		}
		shouldRedraw = true;
	}

	public void lockPiece() {

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

	public boolean isGameRunning() {
		return gameRunning;
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
			startTimeMillis = System.currentTimeMillis();

			// 遊戲迴圈
			do {
				tick();
			} while (isGameRunning());

		}

		@Override
		public void interrupt() {
			super.interrupt();

		}

		public void tick() {
			tickCount++;

			// 每秒下降一次
			if (tickCount - lastDrop > 100) {
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
