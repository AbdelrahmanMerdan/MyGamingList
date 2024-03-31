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

public class GUIModerator extends JFrame {
	
    private UsersImpl users = new UsersImpl();
    BannedUserList banlist;
	
	public GUIModerator(JPanel cardPane, String usernameLoggedIn) {
		
		User moderator = users.getUser(usernameLoggedIn);
		updateban();
		
		//Set up base pane
		JPanel ModeratorPane = new JPanel();
		ModeratorPane.setBackground(new Color(27, 40, 56));
		ModeratorPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(27, 40, 56)));
        ModeratorPane.setLayout(new BorderLayout(0, 0));
        cardPane.add(ModeratorPane, "moderatorTools");
        
        //Set up friends pane
        JPanel bannedusersPane = new JPanel();
        bannedusersPane.setBorder(new MatteBorder(20, 10, 10, 10, (Color) new Color(23, 26, 33)));
		ModeratorPane.add(bannedusersPane);
		bannedusersPane.setLayout(new BoxLayout(bannedusersPane, BoxLayout.Y_AXIS));
		
		//Populate friends pane
		Box banBox = new Box(1);
		
		if (usernameLoggedIn != null) {

		    if (banlist.getSize() == 0) {
		        JLabel lbl = new JLabel("Click below to ban or unban user");
		        lbl.setForeground(Color.WHITE);
		        lbl.setFont(new Font("MS Song", Font.PLAIN, 32));
		        banBox.add(lbl);
		    } else {
		    	for (int i = 0; i < banlist.getSize() ; i++) {
		    		banBox.add(Box.createRigidArea(new Dimension(0, 5))); // creates space between the components
		    	    String bannedName = banlist.getuser(i);
		    	    JLabel nameLabel = new JLabel(bannedName);
		    	    nameLabel.setForeground(Color.WHITE);
		    	    nameLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		    	    nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		    	    nameLabel.setBorder(BorderFactory.createEmptyBorder()); 
		    	    nameLabel.setOpaque(true); 
		    	    nameLabel.setBackground(new Color(27, 40, 56));
		    	    nameLabel.addMouseListener(new MouseAdapter() {
		    	        @Override
		    	        public void mouseClicked(MouseEvent e) {

		    	            	// Open the Remove Friend dialog when the button is clicked
		    	                String unbannedUsername = JOptionPane.showInputDialog(GUIModerator.this,
		    	                        "Enter 'yes' to unban this user:",
		    	                        "Unban User",
		    	                        JOptionPane.PLAIN_MESSAGE);

		    	                if (unbannedUsername != null && !unbannedUsername.isEmpty()) {
		    	                    // Check if the user is a friend
		    	                	if(unbannedUsername.equals("yes") ||unbannedUsername.equals("YES")  ) {
		    	                		 moderator.unbanUser(bannedName);
		    	                		 JOptionPane.showMessageDialog(null, bannedName + " has been unbanned!");
		    	                		 updateban();
		    	                		 GUIModerator moderatorTools = new GUIModerator(cardPane, usernameLoggedIn);
		    	         				((CardLayout) cardPane.getLayout()).show(cardPane, "moderatorTools");
		    	                	}
		    	                	else {
		    	                		JOptionPane.showMessageDialog(null, "User has not been unbanned!");
		    	                	}
		    	                    
		    	                }
		    	        }    
		    	    });
		    	    banBox.add(nameLabel);
		    	}
		    }
		}

		//Set up scroll feature
		JScrollPane friendsScrollPane = new JScrollPane(banBox);
		friendsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		friendsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar highRatedVertical = friendsScrollPane.getVerticalScrollBar();
		highRatedVertical.setPreferredSize( new Dimension(0,0) );
		friendsScrollPane.setPreferredSize(new Dimension(0,0));
		bannedusersPane.add(friendsScrollPane);
		friendsScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		friendsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		// Create a panel to hold the buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.setBackground(new Color (23, 26, 33));

		// Create "Ban User" button
		JButton BanUserButton = new JButton("Ban User");
		BanUserButton.setBackground(new Color(23, 26, 33));
		BanUserButton.setOpaque(true);
		BanUserButton.setForeground(Color.WHITE);
		BanUserButton.setFont(new Font("Verdana", Font.PLAIN, 32));

		// Create "Unban User" button
		JButton UnbanUserButton = new JButton("Unban User");
		UnbanUserButton.setBackground(new Color(23, 26, 33));
		UnbanUserButton.setOpaque(true);
		UnbanUserButton.setForeground(Color.WHITE);
		UnbanUserButton.setFont(new Font("Verdana", Font.PLAIN, 32));



		// Add buttons to the panel
		buttonsPanel.add(BanUserButton);
		buttonsPanel.add(UnbanUserButton);
		ModeratorPane.add(buttonsPanel, BorderLayout.SOUTH);
		friendsScrollPane.getViewport().setBackground(new Color(27, 40, 56));


		
		// This might change later, maybe to look better. Good enough for now
		JLabel ModeratorTools = new JLabel(usernameLoggedIn + " - Moderator Tools");
		ModeratorTools.setForeground(Color.WHITE);
		ModeratorTools.setFont(new Font("MS Song", Font.BOLD, 40));
		ModeratorPane.add(ModeratorTools, BorderLayout.NORTH);

		
		// Add action listener for the "Add Friend" button
        BanUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Add Friend dialog when the button is clicked
                String BannedUsername = JOptionPane.showInputDialog(GUIModerator.this,
                        "Enter the Username you would like to ban:",
                        "Ban User",
                        JOptionPane.PLAIN_MESSAGE);

                // Perform validation check 
                if (BannedUsername != null && !BannedUsername.isEmpty()) {
                    // Check if the user exists in the database
                    if (users.get(BannedUsername) != null) {
//                    	System.out.println(usernameLoggedIn + " : " + BannedUsername);
					
	                    if (usernameLoggedIn.equals(BannedUsername)) {
	                    	JOptionPane.showMessageDialog(null, "You cannot ban yourself!");	
	                    }
	                    
	                    else {
	                    	Boolean ban = moderator.banUser(BannedUsername);
	                    	if(ban) {
	                    		JOptionPane.showMessageDialog(null, "Banned User " + BannedUsername);
	                    		
	                    		updateban();
	   	                		GUIModerator moderatorTools = new GUIModerator(cardPane, usernameLoggedIn);
	   	         				((CardLayout) cardPane.getLayout()).show(cardPane, "moderatorTools");
		                    		
	                    	}
	                    	else {
	                    		JOptionPane.showMessageDialog(null, BannedUsername + " is already banned");
	                    	}
	                    	
	                    }
                    } else {
                        JOptionPane.showMessageDialog(null, "User does not exist!");
                    }
                }
            }
            
        });
	

        UnbanUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Open the Remove Friend dialog when the button is clicked
                String unbannedUsername = JOptionPane.showInputDialog(GUIModerator.this,
                        "Enter Username to unban:",
                        "Unban User",
                        JOptionPane.PLAIN_MESSAGE);

                if (unbannedUsername != null && !unbannedUsername.isEmpty()) {
                    // Check if the user is a friend
                	
                	Boolean unban = moderator.unbanUser(unbannedUsername);
                	if(unban) {
                		JOptionPane.showMessageDialog(null, "User has been unbanned!");
                		
                		updateban();
               		 	GUIModerator moderatorTools = new GUIModerator(cardPane, usernameLoggedIn);
        				((CardLayout) cardPane.getLayout()).show(cardPane, "moderatorTools");
                	}
                	else {
                		JOptionPane.showMessageDialog(null, "User has not been unbanned!");
                	}
                    
                }
            }
        });
        
	}
	
	private void updateban() {
		banlist.usernames =new ArrayList<>();
		banlist = new BannedUserList();
		
	}
}

