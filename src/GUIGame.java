package src;

import database.GameData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIGame {
	
	private static JLabel gameTitleLabel;
	private static JLabel gameImageLabel;
	private static JEditorPane sysRequireText;
	private static JEditorPane descriptionText;
	private static JButton reviewGameButton;
	
	//private static JButton criticReviewButton;
	private static JLabel criticReviewLabel;
	
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
		
		sysRequireText = new JEditorPane();
		sysRequireText.setContentType("text/html");
		sysRequireText.setBackground(Color.WHITE);
		sysRequireText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		sysRequireText.setEditable(false);
		
		JScrollPane sysRequireScrollPane = new JScrollPane(sysRequireText);
		sysRequireScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sysRequireScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sysRequireScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		sysRequireScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameFooterPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameFooterPane.add(sysRequireScrollPane);
		sysRequireScrollPane.setBorder(BorderFactory.createEmptyBorder());
		sysRequireScrollPane.setPreferredSize(new Dimension( 10, 200));
		
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
		gameOptionsNorthPane.setBackground(Color.BLACK);
		gameOptionsPane.add(gameOptionsNorthPane);
		gameOptionsNorthPane.setLayout(new BorderLayout(0, 0));
		
		//unfinished buttons
		reviewGameButton = new JButton("Review Game");
		reviewGameButton.setFocusable(false);
		reviewGameButton.setForeground(Color.WHITE); // only here for temp critic review
		reviewGameButton.setBackground(Color.BLACK);
		gameOptionsNorthPane.add(reviewGameButton, BorderLayout.CENTER);
		
		JPanel gameOptionsSouthPane = new JPanel();
		gameOptionsSouthPane.setBackground(Color.BLACK);
		gameOptionsPane.add(gameOptionsSouthPane);
		gameOptionsSouthPane.setLayout(new BorderLayout(0, 0));
		
		/*criticReviewButton = new JButton("Critic Reviews");
		criticReviewButton.setFocusable(false);
		criticReviewButton.setForeground(Color.WHITE);
		criticReviewButton.setBackground(Color.BLACK);
		gameOptionsSouthPane.add(criticReviewButton, BorderLayout.CENTER);*/
		
		//temp
		criticReviewLabel = new JLabel();
		criticReviewLabel.setForeground(Color.WHITE);
		criticReviewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gameOptionsSouthPane.add(criticReviewLabel);
		
		JPanel gameDescriptionPane = new JPanel();
		gameDescriptionPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gameDescriptionPane.setOpaque(false);
		gamePane.add(gameDescriptionPane, BorderLayout.CENTER);
		
		descriptionText = new JEditorPane();
		descriptionText.setBackground(Color.WHITE);
		descriptionText.setContentType("text/html");
		descriptionText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		descriptionText.setEditable(false);
		
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionText);
		descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		descriptionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameDescriptionPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameDescriptionPane.add(descriptionScrollPane);
		descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		
		
	}
	
	public static void loadGame(Game game) {
		//When database don't have app details
		if(GameData.noAppDetails(game.getID()) || GameData.noAppReviews(game.getID()))
		{
			//Update database
			GameData.updateAppDetails(game.getID());
			game = GameData.getGame(game.getID());
			
		}
		
		//For games that didn't have their info in the database when the program boots
		if(game.getSysRequire() == null)
		{
			game = GameData.getGame(game.getID());
		}
		
		//Setting game page
		gameTitleLabel.setText(game.getName());
		
		//Formatting HTML
		String sysRequireHtml = "<html>" + game.getSysRequire()
				.replace("\"", "")
				.replace("\\", "")
				.replace("{minimum:", "")
				.replace("}", "")
				.replace(",recommended:", "")+ "</html>";
		String descriptionHtml = "<html>" + game.getDesc() + "</html>";
		
		//Formatting text
		sysRequireText.setText(sysRequireHtml);
		sysRequireText.setCaretPosition(0);
		descriptionText.setText(descriptionHtml);
		descriptionText.setCaretPosition(0);
		
		//Setting critic review
		String metaScore = game.getMetaScore();
		String metaLink = game.getMetaURL();
		
		if(game.getMetaScore().equals("N/A")) 
		{
			System.out.println(game.getMetaScore());
			criticReviewLabel.setText("Critic Score: " + game.getMetaScore());
		}
		else
		{
			criticReviewLabel.setText("<html>" + "Critic Score: " + metaScore + "<br/><br/>Read Critic Reviews" + "</html>");
			
			criticReviewLabel.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
	                    Desktop.getDesktop().browse(new URI(metaLink));
	                } catch (IOException | URISyntaxException e1) {
	                    e1.printStackTrace();
	                }
				}
				
				@Override
	            public void mouseExited(MouseEvent e) {
					criticReviewLabel.setText("<html>" + "Critic Score: " + metaScore + "<br/><br/>Read Critic Reviews" + "</html>");
	            }
	 
	            @Override
	            public void mouseEntered(MouseEvent e) {
	            	criticReviewLabel.setText( "<html>" + "Critic Score: " + metaScore + "<br/><br/><a href=''>Read Critic Reviews</a>" + "</html>");
	            }
			});
			
		}
		
		
		//Get image from database and set
		URL url = null;
	    try {
	        url = new URL(game.getCover());
	        gameImageLabel.setIcon(new ImageIcon(ImageIO.read(url)));
	    } catch (MalformedURLException ex) {
	        System.out.println("Malformed URL");
	    } catch (IOException iox) {
	        System.out.println("Can not load file");
	    }
	}
}
