package mygaminglist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class GUINewUserReview extends JDialog{
	
	public GUINewUserReview(JPanel cardPane, Game game) {
		setTitle("New Review");
		setBounds(100, 100, 600, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(960, 540)); //Old Resolution: 890, 507
		setLocationRelativeTo(null);

		JPanel mainPane = new JPanel();
		mainPane.setBackground(new Color(23, 26, 33));
		mainPane.setBorder(new EmptyBorder(0, 20, 0, 20));
		mainPane.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPane);
		
		JPanel headerPane = new JPanel();
		headerPane.setBackground(new Color(23, 26, 33));
		mainPane.add(headerPane, BorderLayout.NORTH);
		headerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel recommendPane = new JPanel();
		recommendPane.setBackground(new Color(23, 26, 33));
		headerPane.add(recommendPane);
		
		JCheckBox recommendCheckBox = new JCheckBox("Would You Recommend This Game to Others?");
		recommendCheckBox.setBackground(new Color(23, 26, 33));
		recommendCheckBox.setForeground(Color.WHITE);
		recommendCheckBox.setFont(new Font("MS Song", Font.PLAIN, 20));
		recommendCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		recommendCheckBox.setFocusable(false);
		recommendPane.add(recommendCheckBox);
		
		JPanel ratingPane = new JPanel();
		ratingPane.setBackground(new Color(23, 26, 33));
		headerPane.add(ratingPane);
		
		JLabel ratingPromptLabel = new JLabel("Please Provide a Rating Out of 10");
		ratingPromptLabel.setForeground(Color.WHITE);
		ratingPromptLabel.setFont(new Font("MS Song", Font.PLAIN, 20));
		ratingPromptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ratingPane.add(ratingPromptLabel);
		
		JSpinner ratingSpinner = new JSpinner();
		ratingSpinner.setFont(new Font("MS Song", Font.PLAIN, 20));
		ratingSpinner.setModel(new SpinnerNumberModel(5, 1, 10, 1));
		ratingPane.add(ratingSpinner);
		
		JPanel footerPane = new JPanel();
		footerPane.setBackground(new Color(23, 26, 33));
		mainPane.add(footerPane, BorderLayout.SOUTH);
		
		JButton finalizeReviewButton = new JButton("Finalize Review");
		finalizeReviewButton.setBackground(new Color(23, 26, 33));
		finalizeReviewButton.setOpaque(true);
		finalizeReviewButton.setForeground(Color.WHITE);
		finalizeReviewButton.setFont(new Font("MS Song", Font.PLAIN, 20));
		footerPane.add(finalizeReviewButton);
		
		JTextArea reviewTextArea = new JTextArea();
		reviewTextArea.setBackground(new Color(27, 40, 56));
		reviewTextArea.setForeground(Color.WHITE);
		reviewTextArea.setFont(new Font("MS Song", Font.PLAIN, 20));
		reviewTextArea.setCaretColor(Color.WHITE);
		reviewTextArea.setLineWrap(true);
		reviewTextArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(reviewTextArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainPane.add(scrollPane);
		
		//finalize button listener									// needs database implementation, should prolly also refresh the page (don't worry much about that if you can't)
		finalizeReviewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				String recommended = "No";
				
				if(recommendCheckBox.isSelected()) {
					recommended ="Yes";
				}
				
				int Score = (Integer)ratingSpinner.getValue();
				
				String comment = reviewTextArea.getText();
				
				
				try {
					Review.review_game(GUIMain.usernameLoggedIn, game, Score, comment, recommended);
					GUIGameReviews.updateGame(game);
					((CardLayout) cardPane.getLayout()).show(cardPane, "reviews");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				dispose();
			}
		});
	}
	
}
