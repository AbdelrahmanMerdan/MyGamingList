package SwingTest1;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame window;

    public GUI(){
        // frame
        window = new JFrame();
        window.setTitle("MyGamesList");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1200, 1000);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(new Color(42, 40, 48));
        window.setLayout(new BorderLayout());


        // panels
        JPanel headerPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        headerPanel.setBackground(Color.black);
        mainPanel.setBackground(new Color(42, 40, 48));

        headerPanel.setPreferredSize(new Dimension(0,75));

        headerPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(75, 75, 75, 75));

        mainPanel.setLayout(new GridLayout(1,2,150,0));

        // subPanels
        JPanel mostPlayedPanel = new JPanel();
        JPanel highRatedPanel = new JPanel();

        mostPlayedPanel.setBackground(Color.black);
        highRatedPanel.setBackground(Color.black);

        mostPlayedPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
        highRatedPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));

        mostPlayedPanel.setLayout(new FlowLayout());
        highRatedPanel.setLayout(new FlowLayout());

        // buttons
        JButton home = new JButton("Home");

        JButton myGames = new JButton("My Games");

        JButton friends = new JButton("Friends");

        // labels
        JLabel mostPlayed = new JLabel("Most Played");
        JLabel highRated = new JLabel("Highest Rated");

        mostPlayed.setForeground(Color.lightGray);
        highRated.setForeground(Color.lightGray);

        mostPlayed.setFont(new Font("Arial",Font.PLAIN, 40));
        highRated.setFont(new Font("Arial", Font.PLAIN, 40));

        // draw panels
        window.add(headerPanel, BorderLayout.NORTH);
        window.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(mostPlayedPanel);
        mainPanel.add(highRatedPanel);

        // draw text
        mostPlayedPanel.add(mostPlayed);
        highRatedPanel.add(highRated);

        // draw buttons
        headerPanel.add(home); headerPanel.add(myGames); headerPanel.add(friends);

        // show frame
        window.setVisible(true);
    }
}