import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MenuExample {
    Random rand = new Random();
    Color menuBackCol;
    Color mItemBackCol;
    Color mItemForegCol;
    Color mItmSelBackCol;
    Color mItmSelForegCol;

    MenuExample() {
        menuBackCol = UIManager.getColor("Menu.background");
        mItemBackCol = UIManager.getColor("MenuItem.background");
        mItemForegCol = UIManager.getColor("MenuItem.foreground");
        mItmSelBackCol = UIManager.getColor("MenuItem.selectionBackground");
        mItmSelForegCol = UIManager.getColor("MenuItem.selectionForeground");

        Box box = new Box(BoxLayout.Y_AXIS);

        for (int i = 0; i < 250; i++) {
            box.add(Box.createRigidArea(new Dimension(0, 2))); // creates space between the components
            JLabel lbl = new JLabel("<html> &ensp;" + i + ": INSERT GAME" + "</html>");
            lbl.setOpaque(true);
            lbl.setBackground(mItemBackCol);
            lbl.addMouseListener(
                    new LabelController(lbl, mItemBackCol, mItemForegCol, mItmSelBackCol, mItmSelForegCol));
            box.add(lbl);
        }

        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // adjusts scrolling speed
        scrollPane.setPreferredSize(new Dimension(100, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(menuBackCol);

        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu submenu = new JMenu("Sub Menu");
        submenu.add(scrollPane);
        menu.add(new JMenuItem("Item 1"));
        menu.add(new JMenuItem("Item 2"));
        menu.add(new JMenuItem("Item 3"));
        menu.add(submenu);
        mb.add(menu);

        JFrame f = new JFrame("Menu with ScrollBar Example");
        f.setJMenuBar(mb);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(640, 480);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String args[]) {
        new MenuExample();
    }
}