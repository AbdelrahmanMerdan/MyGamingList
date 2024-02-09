import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class GUIStore extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIStore frame = new GUIStore();
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
	public GUIStore() {
		setTitle("GUITEST");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 790);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel Header = new JPanel();
		Header.setBackground(Color.BLACK);
		Header.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(Header, BorderLayout.NORTH);
		Header.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		Header.add(panel, BorderLayout.WEST);
		panel.setOpaque(false);
		
		JButton btnNewButton = new JButton("Home");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("My Games");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Friends");
		panel.add(btnNewButton_2);
		
		JPanel panel_1 = new JPanel();
		Header.add(panel_1, BorderLayout.EAST);
		panel_1.setOpaque(false);
		
		txtSearch = new JTextField();
		txtSearch.setText("Search");
		panel_1.add(txtSearch);
		txtSearch.setColumns(15);
		
		JPanel Footer = new JPanel();
		contentPane.add(Footer, BorderLayout.SOUTH);
		
		JPanel MainMenu = new JPanel();
		MainMenu.setBackground(Color.BLACK);
		MainMenu.setBorder(new MatteBorder(5, 0, 0, 0, (Color) new Color(192, 192, 192)));
		contentPane.add(MainMenu, BorderLayout.CENTER);
		MainMenu.setLayout(new CardLayout(0, 0));
		
		JPanel Page1 = new JPanel();
		Page1.setBorder(new EmptyBorder(50, 50, 50, 50));
		Page1.setBackground(Color.DARK_GRAY);
		MainMenu.add(Page1, "name_38693073194600");
		Page1.setLayout(new GridLayout(1, 0, 100, 0));
		
		JPanel mostPlayedPanel = new JPanel();
		Page1.add(mostPlayedPanel);
		mostPlayedPanel.setLayout(new BoxLayout(mostPlayedPanel, BoxLayout.Y_AXIS));
		
		// populate pane
		Box mostPlayedBox = new Box(1);
		JLabel mostPlayedLabel = new JLabel("Most Played");
		mostPlayedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mostPlayedLabel.setForeground(Color.WHITE);
		mostPlayedLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		mostPlayedBox.add(mostPlayedLabel);
		for (int i = 0; i < 250; i++) {
            mostPlayedBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel("<html> &ensp;" + i + ": INSERT GAME" + "</html>");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            mostPlayedBox.add(lbl);
        }
		
		JScrollPane mostPlayedScrollPane = new JScrollPane(mostPlayedBox);
		mostPlayedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mostPlayedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar mostPlayedVertical = mostPlayedScrollPane.getVerticalScrollBar(); //hide scrollbar
		mostPlayedVertical.setPreferredSize( new Dimension(0,0) );
		mostPlayedScrollPane.setPreferredSize(new Dimension(0,0));
		mostPlayedPanel.add(mostPlayedScrollPane);
		mostPlayedScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mostPlayedScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mostPlayedScrollPane.getViewport().setBackground(Color.BLACK);
		
        JPanel highRatedPanel = new JPanel();
		Page1.add(highRatedPanel);
		highRatedPanel.setLayout(new BoxLayout(highRatedPanel, BoxLayout.Y_AXIS));
		
		// populate pane
		Box highRatedBox = new Box(1);
		JLabel highRatedLabel = new JLabel("High Rated");
		highRatedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		highRatedLabel.setForeground(Color.WHITE);
		highRatedLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		highRatedBox.add(highRatedLabel);
		for (int i = 0; i < 250; i++) {
            highRatedBox.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel("<html> &ensp;" + i + ": INSERT GAME" + "</html>");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            highRatedBox.add(lbl);
        }
		
		JScrollPane highRatedScrollPane = new JScrollPane(highRatedBox);
		highRatedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		highRatedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar highRatedVertical = highRatedScrollPane.getVerticalScrollBar(); //hide scrollbar
		highRatedVertical.setPreferredSize( new Dimension(0,0) );
		highRatedScrollPane.setPreferredSize(new Dimension(0,0));
		highRatedPanel.add(highRatedScrollPane);
		highRatedScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		highRatedScrollPane.setBorder(BorderFactory.createEmptyBorder());
		highRatedScrollPane.getViewport().setBackground(Color.BLACK);
		
		JPanel Page2 = new JPanel();
		Page2.setBackground(Color.DARK_GRAY);
		Page2.setForeground(Color.WHITE);
		MainMenu.add(Page2, "name_38702704763100");
	}

}
