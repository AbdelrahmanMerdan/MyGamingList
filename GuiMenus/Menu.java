import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class homeMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homeMenu frame = new homeMenu();
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
	public homeMenu() {
		setTitle("MyGamingList");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel toolbar = new JPanel();
		toolbar.setBackground(new Color(0, 64, 128));
		contentPane.add(toolbar, BorderLayout.NORTH);
		
		JButton home = new JButton("Home");
		
		home.setFont(new Font("Tahoma", Font.PLAIN, 24));
		toolbar.add(home);
		
		JButton myGames = new JButton("My Rated Games");
		myGames.setFont(new Font("Tahoma", Font.PLAIN, 24));
		toolbar.add(myGames);
		
		JButton friends = new JButton("Friends");
		friends.setFont(new Font("Tahoma", Font.PLAIN, 24));
		toolbar.add(friends);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		toolbar.add(btnNewButton);
		
		JPanel parent = new JPanel();
		parent.setBackground(new Color(0, 0, 0));
		contentPane.add(parent, BorderLayout.CENTER);
		parent.setLayout(new CardLayout(0, 0));
		
		JPanel homePage = new JPanel();
		homePage.setBackground(new Color(0, 0, 0));
		parent.add(homePage, "name_24124213436300");
		homePage.setLayout(null);
		
		JLabel title = new JLabel("Welcome to MyGamingList!");
		title.setBounds(-161, 0, 1566, 49);
		title.setFont(new Font("Tahoma", Font.BOLD, 42));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(255, 255, 255));
		homePage.add(title);
		
		JLabel topSeller = new JLabel("Top Sellers");
		
		topSeller.setHorizontalAlignment(SwingConstants.CENTER);
		topSeller.setBounds(137, 231, 310, 221);
		topSeller.setFont(new Font("Tahoma", Font.BOLD, 52));
		topSeller.setForeground(new Color(255, 255, 255));
		homePage.add(topSeller);
		
		JLabel newRelease = new JLabel("New Releases");
		newRelease.setHorizontalAlignment(SwingConstants.CENTER);
		newRelease.setBounds(748, 231, 382, 221);
		newRelease.setForeground(new Color(255, 255, 255));
		newRelease.setFont(new Font("Tahoma", Font.BOLD, 52));
		homePage.add(newRelease);
		
		JLabel intro = new JLabel("View PC games, see user reviews, and interact with the community!");
		intro.setHorizontalAlignment(SwingConstants.CENTER);
		intro.setFont(new Font("Tahoma", Font.PLAIN, 32));
		intro.setForeground(new Color(255, 255, 255));
		intro.setBounds(117, 60, 1024, 105);
		homePage.add(intro);
		
		JPanel topSellerPage = new JPanel();
		topSellerPage.setBackground(new Color(0, 0, 0));
		parent.add(topSellerPage, "name_26396020285800");
		topSellerPage.setLayout(new BorderLayout(0, 0));
		
		JLabel topSellerTitle = new JLabel("Top Sellers");
		topSellerTitle.setForeground(new Color(255, 255, 255));
		topSellerTitle.setFont(new Font("Tahoma", Font.BOLD, 52));
		topSellerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		topSellerPage.add(topSellerTitle, BorderLayout.NORTH);
		
		JButton btnNewButton_3 = new JButton("Next");
		topSellerPage.add(btnNewButton_3, BorderLayout.EAST);
		
		JButton btnNewButton_2 = new JButton("Prev");
		topSellerPage.add(btnNewButton_2, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		topSellerPage.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		
		JLabel game1 = new JLabel("Final Fantasy VII Remake Intergrade");
		game1.setForeground(new Color(255, 255, 255));
		game1.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panel.add(game1);
		
		JLabel game2 = new JLabel("Lethal Company");
		game2.setForeground(new Color(255, 255, 255));
		game2.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panel.add(game2);
		
		JLabel game3 = new JLabel("War Thunder");
		game3.setForeground(new Color(255, 255, 255));
		game3.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panel.add(game3);
		
		
		
		//			TO SEPERATE FROM GENERATED CODE				//
		
		
		//When user clicks top seller will go to top seller page
		topSeller.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.removeAll();
				parent.add(topSellerPage);
				parent.repaint();
				parent.revalidate();
				
			}
		});
		
		//When user clicks home will bring them home o7
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.removeAll();
				parent.add(homePage);
				parent.repaint();
				parent.revalidate();
			}
		});
		
		
	}
}
