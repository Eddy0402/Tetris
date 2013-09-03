package edu.ncku.eddy;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Controller {

	private JFrame window;
	private GameEngine targetEngine;
	private GameKeyListener keyListener;

	public Controller(JFrame window, GameEngine targetEngine) {
		this.window = window;
		this.targetEngine = targetEngine;
	}
	
	public void startListener(){
		this.keyListener = this.new GameKeyListener();
		window.addKeyListener(this.keyListener);
	}

	public void stopListener() {
		this.window.removeKeyListener(this.keyListener);		
	}
	
	public class GameKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			System.out.println(keycode);
			
			switch (keycode) {
			case 37:
				//TODO:��				
				break;
			case 39:
				//TODO:�k
				break;
			case 40:
				//TODO:�U(softdrop)
				break;
			case 38:
				//TODO:�W(180����)
				break;
			case 88:
				//TODO:X(����)
				break;
			case 90:
				//TODO:X(�f��)
				break;
			case 16:
				//TODO:Shift(Hold)
				break;
			case 32:
				//TODO:Space(HardDrop)
				break;				
			default:				
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {	}

	}


}
