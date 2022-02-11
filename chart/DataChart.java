package univ.cis.cs490.chart;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.*;

public class DataChart extends JFrame{

    private Visual contents;

    public DataChart() {
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
        JMenu types = new JMenu("Type");

        JMenuItem query1 = new JMenuItem("The number of students in each of the majors");
        query1.addActionListener(contents);
        query1.setActionCommand("SELECT count(*),major FROM cis2019 group by major");
        queries.add(query1);

        JMenuItem query2 = new JMenuItem("The number of students from each of the home areas");
        query2.addActionListener(contents);
        query2.setActionCommand("SELECT count(*),home FROM cis2019 group by home");
        queries.add(query2);

        JMenuItem query3 = new JMenuItem("The average GPA of students in each of the majors");
        query3.addActionListener(contents);
        query3.setActionCommand("SELECT avg(GPA),major FROM cis2019 group by major");
        queries.add(query3);

        JMenuItem query4 = new JMenuItem("The average number of credits attempted, per year");
        query4.addActionListener(contents);
        query4.setActionCommand("SELECT avg(CREDITS_PASSED),gradyear FROM cis2019 group by gradyear");
        queries.add(query4);

        JMenuItem query5 = new JMenuItem("The number of students grouped by gender.");
        query5.addActionListener(contents);
        query5.setActionCommand("SELECT count(*), gender FROM cis2019 group by gender");
        queries.add(query5);

        JMenuItem query6 = new JMenuItem("Number of students per GPA group.");
        query6.addActionListener(contents);
        query6.setActionCommand("SELECT count(*), gpagroup FROM cis2019 group by gpagroup");
        queries.add(query6);

        JMenuItem bar = new JMenuItem("Bar Chart");
        bar.addActionListener(e->{
            contents.setType(true);
            System.out.println("BarChart!!!");
        });
        types.add(bar);

        JMenuItem line = new JMenuItem("Line Chart");
        line.addActionListener(e-> {
            contents.setType(false);
            System.out.println("Line Chart!!");
        });
        types.add(line);

        mb.add(queries);
        mb.add(types);
        return mb;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DataChart();
            }
        });
    }
}
