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

public class GUIMainMenu extends JFrame {
	
	public GUIMainMenu(JPanel cardPane, MostPlayed mostPlayed, PopReleases popReleases) {
		
		JPanel mainMenuPane = new JPanel();
		mainMenuPane.setForeground(Color.BLACK); // useless?
		mainMenuPane.setBackground(Color.DARK_GRAY);
		mainMenuPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(64, 64, 64)));
		cardPane.add(mainMenuPane, "mainMenu");
		mainMenuPane.setLayout(new GridLayout(1, 0, 100, 0));
		
		JPanel mostPlayedPane = new JPanel();
		mainMenuPane.add(mostPlayedPane);
		mostPlayedPane.setLayout(new BoxLayout(mostPlayedPane, BoxLayout.Y_AXIS));
		
		// populate pane
		Box mostPlayedBox = new Box(1);
		JLabel mostPlayedLabel = new JLabel("Most Played");
		mostPlayedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mostPlayedLabel.setForeground(Color.WHITE);
		mostPlayedLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		mostPlayedBox.add(mostPlayedLabel);
		
		for (int i = 0; i < mostPlayed.getSize(); i++) {
			int game = i;
            mostPlayedBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel(mostPlayed.getGame(game).getName());
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lbl.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				GUIGame.loadGame(mostPlayed.getGame(game));
					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
    			}
    		});
            mostPlayedBox.add(lbl);
        }
		
		JScrollPane mostPlayedScrollPane = new JScrollPane(mostPlayedBox);
		mostPlayedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mostPlayedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar mostPlayedVertical = mostPlayedScrollPane.getVerticalScrollBar();
		mostPlayedVertical.setPreferredSize( new Dimension(0,0) );
		mostPlayedScrollPane.setPreferredSize(new Dimension(0,0));
		mostPlayedPane.add(mostPlayedScrollPane);
		mostPlayedScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mostPlayedScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mostPlayedScrollPane.getViewport().setBackground(Color.BLACK);
		
        JPanel popularReleasePane = new JPanel();
		mainMenuPane.add(popularReleasePane);
		popularReleasePane.setLayout(new BoxLayout(popularReleasePane, BoxLayout.Y_AXIS));
		
		// populate pane
		Box popularReleaseBox = new Box(1);
		JLabel popularReleaseLabel = new JLabel("Popular Releases");
		popularReleaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		popularReleaseLabel.setForeground(Color.WHITE);
		popularReleaseLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		popularReleaseBox.add(popularReleaseLabel);
		
		for (int i = 0; i < popReleases.getSize(); i++) {
			int game = i;
            popularReleaseBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel(popReleases.getGame(game).getName());
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lbl.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					GUIGame.loadGame(popReleases.getGame(game));
					((CardLayout) cardPane.getLayout()).show(cardPane, "game");
				}            	
            });
            popularReleaseBox.add(lbl);
        }
		
		JScrollPane popularReleaseScrollPane = new JScrollPane(popularReleaseBox);
		popularReleaseScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		popularReleaseScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar highRatedVertical = popularReleaseScrollPane.getVerticalScrollBar();
		highRatedVertical.setPreferredSize( new Dimension(0,0) );
		popularReleaseScrollPane.setPreferredSize(new Dimension(0,0));
		popularReleasePane.add(popularReleaseScrollPane);
		popularReleaseScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		popularReleaseScrollPane.setBorder(BorderFactory.createEmptyBorder());
		popularReleaseScrollPane.getViewport().setBackground(Color.BLACK);
		
	}

}
