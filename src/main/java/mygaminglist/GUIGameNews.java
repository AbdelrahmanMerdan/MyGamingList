package mygaminglist;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import database.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JToggleButton;
import javax.swing.DefaultComboBoxModel;

public class GUIGameNews extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static JPanel card;
	private static Box newsBox;
	private static JLabel reviewTitleLabel;
	private static String backLocation;
	private static JPanel buttonPanel;
	private static JScrollPane reviewScrollPane;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUIGameNews(JPanel cardPane) {
		
		card = cardPane;
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout(0, 0));
		mainPane.setBorder(new MatteBorder(50, 50, 50, 50, (Color) new Color(27, 40, 56)));
		cardPane.add(mainPane, "gamenews");
		
		//generate container
		newsBox = new Box(1);
		newsBox.setBackground(new Color(27, 40, 56));
		newsBox.setBorder(new MatteBorder(0, 10, 10, 10, (Color) new Color(23, 26, 33)));
		
		//generate scrollable		
		reviewScrollPane = new JScrollPane(newsBox);
		reviewScrollPane.getViewport().setBackground(new Color(27, 40, 56));
		reviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reviewScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		reviewScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		reviewScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		reviewScrollPane.setBorder(BorderFactory.createEmptyBorder());
		mainPane.add(reviewScrollPane, BorderLayout.CENTER);
		
		//this  rest is mostly formatting and shit
		JPanel ReviewHeaderPane = new JPanel();
		ReviewHeaderPane.setBackground(new Color(27, 40, 56));
		mainPane.add(ReviewHeaderPane, BorderLayout.NORTH);
		ReviewHeaderPane.setLayout(new BorderLayout(0, 0));
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(23, 26, 33));
		ReviewHeaderPane.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(new BorderLayout(0, 0));
		
		JButton backButton = new JButton("  BACK  ");
		backButton.setForeground(Color.WHITE);
		backButton.setBackground(new Color(23, 26, 33));
		backButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		backButton.setOpaque(true);
		buttonPanel.add(backButton);
		backButton.setFocusable(false);
		
		reviewTitleLabel = new JLabel("GAME");
		reviewTitleLabel.setForeground(Color.WHITE);
		reviewTitleLabel.setFont(new Font("MS Song", Font.BOLD, 40));
		ReviewHeaderPane.add(reviewTitleLabel, BorderLayout.WEST);
		
		
		//listener for back button, changes between targets
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, backLocation);
			}
		});
		
	}
	
	
	// draws each reviews
	public static void loadGameNews(Game game) {
		
		GameData.updateNewsBlog(game);
		backLocation = "game";
		
		new GUIGameNews(card);
		reviewTitleLabel.setText(game.getName());
		
		for(int i = 0; i < game.getNewsBlogs().size(); i++) {
			
			generateNewsPost(game, i);
		}
		
		((CardLayout) card.getLayout()).show(card, "gamenews");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                reviewScrollPane.getVerticalScrollBar().setValue(0);
            }
        });
		
		
	}

	public static void generateNewsPost(Game game, int i) {
		
		
		JPanel newsPane = new JPanel();
		newsPane.setBackground(Color.BLACK);
		newsPane.setLayout(new BorderLayout(0, 0));
		
		JPanel newsHeaderPane = new JPanel();
		newsHeaderPane.setBorder(new MatteBorder(10, 0, 10, 0, (Color) new Color(23, 26, 33)));
		newsHeaderPane.setBackground(new Color(23, 26, 33));
		newsPane.add(newsHeaderPane, BorderLayout.NORTH);
		newsHeaderPane.setLayout(new BorderLayout(0, 0));
		newsHeaderPane.setPreferredSize(new Dimension(0,64));
		
		String author = game.getNewsBlogs().get(i).getAuthor() ;
		
		JLabel nameLabel = new JLabel("Author: " + author);
		nameLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		nameLabel.setForeground(Color.WHITE);
		newsHeaderPane.add(nameLabel, BorderLayout.WEST);
		
		String title =  game.getNewsBlogs().get(i).getTitle();
		
		JLabel titleLabel = new JLabel("Title: " + title);
		titleLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newsHeaderPane.add(titleLabel, BorderLayout.NORTH);
		
		String date = game.getNewsBlogs().get(i).getDate();


		JLabel dateLabel = new JLabel("Date: " + date);
		dateLabel.setFont(new Font("MS Song", Font.PLAIN, 32));
		dateLabel.setForeground(Color.WHITE);
		dateLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		newsHeaderPane.add(dateLabel, BorderLayout.EAST);
		
		
		
		JPanel gameContentPane = new JPanel();
		gameContentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gameContentPane.setOpaque(false);
		newsPane.add(gameContentPane, BorderLayout.CENTER);
		
		
		String contentText2 = game.getNewsBlogs().get(i).getContent();
		
		JTextArea newsContentPane = new JTextArea();

		newsContentPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		newsContentPane.setLineWrap(true);
		newsContentPane.setWrapStyleWord(true);
		newsContentPane.setEditable(false);
		
		String contentText = game.getNewsBlogs().get(i).getContent();
		
		contentText = removeFinalTags(contentText);
		contentText = removeLettersBetweenHttpAndPng(contentText);
		contentText = removeHTMLTags(contentText);
		contentText = removeImageTagsAndLetters(contentText);
		contentText = replaceApostrophe(contentText);
				
		newsContentPane.setBackground(new Color(27, 40, 56));
		newsContentPane.setForeground(Color.WHITE);
		newsContentPane.setFont(new Font("MS Song", Font.PLAIN, 20));
		newsContentPane.setText(contentText);
		
		newsPane.setLayout(new BoxLayout(newsPane, BoxLayout.Y_AXIS));
		
		newsPane.add(newsHeaderPane); // Then add header pane
		newsPane.add(newsContentPane); // Add content pane first
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				newsPane.setSize(new Dimension(0, (int) newsPane.getPreferredSize().getHeight() + 40));
			}
		});
		
		// when resized
		newsPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				newsPane.setPreferredSize(null);
				newsPane.setPreferredSize(new Dimension(0, (int) newsPane.getPreferredSize().getHeight() + 40));
			}
		});

		newsBox.add(newsPane);
		
	}
	
	public static String removeFinalTags(String input) {
		
        // Regular expression to match [img]...[/img] tags
		
        String imgRegex = "\\[img\\].*?\\[/img\\]"; 
        String listRegex = "\\[list\\].*?\\[/list\\]";
        String listItemRegex = "\\[\\*\\]";
        String htmlRegex = "<[^>]+>";
        String finalTagsRegex = "\\[[^\\]]*\\]";
        
        // Compile the regex patterns
        
        Pattern imgPattern = Pattern.compile(imgRegex);
        Pattern listPattern = Pattern.compile(listRegex);
        Pattern listItemPattern = Pattern.compile(listItemRegex);
        Pattern htmlPattern = Pattern.compile(htmlRegex);
        Pattern finalTagsPattern = Pattern.compile(finalTagsRegex);
        
        // Create matcher objects for the input string
        
        Matcher imgMatcher = imgPattern.matcher(input);
        Matcher listMatcher = listPattern.matcher(input);
        Matcher listItemMatcher = listItemPattern.matcher(input);
        Matcher htmlMatcher = htmlPattern.matcher(input);
        Matcher finalTagsMatcher = finalTagsPattern.matcher(input);
        
        // Replace all tags by either a empty space of a dash with tab
        
        String result = input;
        result = imgMatcher.replaceAll(""); // Remove [img]...[/img] tags
        result = listMatcher.replaceAll(""); // Remove [list]...[/list] tags
        result = listItemMatcher.replaceAll("\t-"); // Replace [*] list items with a tab and a dash
        result = htmlMatcher.replaceAll(""); // Remove HTML tags
        result = finalTagsMatcher.replaceAll(""); // Remove strings beginning with [ and ]
        
        return result;
    }
	
	public static String removeLettersBetweenHttpAndPng(String input) {
        
        String urlRegex = "http[^\\s]*\\.png";
        
        String result = input.replaceAll(urlRegex, "");
        
        return result;
    }
    
	
    // Modify the given string by removing letters between "http" and ".png"
    public static String modifyURL(Matcher matcher) {
    	
        String url = matcher.group(0); 
        String modifiedURL = url.replaceAll("(http.*?)(\\.png)", "$1$2");
        return modifiedURL;
        
    }	
    
    public static String removeHTMLTags(String input) {
        // Regular expression to match HTML tags (including attributes) in the input string
        String htmlRegex = "<[^>]+>";
        
        // Replace all occurrences of HTML tags with an empty string
        String result = input.replaceAll(htmlRegex, "");
        
        return result;
    }
    
    public static String removeImageTagsAndLetters(String input) {
        // Regular expression to match the pattern {STEAM_CLAN_IMAGE}...(.png|.jpg)
        String imageRegex = "\\{STEAM_CLAN_IMAGE\\}(.*?)\\.(png|jpg)";
        
        // Replace all occurrences of the matched pattern (including tags) with an empty string
        String result = input.replaceAll(imageRegex, "");
        
        return result;
    }
    
    public static String replaceApostrophe(String input) {
        // Replace "&apos;" with "'"
        String result = input.replaceAll("&apos;", "'");
        return result;
    }
}


