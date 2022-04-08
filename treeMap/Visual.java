package univ.cis.cs490.treeMap;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

class Visual extends JPanel implements MouseInputListener {
    private Node parNode;
    private File file;
    private ColorTheme theme;

    Visual(){
        theme = ColorTheme.DEFAULT;
        parNode = new Node(new File("/Users/sungjin.spencerkwon/Desktop/default"));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setFile(File f){
        file = f;
        parNode = new Node(file);
        repaint();
    }

    public void setTheme(ColorTheme th){
        theme = th;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1){
        // draw the NODE.
        Graphics2D g = (Graphics2D)g1;
        double w = getWidth();
        double h = getHeight();
        parNode.draw(0,0, w, h, Direction.VIRTICAL, theme, g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        //tool tip setting.
        int x = e.getX();
        int y = e.getY();
        for(Node ele:parNode.fileList){
            double sX = ele.box.getX();
            double sY = ele.box.getY();
            double eX = sX + ele.box.getWidth();
            double eY = sY + ele.box.getHeight();

            if(x>sX && x<eX && y>sY && y<eY){
                String msg = ele.address+"(Size: "+(int)ele.getSize()+"MB)";
                setToolTipText(msg);
            }
        }

    }
}
