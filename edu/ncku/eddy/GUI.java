package edu.ncku.eddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {

	private JButton startEndButton;
	private JFrame frame;

	public GUI(JFrame frame) {
		this.frame = frame;
	}

	public void initGUI() {

		startEndButton = new JButton("開始");		
		startEndButton.setBounds(300, 20, 200, 30);
		
		ActionListener startEndButtonActionListener = this.new startEndButtonAction();
		startEndButton.addActionListener(startEndButtonActionListener);
		
			
		frame.add(startEndButton);
	}

	// 按鈕動作
	public class startEndButtonAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!Start.gameEngine.isGameRunning()){
				((JButton)e.getSource()).setText("停止");
				Start.gameEngine.start();
			}else{
				Start.gameEngine.stop();
				((JButton)e.getSource()).setText("開始");
			}

		}

	}
}
