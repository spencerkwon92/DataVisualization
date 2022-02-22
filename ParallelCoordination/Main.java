package univ.cis.cs490.ParallelCoordination;

import javax.swing.*;

public class Main extends JFrame {
    Visual contents;
    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setTitle("Paraller Coordinates Implemenation.");
        contents = new Visual();
        setContentPane(contents);
        JMenuBar mb = createMenu();
        setJMenuBar(mb);
        setVisible(true);

    }

    private JMenuBar createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu tables = new JMenu("Tables");
        JMenuItem option1 = new JMenuItem("CIS_2012");
        option1.addActionListener(contents);
        option1.setActionCommand("SELECT * FROM cis2012");

        JMenuItem option2 = new JMenuItem("CIS_2019");
        option2.addActionListener(contents);
        option2.setActionCommand("SELECT * FROM cis2019");

        JMenuItem option3 = new JMenuItem("Marathoners");
        option3.addActionListener(contents);
        option3.setActionCommand("SELECT * FROM marathon");

        tables.add(option1);
        tables.add(option2);
        tables.add(option3);

        mb.add(tables);
        return mb;
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
