package edu.ncku.eddy;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.ncku.eddy.game.component.Piece.RotationMethod;
import edu.ncku.eddy.util.TestOutput;

public class Controller {

	private Frame window;
	private GameEngine targetEngine;
	private GameKeyListener keyListener;

	public Controller(Frame window, GameEngine targetEngine) {
		this.window = window;
		this.targetEngine = targetEngine;
	}

	public void startListener() {
		this.keyListener = this.new GameKeyListener();
		window.addKeyListener(this.keyListener);
		window.setFocusable(true);

		Launcher.gameDisplay.addKeyListener(this.keyListener);
		Launcher.scoreDisplay.addKeyListener(this.keyListener);
	}

	public void stopListener() {
		this.window.removeKeyListener(this.keyListener);
	}

	public class GameKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			TestOutput.sysout(keycode);

			if (keycode == 10) {
				if (!targetEngine.isGameRunning() && ! targetEngine.gameGo && !targetEngine.gameReady) {
					targetEngine.startGame();
				}
			}

			if (targetEngine.isGameRunning()) {
				switch (keycode) {
				case 37:
					// ��
					targetEngine.moveLeft();
					break;
				case 39:
					// �k
					targetEngine.moveRight();
					break;
				case 40:
					// �U(softdrop)
					targetEngine.moveDown();
					break;
				case 38:
					// �W(180����)
					targetEngine.rotatePiece(RotationMethod.UpsideDown);
					break;
				case 88:
					// X(����)
					targetEngine.rotatePiece(RotationMethod.Right);
					break;
				case 90:
					// Z(�f��)
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
					// �����C��
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
