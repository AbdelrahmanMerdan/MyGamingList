package src;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain appFrame = new GUIMain();
					appFrame.setVisible(true);
					appFrame.getComponent(0).requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
