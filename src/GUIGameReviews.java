package src;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import database.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class GUIGameReviews extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Box reviewBox;
	private static JLabel reviewTitleLabel;
	private static String backLocation;
	//private static JButton newReviewButton;
	private static JPanel buttonPanel;

	/**
	 * Create the panel.
	 */
	public GUIGameReviews(JPanel cardPane) {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout(0, 0));
		mainPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) Color.DARK_GRAY));
		cardPane.add(mainPane, "reviews");
		
		//generate container
		reviewBox = new Box(1);
		reviewBox.setBorder(new MatteBorder(0, 10, 10, 10, (Color) new Color(0, 0, 0)));
		
		//generate scrollable
		JScrollPane reviewScrollPane = generateScrollable(reviewBox);
		mainPane.add(reviewScrollPane, BorderLayout.CENTER);
		
		//this  rest is mostly formatting and shit
		JPanel ReviewHeaderPane = new JPanel();
		ReviewHeaderPane.setBackground(Color.BLACK);
		mainPane.add(ReviewHeaderPane, BorderLayout.NORTH);
		ReviewHeaderPane.setLayout(new BorderLayout(0, 0));
		
		reviewTitleLabel = new JLabel("GAME/USER");
		reviewTitleLabel.setForeground(Color.WHITE);
		reviewTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		reviewTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ReviewHeaderPane.add(reviewTitleLabel, BorderLayout.WEST);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(0, 0));
		ReviewHeaderPane.add(buttonPanel, BorderLayout.EAST);
		
		JButton backButton = new JButton("  BACK  ");
		buttonPanel.add(backButton, BorderLayout.EAST);
		backButton.setFocusable(false);
		
		//listener for back button, changes between targets
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, backLocation);
			}
		});
	}
	
	public static void loadGameReviews(JPanel cardPane, Game game) {
		//reset everything
		new GUIGameReviews(cardPane);
		
		//Fill the box
		List<Object> reviews = GUIGame.game.getComment();

		for( int i = 0; i < reviews.size(); i++) {
			review(reviews.get(i));
		}
		
		//setup misc.
		reviewTitleLabel.setText(game.getName());
		backLocation = "game";
		//newReviewButton.setVisible(true);
		
		JButton newReviewButton = new JButton("REVIEW");
		buttonPanel.add(newReviewButton, BorderLayout.WEST);
		newReviewButton.setFocusable(false);
		
		//listener for new review button
		newReviewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (GUIMain.usernameLoggedIn != null) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								GUINewUserReview reviewFrame = new GUINewUserReview(game);
								reviewFrame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				else {
					JOptionPane.showMessageDialog(buttonPanel,
							"Error, Not Logged In");
				}
			}
		});
	}
	
	public static void loadUserReviews(JPanel cardPane, String user) { //ideally this would be a User not a string but /shrug as long as it works (UsersImpl methods should be static i think)
		//reset everything
		new GUIGameReviews(cardPane);
		
//		//fill the box
//		review(); // same as above
//		review();
//		review();
//		review();
		
		//setup misc.
		reviewTitleLabel.setText(user);
		backLocation = "mainMenu";
		//newReviewButton.setVisible(false);
	}
	
	private JScrollPane generateScrollable(Box box) {
		//set up the scroll pane
		JScrollPane reviewScrollPane = new JScrollPane(box);
		reviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reviewScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar reviewScrollVertical = reviewScrollPane.getVerticalScrollBar();
		reviewScrollVertical.setPreferredSize(new Dimension(0,0));
		reviewScrollPane.setPreferredSize(new Dimension(400,0));
		reviewScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		reviewScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		//scroll to top
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				reviewScrollVertical.setValue(0);
			}
		});

		return reviewScrollPane;
	}
	
	private static void review(Object reviews) {
		//reviewBox.add(Box.createRigidArea(new Dimension(0, 20)));
		
		@SuppressWarnings("unchecked")
		List<Object> review = (List<Object>) reviews;
		
		JPanel reviewPane = new JPanel();
		reviewPane.setBackground(Color.BLACK);
		reviewPane.setLayout(new BorderLayout(0, 0));
		
		JPanel reviewHeaderPane = new JPanel();
		reviewHeaderPane.setBorder(new MatteBorder(10, 0, 10, 0, (Color) new Color(0, 0, 0)));
		reviewHeaderPane.setBackground(Color.BLACK);
		reviewPane.add(reviewHeaderPane, BorderLayout.NORTH);
		reviewHeaderPane.setLayout(new BorderLayout(0, 0));
		
		String Username = (String) review.get(0);
		JLabel nameLabel = new JLabel(Username);							// set this to the username or game name
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLabel.setForeground(Color.WHITE);
		reviewHeaderPane.add(nameLabel, BorderLayout.WEST);
		
		String Reccommended = (String) review.get(2);
		JLabel reccomendLabel = new JLabel("RECCOMENDED: "+ Reccommended);					// set this to the yes/no reccomended
		reccomendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reccomendLabel.setForeground(Color.WHITE);
		reccomendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reviewHeaderPane.add(reccomendLabel, BorderLayout.CENTER);
		
		String Score = String.valueOf(review.get(1));
		JLabel scoreLabel = new JLabel("SCORE: " + Score);							// set this to the score
		scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		reviewHeaderPane.add(scoreLabel, BorderLayout.EAST);
		
		JPanel commentPane = new JPanel();
		reviewPane.add(commentPane, BorderLayout.SOUTH);
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel forumPane = new JPanel();
		commentPane.add(forumPane);
		forumPane.setLayout(new BorderLayout(0, 0));
		
		forumPane.setVisible(false);

		//create container
		Box commentBox = new Box(1);
		
		//Fill comment box
		
		@SuppressWarnings("unchecked")
		List<Object> comments = (List<Object>) ((List<Object>) reviews).get(4);
		
		for(int i = 0; i < comments.size(); i +=2)
		{
			comment(commentBox, comments, i);
		}
		
//		//fill comtainer (this should be filled using the comment data from the database in a similar manner as written above)
//		comment(commentBox); // needs to pass comment information to comment(usernames and comments)
//		comment(commentBox);
//		comment(commentBox);
//		comment(commentBox);
		
		JScrollPane commentScrollPane = new JScrollPane(commentBox);
		forumPane.add(commentScrollPane);
		
		JPanel comentHeaderPane = new JPanel();
		comentHeaderPane.setBackground(Color.LIGHT_GRAY);
		commentPane.add(comentHeaderPane, BorderLayout.NORTH);
		comentHeaderPane.setLayout(new BorderLayout(0, 0));
		
		JLabel commentLabel = new JLabel("SHOW / HIDE COMMENTS");
		comentHeaderPane.add(commentLabel, BorderLayout.WEST);
		
		JLabel newComentLabel = new JLabel("Add a Comment");
		comentHeaderPane.add(newComentLabel, BorderLayout.EAST);
		
		JTextPane reviewContentPane = new JTextPane();
		reviewContentPane.setEditable(false);
		
		String ReviewText = String.valueOf(review.get(3));
		reviewContentPane.setText(ReviewText);	// set this to the review text
		reviewPane.add(reviewContentPane, BorderLayout.CENTER);
		
		commentLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (forumPane.isVisible()){
					forumPane.setVisible(false);
				} else {
					forumPane.setVisible(true);
				}
			}
		});
		
		//comment prompt
		newComentLabel.addMouseListener(new MouseAdapter() {					// needs to be implemented, comment needs to be saved to db alongside username
			public void mouseClicked(MouseEvent e) {
				if (GUIMain.usernameLoggedIn != null) {
					String comment;
					
					//Prompt user for comment
					comment = JOptionPane.showInputDialog(reviewContentPane,
							"Enter your Comment:",
							"Add Comment",
							JOptionPane.PLAIN_MESSAGE);
	
					//Keep prompting till user puts a thing
					while(comment != null && comment.equals(""))
					{
						JOptionPane.showMessageDialog(reviewContentPane,
								"Please enter a comment",
								"Error",
								JOptionPane.PLAIN_MESSAGE);
						
						comment = JOptionPane.showInputDialog(reviewContentPane,
								"Enter your Comment:",
								"Add Comment",
								JOptionPane.PLAIN_MESSAGE);
					}
					
					if(comment != null)
					{
						Review.addCommentToUserReview(UsersImpl.getUser(GUIMain.usernameLoggedIn), comment, UsersImpl.getUser(Username), GUIGame.game);
					}
				} else {
					JOptionPane.showMessageDialog(reviewContentPane,
							"Please Login to Comment",
							"Error",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		reviewBox.add(reviewPane);
	}
	
	private static void comment(Box commentBox, Object comment, int i) {
		@SuppressWarnings("unchecked")
		List<Object> comments = (List<Object>) comment;
		
		JPanel commentPane = new JPanel();
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel commentHeaderPane = new JPanel();
		commentHeaderPane.setBackground(Color.LIGHT_GRAY);
		commentPane.add(commentHeaderPane, BorderLayout.NORTH);
		commentHeaderPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		String userName = (String) comments.get(i);
		JLabel usernameLabel = new JLabel(userName);						// set this to the username
		usernameLabel.setForeground(Color.BLACK);
		commentHeaderPane.add(usernameLabel);
		
		String message = (String) comments.get(i+1);
		JTextPane commentContentPane = new JTextPane();
		commentContentPane.setEditable(false);
		commentContentPane.setText(message);						// set this to the comment text
		commentPane.add(commentContentPane, BorderLayout.CENTER);
		
		commentBox.add(commentPane);
	}
}