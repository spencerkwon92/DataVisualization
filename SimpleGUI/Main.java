package univ.cis.cs490.SimpleGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {

    private Vis mainPanel;

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Spencer Kwon First Programming Project.");
        setVisible(true);
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Item 1");
        JMenu subMenu = new JMenu("Submenu");
        JMenuItem item2 = new JMenuItem("Item 2");

        JMenu bgColor = new JMenu("Background Color");
        JMenuItem color1 = new JMenuItem("Gray");
        JMenuItem color2 = new JMenuItem("Sky Blue");

        JMenu housing = new JMenu("House Color");
        JMenuItem hc1 = new JMenuItem("Red");
        JMenuItem hc2 = new JMenuItem("Brown");

        JMenu hp = new JMenu("House Position");
        JMenuItem ml = new JMenuItem("move left");
        JMenuItem mr = new JMenuItem("move right");


        //setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 1");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 2");
            }
        });

        color1.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               mainPanel.backbroundColor = Color.gray;
               mainPanel.repaint();
               System.out.println("The background color is changed to gray");
           }
        });

        color2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainPanel.backbroundColor = new Color(135, 206, 235);
                mainPanel.repaint();
                System.out.println("The background color is changed to skyblue");
            }
        });

        hc1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainPanel.houseColor = Color.red;
                mainPanel.repaint();
                System.out.println("House color is changed to red.");
            }
        });

        hc2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainPanel.houseColor = new Color(150, 75, 0);
                mainPanel.repaint();
                System.out.println("House color is changed to brown.");
            }
        });

        ml.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainPanel.move -= 30;
                mainPanel.repaint();
                System.out.println("Moving house to lef");
            }
        });

        mr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainPanel.move += 50;
                mainPanel.repaint();
                System.out.println("Moving house to right");
            }
        });

        //now hook them all together
        subMenu.add(item2);
        fileMenu.add(item1);
        fileMenu.add(subMenu);
        menuBar.add(fileMenu);

        bgColor.add(color1);
        bgColor.add(color2);
        menuBar.add(bgColor);

        housing.add(hc1);
        housing.add(hc2);
        menuBar.add(housing);

        hp.add(ml);
        hp.add(mr);
        menuBar.add(hp);

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
