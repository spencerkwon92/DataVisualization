package univ.cis.cs490.scatterplot;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.*;

public class Scatterplot extends JFrame{

    private Visual contents;

    public Scatterplot(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setTitle("My First CS490R Program");
        contents = new Visual();
        setContentPane((Container) contents);
        JMenuBar mb = createMenu();
        setJMenuBar(mb);
        setVisible(true);
    }

    private JMenuBar createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu queries = new JMenu("Query");
        JMenu plots = new JMenu("Plots");


        JMenuItem query1 = new JMenuItem("Credits attempted vs. Credits passed.");
        query1.addActionListener(contents);
        query1.setActionCommand("SELECT CREDITS_ATTEMPTED , CREDITS_PASSED , gender FROM cis2012");
        queries.add(query1);

        JMenuItem query2 = new JMenuItem("Credits attempted vs. GPA.");
        query2.addActionListener(contents);
        query2.setActionCommand("SELECT CREDITS_ATTEMPTED , GPA , gender FROM cis2012");
        queries.add(query2);

        JMenuItem query3 = new JMenuItem("Credits passed vs. GPA.");
        query3.addActionListener(contents);
        query3.setActionCommand("SELECT CREDITS_PASSED , GPA , gender FROM cis2012");
        queries.add(query3);

        JMenuItem query4 = new JMenuItem("Age vs GPA");
        query4.addActionListener(contents);
        query4.setActionCommand("SELECT AGE , GPA , gender FROM cis2012");
        queries.add(query4);

        JMenuItem plot1 = new JMenuItem("Show Females");
        plot1.addActionListener(e->{
            contents.setGenderView(true);
            contents.repaint();
        });

        JMenuItem plot2 = new JMenuItem("Reset");
        plot2.addActionListener(e->{
            contents.reset();
        });


        plots.add(plot1);
        plots.add(plot2);
        mb.add(queries);
        mb.add(plots);
        return mb;
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Scatterplot();
            }
        });
    }
}
