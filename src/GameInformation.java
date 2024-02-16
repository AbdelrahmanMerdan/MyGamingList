package src;

import javax.swing.*;

public class GameInformation {
	
	final static int jFrameHeight = 1080;
	final static int jFrameWidth = 720;
	//the 'game' string will be replaced 
	final static String jFrameTitle = "Game Information";
	static String gameTitle = "Heroes of Might and Magic 4";
	
	public static void main(String[] args) {
		
		GameInformation();
		
	}
	
	public static void GameInformation() {
	
		JFrame gameScreenFrame = new JFrame();
		JPanel gameScreenPanel = new JPanel();
		JTextField title = new JTextField();
		
	}
	
	public static JFrame intializeFrame(JFrame f) {
		
		f.setTitle(jFrameTitle);
		f.setSize(jFrameHeight, jFrameWidth);
		f.setAlwaysOnTop(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		return f;
		
		
	}
	
	public static JTextField title(JTextField t) {
		
		t.setText(gameTitle);
		
		return t;
		
	}
	
	
	public void update(JFrame scr) {
		
		scr.setVisible(true);
	}
	

}
