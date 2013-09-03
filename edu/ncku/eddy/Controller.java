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
				//TODO:左				
				break;
			case 39:
				//TODO:右
				break;
			case 40:
				//TODO:下(softdrop)
				break;
			case 38:
				//TODO:上(180度轉)
				break;
			case 88:
				//TODO:X(順轉)
				break;
			case 90:
				//TODO:X(逆轉)
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
