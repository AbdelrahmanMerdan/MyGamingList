package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class GUINewUserReview extends JDialog{
	
	public GUINewUserReview(Game game) {
		setTitle("New Review");
		setBounds(100, 100, 600, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(0, 20, 0, 20));
		mainPane.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPane);
		
		JPanel headerPane = new JPanel();
		mainPane.add(headerPane, BorderLayout.NORTH);
		headerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel recommendPane = new JPanel();
		headerPane.add(recommendPane);
		
		JCheckBox recommendCheckBox = new JCheckBox("Would You Recommend This Game to Others");
		recommendCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		recommendCheckBox.setFocusable(false);
		recommendPane.add(recommendCheckBox);
		
		JPanel ratingPane = new JPanel();
		headerPane.add(ratingPane);
		
		JLabel ratingPromptLabel = new JLabel("Please Provide a Rating Out of 10");
		ratingPromptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ratingPane.add(ratingPromptLabel);
		
		JSpinner ratingSpinner = new JSpinner();
		ratingSpinner.setModel(new SpinnerNumberModel(5, 1, 10, 1));
		ratingPane.add(ratingSpinner);
		
		JPanel footerPane = new JPanel();
		mainPane.add(footerPane, BorderLayout.SOUTH);
		
		JButton finalizeReviewButton = new JButton("Finalize Review");
		footerPane.add(finalizeReviewButton);
		
		JTextPane reviewTextPane = new JTextPane();
		mainPane.add(reviewTextPane);
		
		//finalize button listener									// needs database implementation, should prolly also refresh the page (don't worry much about that if you can't)
		finalizeReviewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(GUIMain.usernameLoggedIn + " " + game.getName() + " " + recommendCheckBox.isSelected() + " " + ratingSpinner.getValue() + " " + reviewTextPane.getText());
				dispose();
			}
		});
	}
	
}
