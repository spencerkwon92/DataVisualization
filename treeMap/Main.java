package univ.cis.cs490.treeMap;

import javax.swing.*;

public class Main extends JFrame {
    Visual contents;
    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setTitle("Tree map");
        contents = new Visual();
        setContentPane(contents);
        JMenuBar mb = createMenu();
        setJMenuBar(mb);
        setVisible(true);
    }

    private JMenuBar createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu file =  new JMenu("Files");
        JMenuItem file1  = new JMenuItem("file1");
        file1.addActionListener(e->{
            // add actions....
        });

        file.add(file1);
        mb.add(file);

        return mb;
    }

    public static void main(String[] argv){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new Main();
            }
        });
    }
}
