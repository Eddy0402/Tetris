package edu.ncku.eddy.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import edu.ncku.eddy.util.Config;

public class GameKeyAdapter extends KeyAdapter {

	private Controller targetController;
	
	public GameKeyAdapter(Controller targetController) {
		this.targetController=targetController;
	}

	HashMap<Integer, Boolean> keyStatus = new HashMap<Integer, Boolean>();
	boolean a = false;
	boolean b = false;

	@Override
	public void keyPressed(KeyEvent e) {
		if (b) {
			b = false;
			return;
		}
		int keyCode = e.getKeyCode();
		Boolean pastStatus = keyStatus.get(keyCode);

		if (pastStatus == null || !pastStatus) {
			keyStatus.put(keyCode, true);
			buttonPressedStateChanged(e.getKeyCode(), true);
			a = true;
			keyReleased(e);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (a) {
			a = false;
			return;
		}
		int keyCode = e.getKeyCode();
		Boolean pastStatus = keyStatus.get(keyCode);

		if (pastStatus == null || pastStatus) {
			keyStatus.put(keyCode, false);
			buttonPressedStateChanged(e.getKeyCode(), false);
			b = true;
			keyPressed(e);
		}
	}

	protected void buttonPressedStateChanged(int keyCode, boolean changeStatus) {
		if(keyCode == Config.KEY_MOVE_LEFT){
			
		}
		if(keyCode == Config.KEY_MOVE_RIGHT){
			
		}
		targetController.processKey(keyCode, changeStatus);
	}
	
	/**
	 * ½T»{¬O§_ARR
	 * @param operation 1 ¥ª 2 ¥k 3¤U­°
	 * @return
	 */
	public boolean checkKeyPressed(int operation){
		switch (operation) {
		case 0:
			if( keyStatus.get(Config.KEY_MOVE_LEFT)!=null && keyStatus.get(Config.KEY_MOVE_LEFT)==true){
				return true;
			}
			return false;
		case 1:
			if( keyStatus.get(Config.KEY_MOVE_RIGHT)!=null && keyStatus.get(Config.KEY_MOVE_RIGHT)==true){
				return true;
			}
			return false;
		case 2:
			if( keyStatus.get(Config.KEY_SOFT_DROP)!=null && keyStatus.get(Config.KEY_SOFT_DROP)==true){
				return true;
			}
			return false;	
		}
		return false;
	}
}
