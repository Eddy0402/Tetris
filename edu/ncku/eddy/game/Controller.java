package edu.ncku.eddy.game;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.component.Piece.RotationMethod;
import edu.ncku.eddy.game.GameEngine.GameState;
import edu.ncku.eddy.util.TestOutput;

public class Controller {

	private Frame window;
	private GameEngine targetEngine;
	private GameKeyListener keyListener;
	private GameKeyAdapter keyAdapter;

	public Controller(Frame window, GameEngine targetEngine) {
		this.window = window;
		this.targetEngine = targetEngine;
	}

	public void startListener() {

		this.keyAdapter = new GameKeyAdapter();
		// Launcher.gameDisplay.addKeyListener(this.keyAdapter);
		// Launcher.scoreDisplay.addKeyListener(this.keyAdapter);

		this.keyListener = new GameKeyListener();

		Launcher.gameDisplay.addKeyListener(this.keyListener);
		Launcher.scoreDisplay.addKeyListener(this.keyListener);

	}

	public void stopListener() {
		Launcher.gameDisplay.removeKeyListener(this.keyAdapter);
		Launcher.scoreDisplay.removeKeyListener(this.keyAdapter);
		this.keyAdapter = null;
	}

	public void moveLeftOnce() {
		targetEngine.moveLeft();
	}

	public class GameKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			TestOutput.sysout(keycode);

			GameState gameState = targetEngine.getGameState();
			if (keycode == 10) {
				if (gameState != GameState.Ready && gameState != GameState.Go
						&& gameState != GameState.Playing) {
					targetEngine.startGame();
				}
			}

			if (gameState == GameState.Playing) {
				switch (keycode) {
				case 37:
					// 左
					targetEngine.moveLeft();
					break;
				case 39:
					// 右
					targetEngine.moveRight();
					break;
				case 40:
					// 下(softdrop)
					targetEngine.moveDown();
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

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

}
