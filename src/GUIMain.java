package src;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUIMain extends JFrame{
	
	private static JPanel mainPane = new JPanel();
	private static JPanel cardPane = new JPanel();
	
	public GUIMain() {
		
		setTitle("MyGamingList");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 790);
		
		mainPane.setBackground(Color.BLACK);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		JPanel headerPane = new JPanel();
		headerPane.setBackground(Color.BLACK);
		headerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.add(headerPane, BorderLayout.NORTH);
		headerPane.setLayout(new BorderLayout(0, 0));
		
		JPanel headerOptionsPane = new JPanel();
		headerPane.add(headerOptionsPane, BorderLayout.WEST);
		headerOptionsPane.setOpaque(false);
		JButton homeButton = new JButton("Home");
		homeButton.setFocusable(false);
		headerOptionsPane.add(homeButton);
		
		homeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, "mainMenu");
			}
			
		});
		
		JButton myGamesButton = new JButton("My Games");
		myGamesButton.setFocusable(false);
		headerOptionsPane.add(myGamesButton);
		JButton friendsButton = new JButton("Friends");
		friendsButton.setFocusable(false);
		headerOptionsPane.add(friendsButton);
		
		JPanel headerSearchPane = new JPanel();
		headerPane.add(headerSearchPane, BorderLayout.EAST);
		headerSearchPane.setOpaque(false);
		
		JTextField headerSearchBox = new JTextField();
		headerSearchBox.setText("Search");
		headerSearchPane.add(headerSearchBox);
		headerSearchBox.setColumns(15);
		
		//JPanel footerPane = new JPanel();
		//mainPane.add(footerPane, BorderLayout.SOUTH);
		
		mainPane.add(cardPane, BorderLayout.CENTER);
		cardPane.setLayout(new CardLayout());

		PopReleases popReleases = new PopReleases();
		MostPlayed mostPlayed = new MostPlayed();
		
		
		GUIMainMenu mainMenu = new GUIMainMenu(cardPane, mostPlayed, popReleases);
		GUIGame game = new GUIGame(cardPane);
		
	}
}
