
package mygaminglist;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.AutoSearch;

public class GUIMain extends JFrame{
	
	private static JPanel basePane;
	private static JPanel mainPane;
	private static JPanel cardPane;
	public static JButton loginButton;
	public static String usernameLoggedIn = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		homeButton.setFont(new Font("Verdana", Font.PLAIN, 24));
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
		myReviewsButton.setFont(new Font("Verdana", Font.PLAIN, 24));
		myReviewsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(loginButton == null || loginButton.getText().equals("Log in"))
				{
					((CardLayout) basePane.getLayout()).show(basePane, "login");
				}
				else
				{
					//((CardLayout) cardPane.getLayout()).show(cardPane, "myReviewedGames");
					GUIGameReviews.loadUserReviews(usernameLoggedIn);
					((CardLayout) cardPane.getLayout()).show(cardPane, "reviews");
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
		
		friendsButton.setFont(new Font("Verdana", Font.PLAIN, 24));
		friendsButton.setFocusable(false);
		headerOptionsPane.add(friendsButton);
		
		//Set up Top Games
		JButton TopGamesButton = new JButton("Top Games");
		TopGamesButton.setOpaque(true);
		TopGamesButton.setForeground(Color.WHITE);
		TopGamesButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		TopGamesButton.setFocusable(false);
		TopGamesButton.setBackground(new Color(23, 26, 33));
		
		TopGamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				{
//					GUIMyFriends myFriends = new GUIMyFriends(cardPane, usernameLoggedIn);
//					((CardLayout) cardPane.getLayout()).show(cardPane, "topGames");
					((CardLayout) cardPane.getLayout()).show(cardPane, "topGames");
				}
			}
		});
		
		
		headerOptionsPane.add(TopGamesButton);
		//End of Top Games
		
		
		
		JPanel headerSearchPane = new JPanel();
		headerPane.add(headerSearchPane, BorderLayout.EAST);
		headerSearchPane.setOpaque(false);
		
		loginButton = new JButton("Log in");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(23, 26, 33));
		loginButton.setOpaque(true);
		loginButton.setFont(new Font("Verdana", Font.PLAIN, 24));
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
		
		JComboBox headerSearchBox = new JComboBox();
		headerSearchBox.setFont(new Font("MS Song", Font.PLAIN, 24));
		headerSearchBox.setEditable(true);
		headerSearchBox.setPreferredSize(new Dimension(350,30));
		String searchPrompt = "Search";
		headerSearchBox.addItem(searchPrompt);
		headerSearchPane.add(headerSearchBox);
		
		JTextField searchQuery = (JTextField) headerSearchBox.getEditor().getEditorComponent();
		ArrayList<String> result = new ArrayList<String>();
		
		// focus management
		headerSearchBox.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				headerSearchBox.removeAllItems();
			}
			@Override
			public void focusLost(FocusEvent e) {
				headerSearchBox.removeAllItems();
				headerSearchBox.addItem(searchPrompt);
			}
		});
		
		// auto-complete
		headerSearchBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	String search = searchQuery.getText();
            	ArrayList<String> result = (ArrayList<String>) AutoSearch.getAutofill(search);
            	
            	headerSearchBox.removeAllItems();
            	headerSearchBox.addItem(search);
            	for (int i = 0; i < result.size(); i++) {
            		if (!result.get(i).equals("")) {
            			headerSearchBox.addItem(result.get(i));            		
            		}
            	}
            	headerSearchBox.hidePopup();
            	if (search.length() >= 2) {
            		headerSearchBox.showPopup();
            	}
            }
		});
		
		// search listener
		searchQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (headerSearchBox.getSelectedItem() != null && !searchQuery.equals("")) {
					String search = headerSearchBox.getSelectedItem().toString();
					AutoSearch.search(search);
					mainPane.requestFocus();
				}
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
		TopGames topGame = new TopGames();
		
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
		AutoSearch autocomplete = new AutoSearch(cardPane);
		GUITopGames topGames = new GUITopGames(cardPane, topGame);
		
	}
}

