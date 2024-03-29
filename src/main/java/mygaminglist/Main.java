package mygaminglist;

import java.awt.EventQueue;

import database.TimeData;

public class Main {
	public static void main(String[] args) {
		if(TimeData.isNextDay())
		{
			TimeData.updateTime();
		}
		
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
