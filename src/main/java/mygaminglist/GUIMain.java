package mygaminglist;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.GameData;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;

public class GUIMain extends JFrame{
	
	private static JPanel basePane;
	private static JPanel mainPane;
	private static JPanel cardPane;
	public static JButton loginButton;
	public static String usernameLoggedIn = null;
	
	public GUIMain() {
		
		setTitle("MyGamingList");
		setBackground(new Color(23, 26, 33));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		setSize(new Dimension (1920, 1080)); //Old Resolution: 1511, 885
		setLocationRelativeTo(null);
		
		//instantiate base pane
		basePane = new JPanel();
		basePane.setLayout(new CardLayout());
		setContentPane(basePane);
		
		//load login page
		GUILogin login = new GUILogin();
		basePane.add(login.getMainPane(), "login");
		//((CardLayout) basePane.getLayout()).show(basePane, "login");
		
		//instantiate main pane
		mainPane = new JPanel();
		mainPane.setBackground(new Color(23, 26, 33));
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new BorderLayout(0, 0));
		basePane.add(mainPane, "main");
		((CardLayout) basePane.getLayout()).show(basePane, "main");
		
		//instantiate components for main pane
		JPanel headerPane = new JPanel();
		headerPane.setBackground(new Color(23, 26, 33));
		headerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.add(headerPane, BorderLayout.NORTH);
		headerPane.setLayout(new BorderLayout(0, 0));
		
		JPanel headerOptionsPane = new JPanel();
		headerOptionsPane.setBackground(new Color(23, 26, 33));
		headerPane.add(headerOptionsPane, BorderLayout.WEST);
		headerOptionsPane.setOpaque(false);
		JButton homeButton = new JButton("Home");
		homeButton.setForeground(Color.WHITE);
		homeButton.setBackground(new Color(23, 26, 33));
		homeButton.setOpaque(true);
		homeButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		homeButton.setFocusable(false);
		headerOptionsPane.add(homeButton);
		
		homeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, "mainMenu");
			}
		});
		
		
		//Set up My Reviews
		JButton myReviewsButton = new JButton("My Reviews");
		myReviewsButton.setForeground(Color.WHITE);
		myReviewsButton.setBackground(new Color(23, 26, 33));
		myReviewsButton.setOpaque(true);
		myReviewsButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		myReviewsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(loginButton == null || loginButton.getText().equals("Log in"))
				{
					((CardLayout) basePane.getLayout()).show(basePane, "login");
				}
				else
				{
					myReviewsButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					GUIGameReviews.loadUserReviews(usernameLoggedIn);
					((CardLayout) cardPane.getLayout()).show(cardPane, "reviews");
					myReviewsButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		
		myReviewsButton.setFocusable(false);
		headerOptionsPane.add(myReviewsButton);
		
		//Set up My Friends
		JButton friendsButton = new JButton("Friends");
		friendsButton.setForeground(Color.WHITE);
		friendsButton.setBackground(new Color(23, 26, 33));
		friendsButton.setOpaque(true);
		friendsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginButton == null || loginButton.getText().equals("Log in"))
				{
					((CardLayout) basePane.getLayout()).show(basePane, "login");
				}
				else
				{
					GUIMyFriends myFriends = new GUIMyFriends(cardPane, usernameLoggedIn);
					((CardLayout) cardPane.getLayout()).show(cardPane, "myFriends");
				}
			}
		});
		
		friendsButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		friendsButton.setFocusable(false);
		headerOptionsPane.add(friendsButton);
		
		JPanel headerSearchPane = new JPanel();
		headerPane.add(headerSearchPane, BorderLayout.EAST);
		headerSearchPane.setOpaque(false);
		
		JTextField headerSearchBox = new JTextField();
		headerSearchBox.setFont(new Font("MS Song", Font.PLAIN, 20));
		String searchPrompt = "Search";
		
		loginButton = new JButton("Log in");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(23, 26, 33));
		loginButton.setOpaque(true);
		loginButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginButton.getText() != null && loginButton.getText().equals("Log out"))
				{
					((CardLayout) cardPane.getLayout()).show(cardPane, "mainMenu");
					loginButton.setText("Log in");
					usernameLoggedIn = null;
				}
				else
				{
					((CardLayout) basePane.getLayout()).show(basePane, "login");
				}
			}
		});
		
		headerSearchPane.add(loginButton);
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
				FindIterable<Document> result = GameData.games.find(filter);
				
				if (result.first() != null) {
					int id = result.first().getInteger("_id");
					GUIGame.loadGame(id);
					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
				} else {
					headerSearchBox.setText("Invalid Name");
					
				}
				headerSearchBox.getRootPane().requestFocus();
			}
		});

		//focus management
		headerSearchBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				headerSearchBox.setText("");
			}
			public void focusLost(FocusEvent e) {
				try {
					Thread.sleep(330);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				headerSearchBox.setText(searchPrompt);
			}
		});

		mainPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainPane.requestFocus();
			}
		});
		
		//order matters
		PopReleases popReleases = new PopReleases();
		MostPlayed mostPlayed = new MostPlayed();
		
		//instantiate card pane
		cardPane = new JPanel();
		mainPane.add(cardPane, BorderLayout.CENTER);
		cardPane.setLayout(new CardLayout());
		cardPane.setBackground(new Color(27, 40, 56));
		
		//instantiate all GUI elements
		GUIMainMenu mainMenu = new GUIMainMenu(cardPane, mostPlayed, popReleases);
		GUIMyFriends myFriends = new GUIMyFriends(cardPane, usernameLoggedIn);
		GUIGame game = new GUIGame(cardPane);
		GUIGameReviews review = new GUIGameReviews(cardPane);
		
	}
}
