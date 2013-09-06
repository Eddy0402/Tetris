package edu.ncku.eddy.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
                setButtonPressedState(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
                setButtonPressedState(e.getKeyCode(), false);
        }

        protected void setButtonPressedState(int keyCode, boolean pressed) {
        	
        }
}
