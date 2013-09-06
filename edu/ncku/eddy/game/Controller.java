package edu.ncku.eddy.game;

import java.awt.Frame;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.component.Piece.RotationMethod;
import edu.ncku.eddy.game.GameEngine.GameState;
import edu.ncku.eddy.util.Config;

public class Controller {

	private GameEngine targetEngine;
	private GameKeyAdapter keyAdapter;

	private DASThread leftThread;
	private DASThread rightThread;
	private DASThread softDropThread;

	public Controller(Frame window, GameEngine targetEngine) {
		this.targetEngine = targetEngine;

	}

	public void startListener() {

		this.keyAdapter = new GameKeyAdapter(this);
		Launcher.gameDisplay.addKeyListener(this.keyAdapter);
		Launcher.scoreDisplay.addKeyListener(this.keyAdapter);

	}

	public void stopListener() {
		Launcher.gameDisplay.removeKeyListener(this.keyAdapter);
		Launcher.scoreDisplay.removeKeyListener(this.keyAdapter);
		this.keyAdapter = null;
	}

	public void moveLeftOnce() {
		targetEngine.moveLeft();
	}

	public void processKey(int keycode, boolean changeStatus) {

		GameState gameState = targetEngine.getGameState();
		if (keycode == Config.KEY_START_GAME && changeStatus == true) {
			if (gameState != GameState.Ready && gameState != GameState.Go
					&& gameState != GameState.Playing) {
				targetEngine.startGame();
			}
		}

		// 按下
		if (gameState == GameState.Playing && changeStatus == true) {
			
			if(keycode==Config.KEY_MOVE_LEFT) {
				// 左
				if (leftThread ==null || !leftThread.isAlive()) {
					leftThread = new DASThread(Config.DAS, Config.ARR, 0);
					leftThread.start();
				}
			}

			if(keycode==Config.KEY_MOVE_RIGHT) {
				// 右
				if (rightThread ==null || !rightThread.isAlive()) {
					rightThread = new DASThread(Config.DAS, Config.ARR, 1);
					rightThread.start();
				}
			}
			
			if(keycode==Config.KEY_SOFT_DROP) {
				// softdrop
				if (softDropThread ==null || !softDropThread.isAlive()) {
					softDropThread = new DASThread(0, Config.SOFT_DROP_ARR, 2);
					softDropThread.start();
				}
			}
			
			if(keycode==Config.KEY_ROTATE_UPSIDE_DOWN) {
				// 180度轉
				targetEngine.rotatePiece(RotationMethod.UpsideDown);
			}
			
			if(keycode==Config.KEY_ROTATE_RIGHT) {
				// X(順轉)
				targetEngine.rotatePiece(RotationMethod.Right);
			}
			
			if(keycode==Config.KEY_ROTATE_LEFT) {
				// Z(逆轉)
				targetEngine.rotatePiece(RotationMethod.Left);
			}
			
			if(keycode==Config.KEY_HOLD) {
				// Shift(Hold)
				targetEngine.hold();
			}
			
			if(keycode==Config.KEY_HARD_DROP) {
				// Space(HardDrop)
				targetEngine.hardDrop();
			}
			
			if(keycode==Config.KEY_STOP_GAME) {
				// 結束遊戲
				targetEngine.stopGame();
			}

		}

	}

	class DASThread extends Thread {
		int DAS;
		int ARR;
		int operation;

		public DASThread(int DAS, int ARR, int operation) {
			this.DAS = DAS;
			this.ARR = ARR;
			this.operation = operation;
		}

		public void run() {
			switch (operation) {
			case 0:
				if (targetEngine.moveLeft()) {	}else{if(Config.AR)stop();}
				break;
			case 1:
				if (targetEngine.moveRight()) {	}else{if(Config.AR)stop();}
				break;
			case 2:
				if (targetEngine.softDrop()) {	}else{if(Config.AR)stop();}
				break;
			}

			// DAS
			try {
				Thread.sleep(DAS);
			} catch (InterruptedException e) {
			}

			// ARR
			while (true) {
				if (keyAdapter.checkKeyPressed(operation))
				{}else{
					stop();
				}
				
				switch (operation) {
				case 0:
					if (targetEngine.moveLeft()) {	}else{if(Config.AR)stop();}
					break;
				case 1:
					if (targetEngine.moveRight()) {	}else{if(Config.AR)stop();}
					break;
				case 2:
					if (targetEngine.softDrop()) {	}else{if(Config.AR)stop();}
					break;
				}

				try {
					Thread.sleep(ARR);
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
