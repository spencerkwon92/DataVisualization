package univ.cis.cs490.SQLprac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;

public class Vis extends JPanel implements ActionListener {

    private String contents;

    public Vis() {
        contents = "Hello Grace!";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        contents = e.getActionCommand();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawString(contents, 10, 30);
    }

    public void setContent(String updata){
        this.contents = updata;
        repaint();
    }

    public String getContent(){
        return contents;
    }


}
