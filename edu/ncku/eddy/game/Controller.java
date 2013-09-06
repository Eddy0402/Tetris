package edu.ncku.eddy.game;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.component.Piece.RotationMethod;
import edu.ncku.eddy.game.GameEngine.GameState;
import edu.ncku.eddy.util.Config;
import edu.ncku.eddy.util.TestOutput;

public class Controller {

	private Frame window;
	private GameEngine targetEngine;
	private GameKeyAdapter keyAdapter;

	private DASThread leftThread;
	private DASThread rightThread;
	private DASThread softDropThread;

	public Controller(Frame window, GameEngine targetEngine) {
		this.window = window;
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
		if (keycode == 10 && changeStatus == true) {
			if (gameState != GameState.Ready && gameState != GameState.Go
					&& gameState != GameState.Playing) {
				targetEngine.startGame();
			}
		}

		// 按下
		if (gameState == GameState.Playing && changeStatus == true) {
			switch (keycode) {
			case 37:
				// 左
				if (leftThread ==null || !leftThread.isAlive()) {
					leftThread = new DASThread(Config.DAS, Config.ARR, 0);
					leftThread.start();
				}
				break;
			case 39:
				// 右
				if (rightThread ==null || !rightThread.isAlive()) {
					rightThread = new DASThread(Config.DAS, Config.ARR, 1);
					rightThread.start();
				}
				break;
			case 40:
				// 下(softdrop)
				if (softDropThread ==null || !softDropThread.isAlive()) {
					softDropThread = new DASThread(0, Config.SOFT_DROP_ARR, 2);
					softDropThread.start();
				}
				break;
			case 38:
				// 上(180度轉)
				targetEngine.rotatePiece(RotationMethod.UpsideDown);
				break;
			case 88:
				// X(順轉)
				targetEngine.rotatePiece(RotationMethod.Right);
				break;
			case 90:
				// Z(逆轉)
				targetEngine.rotatePiece(RotationMethod.Left);
				break;
			case 16:
				// Shift(Hold)
				targetEngine.hold();
				break;
			case 32:
				// Space(HardDrop)
				targetEngine.hardDrop();
				break;
			case 27:
				// 結束遊戲
				targetEngine.stopGame();
				break;
			default:
				break;
			}
		}

	}

	private class DASThread extends Thread {
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
				if (targetEngine.moveLeft()) {	}else{stop();}
				break;
			case 1:
				if (targetEngine.moveRight()) {	}else{stop();}
				break;
			case 2:
				if (targetEngine.softDrop()) {	}else{stop();}
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
					if (targetEngine.moveLeft()) {	}else{stop();}
					break;
				case 1:
					if (targetEngine.moveRight()) {	}else{stop();}
					break;
				case 2:
					if (targetEngine.softDrop()) {	}else{stop();}
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
