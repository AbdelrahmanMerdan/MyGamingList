package src;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import javax.swing.JButton;

public class GUIMyFriends extends JFrame {
	
	public GUIMyFriends(JPanel cardPane) {
		
		//Set up base pane
		JPanel myFriendsPane = new JPanel();
		myFriendsPane.setForeground(Color.BLACK); 
		myFriendsPane.setBackground(Color.BLACK);
		myFriendsPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(64, 64, 64)));
        myFriendsPane.setLayout(new BorderLayout(0, 0));
        cardPane.add(myFriendsPane, "myFriends");
        
        //Set up games pane
        JPanel friendsPane = new JPanel();
		myFriendsPane.add(friendsPane);
		friendsPane.setLayout(new BoxLayout(friendsPane, BoxLayout.Y_AXIS));
		
		//Populate games pane
		Box friendsBox = new Box(1);
		
		for (int i = 0; i < 100; i++) {
            friendsBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel("Friend "+i);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lbl.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
//					GUIGame.loadGame(popReleases.getGame(game));
//					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
				}            	
            });
            friendsBox.add(lbl);
        }
		
		//Set up scroll feature
		JScrollPane friendsScrollPane = new JScrollPane(friendsBox);
		friendsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		friendsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar highRatedVertical = friendsScrollPane.getVerticalScrollBar();
		highRatedVertical.setPreferredSize( new Dimension(0,0) );
		friendsScrollPane.setPreferredSize(new Dimension(0,0));
		friendsPane.add(friendsScrollPane);
		friendsScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		friendsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JLabel myFriendsLabel = new JLabel("My Friends");
		myFriendsLabel.setForeground(Color.WHITE);
		myFriendsLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		myFriendsPane.add(myFriendsLabel, BorderLayout.NORTH);
		
		JButton addFriendButton = new JButton("Add Friend");
		addFriendButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		myFriendsPane.add(addFriendButton, BorderLayout.SOUTH);
		friendsScrollPane.getViewport().setBackground(Color.BLACK);
		
	}
}
