package mygaminglist;

import java.awt.EventQueue;

import database.TimeData;

import javax.swing.*;

import static java.lang.System.exit;

public class Main {
	public static void main(String[] args) {
		if(TimeData.isNextDay())
		{
			TimeData.updateTime();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

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
