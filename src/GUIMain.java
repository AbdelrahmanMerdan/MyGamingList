package src;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.*;

public class GUIMain extends JFrame{
	
	private static JPanel mainPane = new JPanel();
	private static JPanel cardPane = new JPanel();
	
	public GUIMain() {
		
		setTitle("MyGamingList");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		
		mainPane.setBackground(Color.BLACK);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		mainPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainPane.requestFocus();
			}
		});
		
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
		String searchPrompt = "Search";
		headerSearchBox.setText(searchPrompt);
		headerSearchPane.add(headerSearchBox);
		headerSearchBox.setColumns(15);
		
		headerSearchBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Search function (case in-sensitive)
				System.out.println(headerSearchBox.getText());
				
				//Filtering
				Pattern namePattern = Pattern.compile("^" + headerSearchBox.getText() + "$",
						Pattern.CASE_INSENSITIVE);
				Bson filter = regex("name", namePattern);
				FindIterable<Document> result = Database.games.find(filter);
				System.out.println(result.first());
				
				if (result.first() != null) {
					try {
						Game gameResult = Database.map.readValue(result.first().toJson(),Game.class);
						GUIGame.loadGame(gameResult);
						((CardLayout) cardPane.getLayout()).show(cardPane, "game");
					} catch (JsonMappingException e1) {
						System.out.println("JsonMappingException");
					} catch (JsonProcessingException e1) {
						System.out.println("JsonProcessingException");
					}
				} else {
					headerSearchBox.setText("Invalid Name");
				}
				headerSearchBox.getRootPane().requestFocus();
			}
		});
		
		headerSearchBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				headerSearchBox.setText("");
			}
			public void focusLost(FocusEvent e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
				headerSearchBox.setText(searchPrompt);
			}
		});
		
		//JPanel footerPane = new JPanel();
		//mainPane.add(footerPane, BorderLayout.SOUTH);
		
		mainPane.add(cardPane, BorderLayout.CENTER);
		cardPane.setLayout(new CardLayout());
		
		//order matters
		PopReleases popReleases = new PopReleases();
		MostPlayed mostPlayed = new MostPlayed();
		
		GUIMainMenu mainMenu = new GUIMainMenu(cardPane, mostPlayed, popReleases);
		GUIGame game = new GUIGame(cardPane);
	}
}
