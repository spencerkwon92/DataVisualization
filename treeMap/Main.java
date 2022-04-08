package univ.cis.cs490.treeMap;

import javax.swing.*;
import java.io.File;

public class Main extends JFrame {
    Visual contents;
    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setTitle("TreeMap");
        contents = new Visual();
        setContentPane(contents);
        JMenuBar mb = createMenu();
        setJMenuBar(mb);
        setVisible(true);
    }

    private JMenuBar createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu file =  new JMenu("Files");
        JMenuItem file1  = new JMenuItem("Pick A Folder");
        file1.addActionListener(e->{
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int respons = fc.showOpenDialog(null);
            if(respons == JFileChooser.APPROVE_OPTION){
                File f = new File(fc.getSelectedFile().getAbsolutePath());
                contents.setFile(f);
            }
        });

        JMenu sb = new JMenu("Color Scheme");
        JMenuItem color1 = new JMenuItem("File Type.");
        color1.addActionListener(e->{
            contents.setTheme(ColorTheme.FILETYPE);
        });

        JMenuItem color2 = new JMenuItem("File Age.");
        color2.addActionListener(e->{
            contents.setTheme(ColorTheme.AGE);
        });

        JMenuItem color3 = new JMenuItem("Rendom Color");
        color3.addActionListener(e->{
            contents.setTheme(ColorTheme.RANDOM);

        });

        sb.add(color1);
        sb.add(color2);
        sb.add(color3);

        file.add(sb);
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
