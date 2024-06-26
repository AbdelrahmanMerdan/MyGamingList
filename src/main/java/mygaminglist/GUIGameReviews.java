package mygaminglist;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import database.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.DefaultComboBoxModel;

public class GUIGameReviews extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JPanel card;
	private static Box reviewBox;
	private static JLabel reviewTitleLabel;
	private static JButton newReviewButton;
	private static String backLocation;
	private static JPanel buttonPanel;
	private static JScrollPane reviewScrollPane;
	private static Game loadedGame;
	private static String loadedUser;
	private static boolean chronOrder = true;
	private static int recommendSort = 0;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUIGameReviews(JPanel cardPane) {
		
		card = cardPane;
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout(0, 0));
		mainPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(27, 40, 56)));
		cardPane.add(mainPane, "reviews");
		
		//generate container
		reviewBox = new Box(1);
		reviewBox.setBackground(new Color(27, 40, 56));
		reviewBox.setBorder(new MatteBorder(0, 10, 10, 10, (Color) new Color(23, 26, 33)));
		
		//generate scrollable		
		reviewScrollPane = new JScrollPane(reviewBox);
		reviewScrollPane.getViewport().setBackground(new Color(27, 40, 56));
		reviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reviewScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		reviewScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		reviewScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		reviewScrollPane.setBorder(BorderFactory.createEmptyBorder());

		mainPane.add(reviewScrollPane, BorderLayout.CENTER);
		
		//this  rest is mostly formatting and shit
		JPanel ReviewHeaderPane = new JPanel();
		ReviewHeaderPane.setBackground(new Color(27, 40, 56));
		mainPane.add(ReviewHeaderPane, BorderLayout.NORTH);
		ReviewHeaderPane.setLayout(new BorderLayout(0, 0));
		
		reviewTitleLabel = new JLabel("GAME/USER");
		reviewTitleLabel.setForeground(Color.WHITE);
		reviewTitleLabel.setFont(new Font("MS Song", Font.BOLD, 40));
		reviewTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ReviewHeaderPane.add(reviewTitleLabel, BorderLayout.WEST);
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(23, 26, 33));
		ReviewHeaderPane.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JComboBox sortRecommendedComboBox = new JComboBox();
		sortRecommendedComboBox.setForeground(Color.WHITE);
		sortRecommendedComboBox.setBackground(new Color(23, 26, 33));
		sortRecommendedComboBox.setFont(new Font("Verdana", Font.PLAIN, 20));
		sortRecommendedComboBox.setModel(new DefaultComboBoxModel(new String[] {"All Reviews", "Recommended", "Not Recommended"}));
		sortRecommendedComboBox.setMaximumRowCount(3);
		sortRecommendedComboBox.setSelectedIndex(recommendSort);
		buttonPanel.add(sortRecommendedComboBox);
		
		JButton backButton = new JButton("  BACK  ");
		backButton.setForeground(Color.WHITE);
		backButton.setBackground(new Color(23, 26, 33));
		backButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		backButton.setOpaque(true);
		buttonPanel.add(backButton);
		backButton.setFocusable(false);
		
		JToggleButton sortOrderButton = new JToggleButton("Sort by New");
		sortOrderButton.setForeground(Color.WHITE);
		sortOrderButton.setBackground(new Color(23, 26, 33));
		sortOrderButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		sortOrderButton.setSelected(!chronOrder);
		buttonPanel.add(sortOrderButton);
		
		newReviewButton = new JButton("REVIEW");
		newReviewButton.setForeground(Color.WHITE);
		newReviewButton.setBackground(new Color(23, 26, 33));
		newReviewButton.setOpaque(true);
		newReviewButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		buttonPanel.add(newReviewButton);
		newReviewButton.setFocusable(false);
		
		//listener for back button, changes between targets
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, backLocation);
			}
		});
		
		sortOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selected = sortOrderButton.isSelected();
				chronOrder = !selected;
				drawReviews();
			}
		});
		
		sortRecommendedComboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	int selected = sortRecommendedComboBox.getSelectedIndex();
		    	recommendSort = selected;
		    	drawReviews();
		    }
		});
	}
	
	// resets the class for new game reviews and calls drawReviews
	public static void loadGameReviews(Game game) {
		loadedUser = null;
		loadedGame = game;
		chronOrder = true;
		recommendSort = 0;
		backLocation = "game";
		
		drawReviews();
		
		newReviewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				boolean hasReviewed = Review.AlreadyReviewed(game, GUIMain.usernameLoggedIn);
				boolean isBanned = User.isBanned(GUIMain.usernameLoggedIn);
				
				if(isBanned) {
					JOptionPane.showMessageDialog(null, "You are banned from reviewing.");
				}
				
				else if (GUIMain.usernameLoggedIn != null && !hasReviewed) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								GUINewUserReview reviewFrame = new GUINewUserReview(card, game);
								reviewFrame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}

				else if(hasReviewed) {
					int reWrite = JOptionPane.showConfirmDialog(null, "You cannot review games you've already reviewed.\n\nWould you like to delete your current review in order to make a new one?", "New Review", 
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					switch(reWrite) {
					case 0:
						Review.DeleteReview(GUIMain.usernameLoggedIn, game);
						drawReviews();
						try {
							GUINewUserReview reviewFrame = new GUINewUserReview(card, game);
							drawReviews();
							reviewFrame.setVisible(true);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please login to review.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	// resets the class for new user reviews and calls drawReviews
	public static void loadUserReviews(String user) { 
		loadedUser = user;
		loadedGame = null;
		chronOrder = true;
		recommendSort = 0;
		backLocation = "mainMenu";
		drawReviews();
	}
	
	// iterates through the review list and calls generateReviews for each review
	@SuppressWarnings("unchecked")
	private static void drawReviews() {
		new GUIGameReviews(card);
		
		if (loadedUser == null) { // game
			reviewTitleLabel.setText(loadedGame.getName());
			
			List<Object> reviews = sort(loadedGame.getComment());

			for( int i = 0; i < reviews.size(); i++) {
				List<Object> review = (List<Object>) reviews.get(i);
				List<Object> commentArray = (List<Object>) ((List<Object>) review).get(4);
				
				generateReviews(review, commentArray, GUIGame.game);
			}
		} else if (loadedGame == null) { // user
			reviewTitleLabel.setText(loadedUser+" Reviews");
			newReviewButton.setVisible(false);
			
			User current = UsersImpl.getUser(loadedUser);
			List<Object> reviews = sort(current.getGames());

			for(int i = 0; i < reviews.size(); i++) {
				List<Object> review = (List<Object>) reviews.get(i);

				int id = (int) review.get(0);
				Game game = GameData.getGame(id);
				List<Object> comments = Review.getAllCommentsForReview(current, game);

				generateReviews(review, comments, game);
			}
		}
		
		((CardLayout) card.getLayout()).show(card, "reviews");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				reviewScrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}
	
	// filters and sorts the review list
	@SuppressWarnings("unchecked")
	private static List<Object> sort(List<Object> unsortedList){
		
		ArrayList<Object> sortedList = new ArrayList<Object>();
		
		for( int i = 0; i < unsortedList.size(); i++) {

			List<Object> review;
			if (chronOrder) {
				review = (List<Object>) unsortedList.get(i);
			} else {
				review = (List<Object>) unsortedList.get(unsortedList.size() - 1 - i);
			}

			String isRecommended;
			if (loadedUser == null) { // game
				List<Object> reviewElements = (List<Object>) review;
				isRecommended = (String) reviewElements.get(2);
			} else {
				isRecommended = (String) review.get(3);
			}
			if (recommendSort == 1 && !isRecommended.equals("Yes")) {
				continue;
			} else if (recommendSort == 2 && !isRecommended.equals("No")) {
				continue;
			}
			sortedList.add(review);
		}
		return sortedList;
	}
	
	// draws each reviews
	private static void generateReviews(List<Object> reviews, List<Object> commentArray, Game game) {
		
		List<Object> review = reviews;
		
		JPanel reviewPane = new JPanel();
		reviewPane.setBackground(Color.BLACK);
		reviewPane.setLayout(new BorderLayout(0, 0));
		
		JPanel reviewHeaderPane = new JPanel();
		reviewHeaderPane.setBorder(new MatteBorder(10, 0, 10, 0, (Color) new Color(23, 26, 33)));
		reviewHeaderPane.setBackground(new Color(23, 26, 33));
		reviewPane.add(reviewHeaderPane, BorderLayout.NORTH);
		reviewHeaderPane.setLayout(new BorderLayout(0, 0));
		
		String name;
		if (loadedUser == null) {
			name = (String) review.get(0);
		} else {
			name = (String) review.get(1);
		}
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		nameLabel.setForeground(Color.WHITE);
		reviewHeaderPane.add(nameLabel, BorderLayout.WEST);
		
		String recommended;
		if (loadedUser == null) { // game
			recommended = (String) review.get(2);
		} else {
			recommended = (String) review.get(3);
		}
		JLabel recommendLabel = new JLabel("RECCOMENDED: " + recommended);
		recommendLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		recommendLabel.setForeground(Color.WHITE);
		recommendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reviewHeaderPane.add(recommendLabel, BorderLayout.CENTER);
		
		String score;
		if (loadedUser == null) { // game
			score = String.valueOf(review.get(1));
		} else {
			score = String.valueOf(review.get(2));
		}
		JLabel scoreLabel = new JLabel("SCORE: " + score);
		scoreLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		reviewHeaderPane.add(scoreLabel, BorderLayout.EAST);
		
		if(loadedUser != null && (GUIMain.usernameLoggedIn.equals(loadedUser)))
		{
			reviewHeaderPane.remove(recommendLabel);
			reviewHeaderPane.remove(scoreLabel);
			scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
			JPanel labelPanel = new JPanel();
			labelPanel.setLayout(new BorderLayout(0,0));
			labelPanel.setBackground(new Color(23, 26, 33));
			JLabel deleteLabel = new JLabel("DELETE REVIEW");
			deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			deleteLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
			deleteLabel.setForeground(Color.WHITE);
			deleteLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			deleteLabel.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this review?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    				switch(confirm) {
    				case 0:
    					Review.DeleteReview(GUIMain.usernameLoggedIn, game);
						drawReviews();
    				}
    			}
    		});
			
			labelPanel.add(recommendLabel, BorderLayout.CENTER);
			labelPanel.add(scoreLabel, BorderLayout.EAST);
			reviewHeaderPane.add(labelPanel, BorderLayout.CENTER);
			reviewHeaderPane.add(deleteLabel, BorderLayout.EAST);
		}
		
		JPanel commentPane = new JPanel();
		reviewPane.add(commentPane, BorderLayout.SOUTH);
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel forumPane = new JPanel();
		commentPane.add(forumPane);
		forumPane.setLayout(new BorderLayout(0, 0));

		forumPane.setVisible(false);
		
		Box commentBox = new Box(1);
		
		for(int i = 0; i < commentArray.size(); i +=2)
		{
			comment(commentBox, commentArray, i);
		}
		
		JScrollPane commentScrollPane = new JScrollPane(commentBox);
		commentScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		forumPane.add(commentScrollPane);
		
		JPanel comentHeaderPane = new JPanel();
		comentHeaderPane.setBackground(new Color(23, 26, 33));
		commentPane.add(comentHeaderPane, BorderLayout.NORTH);
		comentHeaderPane.setLayout(new BorderLayout(0, 0));
		
		JLabel commentLabel = new JLabel("SHOW / HIDE COMMENTS");
		commentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		commentLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		commentLabel.setForeground(Color.WHITE);
		comentHeaderPane.add(commentLabel, BorderLayout.WEST);
		
		JLabel newCommentLabel = new JLabel("ADD A COMMENT");
		newCommentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newCommentLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		newCommentLabel.setForeground(Color.WHITE);
		comentHeaderPane.add(newCommentLabel, BorderLayout.EAST);
		
		JTextArea reviewContentPane = new JTextArea();
		reviewContentPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		reviewContentPane.setLineWrap(true);
		reviewContentPane.setWrapStyleWord(true);
		reviewContentPane.setEditable(false);
		
		String ReviewText;
		if (loadedUser == null) { // game
			ReviewText = String.valueOf(review.get(3));
		} else {
			ReviewText = String.valueOf(review.get(4));
		}
		reviewContentPane.setBackground(new Color(27, 40, 56));
		reviewContentPane.setForeground(Color.WHITE);
		reviewContentPane.setFont(new Font("MS Song", Font.PLAIN, 32));
		reviewContentPane.setText(ReviewText);
		reviewPane.add(reviewContentPane, BorderLayout.CENTER);
		
		// when started
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				commentScrollPane.getVerticalScrollBar().setValue(0);
				commentScrollPane.getHorizontalScrollBar().setValue(0);
				reviewPane.setPreferredSize(null);
				reviewPane.setPreferredSize(new Dimension(0, (int) reviewPane.getPreferredSize().getHeight() + 20));
			}
		});

		// when resized
		reviewPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				reviewPane.setPreferredSize(null);
				reviewPane.setPreferredSize(new Dimension(0, (int) reviewPane.getPreferredSize().getHeight() + 20));
			}
		});

		// when comments shown
		commentLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (forumPane.isVisible()) {
					forumPane.setVisible(false);
					
				} else {
					commentScrollPane.setPreferredSize(null);
					int offset = 70;
					int maxHeight = Math.max(500, (int) (reviewPane.getSize().getHeight()
							- reviewContentPane.getPreferredSize().getHeight() - offset));
					if (commentScrollPane.getPreferredSize().getHeight() > maxHeight) {
						commentScrollPane.setPreferredSize(new Dimension(0, maxHeight));
					}
					forumPane.setVisible(true);
				}
				reviewPane.setPreferredSize(null);
				reviewPane.setPreferredSize(new Dimension(0, (int) reviewPane.getPreferredSize().getHeight() + 20));
			}
		});
		
		newCommentLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (GUIMain.usernameLoggedIn != null) {
					
					if(User.isBanned(GUIMain.usernameLoggedIn))
					{
						JOptionPane.showMessageDialog(null,
								"You cannot comment because you are banned.",
								"Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						String comment;
						
						//Prompt user for comment
						comment = JOptionPane.showInputDialog(null,
								"Enter your Comment:",
								"Add Comment",
								JOptionPane.PLAIN_MESSAGE);

						//Keep prompting till user puts a thing
						while(comment != null && comment.equals(""))
						{
							JOptionPane.showMessageDialog(null,
									"Please enter a comment.",
									"ERROR",
									JOptionPane.WARNING_MESSAGE);

							comment = JOptionPane.showInputDialog(null,
									"Enter your Comment:",
									"Add Comment",
									JOptionPane.PLAIN_MESSAGE);
						}
						
						if(comment != null)
						{
							if(loadedUser == null)
							{
								Review.addCommentToUserReview(UsersImpl.getUser(GUIMain.usernameLoggedIn), comment, UsersImpl.getUser(name), game);
								drawReviews();
							}
							else
							{
								newCommentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								Review.addCommentToUserReview(UsersImpl.getUser(GUIMain.usernameLoggedIn), comment, UsersImpl.getUser(GUIMain.usernameLoggedIn), game);
								drawReviews();
							}
						}

					}		
				} else {
					JOptionPane.showMessageDialog(null,
							"Please login to comment.",
							"Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
				
		});
		
		reviewBox.add(reviewPane);
	}
	
	//draws each comment
	private static void comment(Box commentBox, Object comment, int i) {
		@SuppressWarnings("unchecked")
		List<Object> comments = (List<Object>) comment;
		
		JPanel commentPane = new JPanel();
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel commentHeaderPane = new JPanel();
		commentHeaderPane.setBackground(new Color(23, 26, 33));
		commentPane.add(commentHeaderPane, BorderLayout.NORTH);
		commentHeaderPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		String userName = (String) comments.get(i);
		JLabel usernameLabel = new JLabel(userName);
		usernameLabel.setFont(new Font("MS Song", Font.PLAIN, 24));
		usernameLabel.setForeground(Color.WHITE);
		commentHeaderPane.add(usernameLabel);
		
		String message = (String) comments.get(i+1);
		JTextArea commentContentPane = new JTextArea();
		commentContentPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		commentContentPane.setLineWrap(true);
		commentContentPane.setWrapStyleWord(true);
		commentContentPane.setEditable(false);
		commentContentPane.setBackground(new Color(27, 40, 56));
		commentContentPane.setForeground(Color.WHITE);
		commentContentPane.setFont(new Font("MS Song", Font.PLAIN, 24));
		commentContentPane.setText(message);
		commentPane.add(commentContentPane, BorderLayout.CENTER);
		
		commentBox.add(commentPane);
	}
	
	public static List<Object> testSort(boolean order, int filter, boolean game, ArrayList<Object> arr){
		chronOrder = order;
		recommendSort = filter;
		if (game) {
			loadedUser = null;
		} else {
			String testUser = "";
			loadedUser = testUser;
		}
		List<Object> sortedArr = sort(arr);
		return sortedArr;
	}
}
