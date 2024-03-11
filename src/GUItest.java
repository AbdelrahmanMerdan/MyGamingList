package src;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class GUItest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUItest frame = new GUItest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUItest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(20, 20, 20, 20));

		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		//generate container
		Box reviewBox = new Box(1);
		
		//fill container with reviews (ideally we fill an array with reviews and iterate through it, this would allow the code to work for both users and games)
		
		List<Object> reviews = GUIGame.game.getComment();
		
		for( int i = 0; i < reviews.size(); i++) {
			
			@SuppressWarnings("unchecked")
			String review = (String) ((List<Object>) reviews.get(i)).get(3);
			
			review(reviewBox, review);
			
		}
		
		
		
		
		JScrollPane reviewScrollPane = generateScrollable(reviewBox);
		mainPane.add(reviewScrollPane);
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
	
	private void review(Box reviewBox, String Review) {
		reviewBox.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel reviewPane = new JPanel();
		reviewPane.setLayout(new BorderLayout(0, 0));
		
		JPanel reviewHeaderPane = new JPanel();
		reviewPane.add(reviewHeaderPane, BorderLayout.NORTH);
		reviewHeaderPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel usernameLabel = new JLabel("USERNAME");
		reviewHeaderPane.add(usernameLabel);
		
		JLabel reccomendLabel = new JLabel("RECCOMENDED");
		reccomendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reviewHeaderPane.add(reccomendLabel);
		
		JLabel scoreLabel = new JLabel("SCORE");
		scoreLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		reviewHeaderPane.add(scoreLabel);
		
		JPanel commentPane = new JPanel();
		reviewPane.add(commentPane, BorderLayout.SOUTH);
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel forumPane = new JPanel();
		commentPane.add(forumPane);
		forumPane.setLayout(new BorderLayout(0, 0));
		
		forumPane.setVisible(false);

		//create container
		Box commentBox = new Box(1);
		
		
//		List<Object> reviews = GUIGame.game.getComment();
		
		
//		for( int i = 0; i < reviews.size(); i++) {
//			
//			@SuppressWarnings("unchecked")
//			String review = (String) ((List<Object>) reviews.get(i)).get(3);
//			
//			review(reviewBox, review);
//			
//		}
		
		//fill comtainer (this should be filled using the comment data from the database in a similar manner as written above)
//		comment(commentBox);
//		comment(commentBox);
//		comment(commentBox);
//		comment(commentBox);
		
		JScrollPane commentScrollPane = new JScrollPane(commentBox);
		forumPane.add(commentScrollPane);
		
		JPanel comentHeaderPane = new JPanel();
		commentPane.add(comentHeaderPane, BorderLayout.NORTH);
		comentHeaderPane.setLayout(new BorderLayout(0, 0));
		
		JLabel commentLabel = new JLabel("SHOW / HIDE COMMENTS");
		comentHeaderPane.add(commentLabel, BorderLayout.WEST);
		
		JLabel newComentLabel = new JLabel("Add a Comment");
		comentHeaderPane.add(newComentLabel, BorderLayout.EAST);
		
		JTextPane reviewContentPane = new JTextPane();
		reviewContentPane.setEditable(false);
		
		reviewContentPane.setText(Review);
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
		
		newComentLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("new comment");
			}
		});
		
		reviewBox.add(reviewPane);
	}
	
	private void comment(Box commentBox, String Comment) {
		JPanel commentPane = new JPanel();
		commentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel commentHeaderPane = new JPanel();
		commentPane.add(commentHeaderPane, BorderLayout.NORTH);
		commentHeaderPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel usernameLabel = new JLabel("USERNAME");
		commentHeaderPane.add(usernameLabel);
		
		JTextPane commentContentPane = new JTextPane();
		commentContentPane.setEditable(false);
		commentContentPane.setText(Comment);
		commentPane.add(commentContentPane, BorderLayout.CENTER);
		
		commentBox.add(commentPane);
	}

}
