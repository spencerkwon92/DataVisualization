package univ.cis.cs490.treeMap;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Visual extends JPanel {
//    List<Node> tree = new ArrayList<>();
    Visual(){
        String testFile = "/Users/sungjin.spencerkwon/Desktop/univ";
        File f = new File(testFile);
        Node parNode = new Node(f);
        parNode.direction = Direction.HORIZENTAL;
    }

    @Override
    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
    }

    public void sizeTransform(double data){

    }
}
