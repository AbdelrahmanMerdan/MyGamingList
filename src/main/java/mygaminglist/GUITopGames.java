package mygaminglist;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
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

import database.GameData;

public class GUITopGames extends JFrame {
	
	public GUITopGames(JPanel cardPane, TopGames topGames) {
		
		JPanel TopGameMenuPane = new JPanel();
		TopGameMenuPane.setForeground(Color.BLACK);
		TopGameMenuPane.setBackground(new Color(27, 40, 56));
		TopGameMenuPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(27, 40, 56)));
		
		cardPane.add(TopGameMenuPane, "topGames");
		
		TopGameMenuPane.setLayout(new GridLayout(1, 0, 100, 0));
		
		JPanel TopGamePane = new JPanel();
		TopGameMenuPane.add(TopGamePane);
		TopGamePane.setLayout(new BoxLayout(TopGamePane, BoxLayout.Y_AXIS));
		
		// populate pane
		Box TopGameBox = new Box(1);
		TopGameBox.setBackground(new Color(23, 26, 33));
		JLabel TopGamesLabel = new JLabel("Top Games");
		TopGamesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TopGamesLabel.setForeground(Color.WHITE);
		TopGamesLabel.setFont(new Font("Verdana", Font.BOLD, 50));
		TopGameBox.add(TopGamesLabel);
		
		for (int i = 0; i < topGames.getSize(); i++) {
			int game = i;
            TopGameBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            
            JPanel lblpanel = new JPanel();
            lblpanel.setBackground(new Color(23, 26, 33));
            lblpanel.setLayout(new BorderLayout(0,0));
            
            JLabel lbl = new JLabel("Rank: "+(i+1)+GameData.getName(topGames.getID(game)));
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("MS Song", Font.PLAIN, 32));
            lbl.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				lbl.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    				GUIGame.loadGame(topGames.getID(game));
					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
					lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    			}
    		
    			
    		});
            
            JLabel scorelbl = new JLabel("Overall User Score: "+GameData.getAverageofusers(topGames.getID(game)));
            scorelbl.setForeground(Color.WHITE);
            scorelbl.setFont(new Font("MS Song", Font.PLAIN, 32));
            scorelbl.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
            
            lblpanel.add(lbl, BorderLayout.WEST);
            lblpanel.add(scorelbl, BorderLayout.EAST);
            
            
            TopGameBox.add(lblpanel);

        }
		
		JScrollPane TopGameScrollPane = new JScrollPane(TopGameBox);
		TopGameScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		TopGameScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar mostPlayedVertical = TopGameScrollPane.getVerticalScrollBar();
		mostPlayedVertical.setPreferredSize( new Dimension(0,0) );
		TopGameScrollPane.setPreferredSize(new Dimension(0,0));
		TopGamePane.add(TopGameScrollPane);
		TopGameScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        TopGameScrollPane.setBorder(BorderFactory.createEmptyBorder());
        TopGameScrollPane.getViewport().setBackground(new Color(23, 26, 33));

		
	}

}
