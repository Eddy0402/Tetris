package edu.ncku.eddy;

import java.util.Random;

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
	private boolean shouldRedraw;

	private Thread gameThread;

	// drop���Ѩ⦸�hlock
	private int lockCount = 0;
	
	//�ϥΪ�Piece�ƶq
	private int pieceCount;

	public int getPieceCount() {
		return pieceCount;
	}

	public GameEngine() {
		this.gameField = new Field();
	}

	public void startGame() {
		gameField.reset();

		//�ؤl�X
		long seed = new Random().nextLong();
		
		pieceCount = 0;		

		randomizer = new Randomizer(seed);
		getNewPiece();

		gameThread = new GameDisplayThread(this);
		gameRunning = true;
		gameThread.start();

	}
	
	private void getNewPiece(){
		TestOutput.sysout(pieceCount);
		currentPiece = randomizer.getNewPiece();
		if (!currentPiece.canMoveDown()){
			gameOver();
		}
	}

	public void gameOver() {
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
		// TODO:�Ƚw
	}

	public void moveLeft() {
		if(currentPiece.moveLeft())shouldRedraw = true;
	}

	public void moveRight() {
		if(currentPiece.moveRight())shouldRedraw = true;
	}

	public void rotatePiece(RotationMethod rotationMethod) {
		if(currentPiece.rotatePiece(rotationMethod))shouldRedraw = true;
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
		pieceCount++;
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
		gameField.checkLineClear();
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

	public class GameDisplayThread extends Thread {

		private GameEngine targetEngine;
		private Display display;

		private long lastDrop = 0; // �W��drop��tick
		private long tickCount; // �`�@�]�L�X��tick
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

			// �C���j��
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

			//�C��U���@��
			if (tickCount - lastDrop > 100) {
				targetEngine.drop();
				TestOutput.sysout("drop tick");
				lastDrop = tickCount;
			}

			// �Y�����ܡG
			// targetEngine.shouldRedraw = true;

			// �g�Ltick�`��tickCount
			// �g�L�ɶ�System.currentTimeMillis()-startTime

			// �ؼгt�׬�100 ticks / sec
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
