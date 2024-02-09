import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LabelController implements MouseListener {

    JLabel lbl;
    Color mItemBackCol;
    Color mItemForegCol;
    Color mItmSelBackCol;
    Color mItmSelForegCol;

    public LabelController(JLabel lbl, Color mItemBackCol, Color mItemForegCol, Color mItmSelBackCol,
            Color mItmSelForegCol) {
        this.lbl = lbl;
        this.mItemBackCol = mItemBackCol;
        this.mItemForegCol = mItemForegCol;
        this.mItmSelBackCol = mItmSelBackCol;
        this.mItmSelForegCol = mItmSelForegCol;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String selectedText = lbl.getText().replaceAll("<[^>]*>", "").replace("&ensp;","").trim();
        System.out.println(selectedText);
        javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath(); // close the menu
        lbl.setBackground(mItemBackCol);
        lbl.setForeground(mItemForegCol);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        lbl.setBackground(mItmSelBackCol);
        lbl.setForeground(mItmSelForegCol);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lbl.setBackground(mItemBackCol);
        lbl.setForeground(mItemForegCol);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}