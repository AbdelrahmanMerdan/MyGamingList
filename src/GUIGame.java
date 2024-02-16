import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class GUIGame {
	
	private JLabel gameTitleLabel;
	private JLabel gameImageLabel;
	private JTextArea descriptionText;
	private JTextArea bigDescriptionText;
	private JButton reviewGameButton;
	private JButton criticReviewButton;
	
	public GUIGame(JPanel cardPane) {
		
		JPanel gamePane = new JPanel();
		gamePane.setBackground(Color.DARK_GRAY);
		cardPane.add(gamePane, "game");
		gamePane.setLayout(new BorderLayout(0, 0));
		
		JPanel gameHeaderPane = new JPanel();
		gameHeaderPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameHeaderPane, BorderLayout.NORTH);
		gameHeaderPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameHeaderPane.setOpaque(false);
		
		gameTitleLabel = new JLabel("GLOOBERLANTIS");
		gameTitleLabel.setForeground(Color.WHITE);
		gameTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		gameHeaderPane.add(gameTitleLabel);
		
		JPanel gameFooterPane = new JPanel();
		gameFooterPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameFooterPane, BorderLayout.SOUTH);
		gameFooterPane.setOpaque(false);
		
		//JTextArea descriptionText = new JTextArea();
		descriptionText = new JTextArea();
		descriptionText.setForeground(Color.WHITE);
		descriptionText.setBackground(Color.BLACK);
		descriptionText.setWrapStyleWord(true);
		descriptionText.setText("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"");
		descriptionText.setTabSize(20);
		descriptionText.setLineWrap(true);
		descriptionText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		descriptionText.setEditable(false);
		
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionText);
		descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		descriptionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameFooterPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameFooterPane.add(descriptionScrollPane);
		descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel gameImagePane = new JPanel();
		gameImagePane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameImagePane, BorderLayout.WEST);
		gameImagePane.setLayout(new BoxLayout(gameImagePane, BoxLayout.X_AXIS));
		gameImagePane.setOpaque(false);
		
		ImageIcon image = new ImageIcon("src/gloober.png");
		gameImageLabel = new JLabel(image);
		gameImagePane.add(gameImageLabel);
		
		JPanel gameOptionsPane = new JPanel();
		gameOptionsPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameOptionsPane, BorderLayout.EAST);
		gameOptionsPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameOptionsPane.setOpaque(false);
		
		JPanel gameOptionsNorthPane = new JPanel();
		gameOptionsPane.add(gameOptionsNorthPane);
		gameOptionsNorthPane.setLayout(new BorderLayout(0, 0));
		
		//unfinished buttons
		reviewGameButton = new JButton("Review Game");
		reviewGameButton.setFocusable(false);
		reviewGameButton.setForeground(Color.WHITE);
		reviewGameButton.setBackground(Color.BLACK);
		gameOptionsNorthPane.add(reviewGameButton, BorderLayout.CENTER);
		
		JPanel gameOptionsSouthPane = new JPanel();
		gameOptionsPane.add(gameOptionsSouthPane);
		gameOptionsSouthPane.setLayout(new BorderLayout(0, 0));
		
		criticReviewButton = new JButton("Critic Reviews");
		criticReviewButton.setFocusable(false);
		criticReviewButton.setForeground(Color.WHITE);
		criticReviewButton.setBackground(Color.BLACK);
		gameOptionsSouthPane.add(criticReviewButton, BorderLayout.CENTER);
		
		JPanel gameDescriptionPane = new JPanel();
		gameDescriptionPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameDescriptionPane, BorderLayout.CENTER);
		gameDescriptionPane.setOpaque(false);
		
		bigDescriptionText = new JTextArea();
		bigDescriptionText.setForeground(Color.WHITE);
		bigDescriptionText.setBackground(Color.BLACK);
		bigDescriptionText.setWrapStyleWord(true);
		bigDescriptionText.setText("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"");
		bigDescriptionText.setTabSize(20);
		bigDescriptionText.setLineWrap(true);
		bigDescriptionText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		bigDescriptionText.setEditable(false);
		
		JScrollPane bigDescriptionScrollPane = new JScrollPane(bigDescriptionText);
		bigDescriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bigDescriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bigDescriptionScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		bigDescriptionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameDescriptionPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameDescriptionPane.add(bigDescriptionScrollPane);
		bigDescriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
	}
	
	public void loadGame(int appID) {
		gameTitleLabel.setText("");
		descriptionText.setText("");
		//gameImageLabel.setIcon();
		bigDescriptionText.setText("");
	}
}
