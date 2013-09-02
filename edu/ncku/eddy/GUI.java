package edu.ncku.eddy;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {

	private JButton startEndButton;
	private JFrame mainFrame;
		
	public GUI(JFrame mainFrame){
		this.mainFrame = mainFrame;
	}

	public void initGUI(){
		Action startEndButtonAction = this.new startEndButtonAction();
		startEndButton = new JButton(startEndButtonAction);
	}
	//«ö¶s°Ê§@
	
	
	
	
		public class startEndButtonAction implements Action{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Object getValue(String key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void putValue(String key, Object value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setEnabled(boolean b) {
				// TODO Auto-generated method stub
				
			}
}
