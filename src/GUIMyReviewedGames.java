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

public class GUIMyReviewedGames extends JFrame {
	
	private JLabel reviewedGamesLabel;
	
	public GUIMyReviewedGames(JPanel cardPane) {
		
		//Set up base pane
		JPanel myReviewedGamesPane = new JPanel();
		myReviewedGamesPane.setForeground(Color.BLACK); 
		myReviewedGamesPane.setBackground(Color.BLACK);
		myReviewedGamesPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(64, 64, 64)));
        myReviewedGamesPane.setLayout(new BorderLayout(0, 0));
        cardPane.add(myReviewedGamesPane, "myReviewedGames");
        
        //Set up games pane
        JPanel gamesPane = new JPanel();
		myReviewedGamesPane.add(gamesPane);
		gamesPane.setLayout(new BoxLayout(gamesPane, BoxLayout.Y_AXIS));
		
		//Populate games pane
		Box gamesBox = new Box(1);
		
		for (int i = 0; i < 100; i++) {
            gamesBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel("Game "+i);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lbl.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
//					GUIGame.loadGame(popReleases.getGame(game));
//					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
				}            	
            });
            gamesBox.add(lbl);
        }
		
		//Set up scroll feature
		JScrollPane gamesScrollPane = new JScrollPane(gamesBox);
		gamesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		gamesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar highRatedVertical = gamesScrollPane.getVerticalScrollBar();
		highRatedVertical.setPreferredSize( new Dimension(0,0) );
		gamesScrollPane.setPreferredSize(new Dimension(0,0));
		gamesPane.add(gamesScrollPane);
		gamesScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gamesScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		reviewedGamesLabel = new JLabel("My Reviewed Games");
		reviewedGamesLabel.setForeground(Color.WHITE);
		reviewedGamesLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		myReviewedGamesPane.add(reviewedGamesLabel, BorderLayout.NORTH);
		gamesScrollPane.getViewport().setBackground(Color.BLACK);
		
	}
	
	public void setReviewedGamesLabel(String txt)
	{
		reviewedGamesLabel.setText(txt);
	}
	

}
