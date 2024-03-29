package src;

import database.GameData;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class GUIGame {
	
	private static JLabel gameTitleLabel;
	private static JLabel gameImageLabel;
	private static JEditorPane sysRequireText;
	private static JEditorPane descriptionText;
	private static JButton reviewGameButton;
	private static JPanel gameOptionsSouthPane;
	public static Game game;
	
	public static JLabel criticReviewLabel;
	
	public GUIGame(JPanel cardPane) {
		
		//So my eyes don't hurt whenever i see a link 
		HTMLEditorKit kit = new HTMLEditorKit();
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("a {color:white;}");
		
		JPanel gamePane = new JPanel();
		gamePane.setBackground(new Color(27, 40, 56));
		cardPane.add(gamePane, "game");
		gamePane.setLayout(new BorderLayout(0, 0));
		
		JPanel gameHeaderPane = new JPanel();
		gameHeaderPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameHeaderPane, BorderLayout.NORTH);
		gameHeaderPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameHeaderPane.setOpaque(false);
		
		gameTitleLabel = new JLabel("GLOOBERLANTIS");
		gameTitleLabel.setForeground(Color.WHITE);
		gameTitleLabel.setFont(new Font("MS Song", Font.BOLD, 40));
		gameHeaderPane.add(gameTitleLabel);
		
		JPanel gameFooterPane = new JPanel();
		gameFooterPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameFooterPane, BorderLayout.SOUTH);
		gameFooterPane.setOpaque(false);
		
		sysRequireText = new JEditorPane();
		sysRequireText.setContentType("text/html");
		sysRequireText.setBackground(new Color(27, 40, 56));
		sysRequireText.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		sysRequireText.setForeground(Color.WHITE);
		sysRequireText.setFont(new Font("MS Song", Font.PLAIN, 20));
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
		gameOptionsNorthPane.setBackground(new Color(23, 26, 33));
		gameOptionsPane.add(gameOptionsNorthPane);
		gameOptionsNorthPane.setLayout(new BorderLayout(0, 0));
		
		reviewGameButton = new JButton("Reviews");
		reviewGameButton.setFocusable(false);
		reviewGameButton.setForeground(Color.WHITE); 
		reviewGameButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		reviewGameButton.setBackground(new Color(23, 26, 33));
		gameOptionsNorthPane.add(reviewGameButton, BorderLayout.CENTER);
		
		reviewGameButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				GUIGameReviews.loadGameReviews(cardPane, game);
				((CardLayout) cardPane.getLayout()).show(cardPane, "reviews");
			}
		});
		
		gameOptionsSouthPane = new JPanel();
		gameOptionsSouthPane.setBackground(new Color(23, 26, 33));
		gameOptionsPane.add(gameOptionsSouthPane);
		gameOptionsSouthPane.setLayout(new BorderLayout(0, 0));
		
		criticReviewLabel = new JLabel();
		criticReviewLabel.setForeground(Color.WHITE);
		criticReviewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		gameOptionsSouthPane.add(criticReviewLabel);
		
		JPanel gameDescriptionPane = new JPanel();
		gameDescriptionPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gameDescriptionPane.setOpaque(false);
		gamePane.add(gameDescriptionPane, BorderLayout.CENTER);
		
		descriptionText = new JEditorPane();
		descriptionText.setBackground(new Color(27, 40, 56));
		descriptionText.setContentType("text/html");
		descriptionText.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		descriptionText.setForeground(Color.WHITE);
		descriptionText.setFont(new Font("MS Song", Font.PLAIN, 20));
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
	
	public static void loadGame(int id) {
		checkGameData(id);
		
		game = GameData.getGame(id);
		
		setGameCover();
		setGameInfo();
		setMetacriticReviews();
	}
	
	private static void checkGameData(int id) {
		if(GameData.noAppExists(id))
		{
			GameData.addApp(id);
		}

		if(GameData.noAppDetails(id) || GameData.noAppReviews(id))
		{
			GameData.updateAppDetails(id);
		}
		
	}
	
	private static void setGameInfo() {
		gameTitleLabel.setText(game.getName());
		
		//Formatting 
		String sysRequireHtml = "<html>" + game.getSysRequire()
		.replace("\"", "")
		.replace("\\", "")
		.replace("{minimum:", "")
		.replace("}", "")
		.replace(",recommended:", "")+ "</html>";
		
		String descriptionHtml = "<html>" + game.getDesc() + "</html>";

		sysRequireText.setText(sysRequireHtml);
		sysRequireText.setCaretPosition(0);
		descriptionText.setText(descriptionHtml);
		descriptionText.setCaretPosition(0);
	}
	
	private static void setGameCover() {
		URL url = null;
		
		//Get image from database and set
		try {
			url = new URL(game.getCover());
			gameImageLabel.setIcon(new ImageIcon(ImageIO.read(url)));
		} catch (MalformedURLException ex) {
			System.out.println("Malformed URL");
		} catch (IOException iox) {
			System.out.println("Can not load file");
		}
	}
	
	private static void setMetacriticReviews() {
		//Reset reviews
		gameOptionsSouthPane.remove(criticReviewLabel);

		//Create new label with its own unique properties
		criticReviewLabel = new JLabel();
		criticReviewLabel.setForeground(Color.WHITE);
		criticReviewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

		String metaScore = game.getMetaScore();
		String metaLink = game.getMetaURL();

		if(metaScore.equals("N/A")) 
		{
			criticReviewLabel.setText("Critic Score: " + metaScore);
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

		gameOptionsSouthPane.add(criticReviewLabel);	
	}
}
