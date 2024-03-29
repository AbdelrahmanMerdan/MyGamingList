package mygaminglist;

import java.awt.CardLayout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import javax.swing.JButton;

import database.UsersImpl;

public class GUIMyFriends extends JFrame {
	
    private UsersImpl users = new UsersImpl();
	
	public GUIMyFriends(JPanel cardPane, String usernameLoggedIn) {
		
		//Set up base pane
		JPanel myFriendsPane = new JPanel();
		myFriendsPane.setBackground(new Color(27, 40, 56));
		myFriendsPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(27, 40, 56)));
        myFriendsPane.setLayout(new BorderLayout(0, 0));
        cardPane.add(myFriendsPane, "myFriends");
        
        //Set up friends pane
        JPanel friendsPane = new JPanel();
		myFriendsPane.add(friendsPane);
		friendsPane.setLayout(new BoxLayout(friendsPane, BoxLayout.Y_AXIS));
		
		//Populate friends pane
		Box friendsBox = new Box(1);
		
		if (usernameLoggedIn != null) {
		    List<String> friends = users.listFriend(usernameLoggedIn);
		    int nonEmptyCount = (int) friends.stream().filter(friend -> !friend.isEmpty()).count();

		    if (nonEmptyCount == 0) {
		        JLabel lbl = new JLabel("Click below to Add a Friend");
		        lbl.setForeground(Color.WHITE);
		        lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		        friendsBox.add(lbl);
		    } else {
		    	for (int i = 0; i < friends.size(); i++) {
		    	    friendsBox.add(Box.createRigidArea(new Dimension(0, 5))); // creates space between the components
		    	    String friendName = friends.get(i);
		    	    JLabel nameLabel = new JLabel(friendName);
		    	    nameLabel.setForeground(Color.WHITE);
		    	    nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		    	    nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		    	    nameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
		    	    nameLabel.setOpaque(true); 
		    	    nameLabel.setBackground(new Color(27, 40, 56));
		    	    nameLabel.addMouseListener(new MouseAdapter() {
		    	        @Override
		    	        public void mouseClicked(MouseEvent e) {
		    	            // Open the friend's reviews page when the name is clicked
		    	            GUIGameReviews.loadUserReviews(cardPane, friendName);
		    	            ((CardLayout) cardPane.getLayout()).show(cardPane, "reviews");
		    	        }
		    	    });
		    	    friendsBox.add(nameLabel);
		    	}
		    }
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
		
		// Create a panel to hold the buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.setBackground(new Color (23, 26, 33));

		// Create "Add Friend" button
		JButton addFriendButton = new JButton("Add Friend");
		addFriendButton.setBackground(new Color(23, 26, 33));
		addFriendButton.setOpaque(true);
		addFriendButton.setForeground(Color.WHITE);
		addFriendButton.setFont(new Font("Verdana", Font.PLAIN, 32));

		// Create "Remove Friend" button
		JButton removeFriendButton = new JButton("Remove Friend");
		removeFriendButton.setBackground(new Color(23, 26, 33));
		removeFriendButton.setOpaque(true);
		removeFriendButton.setForeground(Color.WHITE);
		removeFriendButton.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		// Add buttons to the panel
		buttonsPanel.add(addFriendButton);
		buttonsPanel.add(removeFriendButton);
		myFriendsPane.add(buttonsPanel, BorderLayout.SOUTH);
		friendsScrollPane.getViewport().setBackground(new Color(27, 40, 56));
		
		// This will change later, maybe to look better. Good enough for now
		JLabel myFriendsLabel = new JLabel(usernameLoggedIn + " - My Friends");
		myFriendsLabel.setForeground(Color.WHITE);
		myFriendsLabel.setFont(new Font("MS Song", Font.BOLD, 40));
		myFriendsPane.add(myFriendsLabel, BorderLayout.NORTH);

        // Add action listener for the "Add Friend" button
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Add Friend dialog when the button is clicked
                String friendUsername = JOptionPane.showInputDialog(GUIMyFriends.this,
                        "Enter Friend's Username:",
                        "Add Friend",
                        JOptionPane.PLAIN_MESSAGE);

                // Perform validation check 
                if (friendUsername != null && !friendUsername.isEmpty()) {
                    // Check if the user exists in the database
                    if (users.get(friendUsername) != null) {
//                    	System.out.println(usernameLoggedIn + " : " + friendUsername);
	                    if (usernameLoggedIn.equals(friendUsername)) {
	                    	JOptionPane.showMessageDialog(null, "You cannot friend yourself!");	
	                    }
	                    else {
	                    	// Check if the user is already a friend
	                        if (users.listFriend(usernameLoggedIn).contains(friendUsername)) {
	                            JOptionPane.showMessageDialog(null, "You are already friends!");
	                        } else {
	                            // Add the friend and update the GUI
	                        	// Update GUI works but needs a refresh by going to a different card and then back
	                        	System.out.println("Adding friend: " + friendUsername);
	                            users.updateFriend(usernameLoggedIn, friendUsername, "add");
	                            JOptionPane.showMessageDialog(null, "Friend added successfully!");
	                            GUIMyFriends myFriends = new GUIMyFriends(cardPane, usernameLoggedIn);
	            				((CardLayout) cardPane.getLayout()).show(cardPane, "myFriends");
	                            
	                        }
	                    }
                    } else {
                        JOptionPane.showMessageDialog(null, "User does not exist!");
                    }
                }
            }
        });
	

        removeFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Open the Remove Friend dialog when the button is clicked
                String friendUsername = JOptionPane.showInputDialog(GUIMyFriends.this,
                        "Enter Friend's Username:",
                        "Remove Friend",
                        JOptionPane.PLAIN_MESSAGE);

                if (friendUsername != null && !friendUsername.isEmpty()) {
                    // Check if the user is a friend
                    if (users.listFriend(usernameLoggedIn).contains(friendUsername)) {
                        int confirm = JOptionPane.showOptionDialog(null,
                                "Are you sure you want to remove this friend?",
                                "Confirm Removal",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, null, null);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Remove the friend and update the GUI
                            users.updateFriend(usernameLoggedIn, friendUsername, "remove");
                            JOptionPane.showMessageDialog(null, "Friend removed successfully!");
			    GUIMyFriends myFriends = new GUIMyFriends(cardPane, usernameLoggedIn);
            		    ((CardLayout) cardPane.getLayout()).show(cardPane, "myFriends");					
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "User is not in your friend list!");
                    }
                }
            }
        });
        
	}
}
