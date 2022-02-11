package univ.cis.cs490.SQLprac;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database extends JFrame {

    private Vis contents;
    private int rowNum;

    Database() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setTitle("My First CS490R Program");
        contents = new Vis();
        setContentPane(contents);
        var abigail = createMenu();
        setJMenuBar(abigail);
        setVisible(true);

    }

    private JMenuBar createMenu() {
        var mb = new JMenuBar();
        var file = new JMenu("Querys");
        var option1 = new JMenuItem("how many students majored in CS or IT?");

        option1.addActionListener((e)->{
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019 WHERE major = 'Information Systems' OR major = 'Computer Science'");
                rs.next();
                int result = rs.getInt(1);// the number in the parenthsis refers to collumn.
                String newContent = "There are " + result + " who are studying CS or IT.";

                System.out.println(newContent);
                contents.setContent(newContent);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JMenuItem option2 = new JMenuItem("How many students have a GPA over 3.85?");
        option2.addActionListener((e) -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019 WHERE gpa >= 3.85");
                rs.next();
                int result = rs.getInt(1);// the number in the parenthsis refers to collumn.
                String newContent = "There are " + result + " students who have over 3.85 GPA.";

                System.out.println(newContent);
                contents.setContent(newContent);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JMenuItem option3 = new JMenuItem("How many people study Computer Science in hawaii?");
        option3.addActionListener((e)->{

            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019 WHERE major = 'Computer Science' AND home = 'Hawaii'");
                rs.next();
                int result = rs.getInt(1);// the number in the parenthsis refers to collumn.
                String newContent = "There are " + result + " students who studying CS in Hawaii.";

                System.out.println(newContent);
                contents.setContent(newContent);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        JMenuItem option4 = new JMenuItem("How many students majoring CS in the 25-29 age agroup?");
        option4.addActionListener((e)->{
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019 WHERE major = 'Computer Science' AND agegroup = '25-29'");
                rs.next();
                int result = rs.getInt(1);// the number in the parenthsis refers to collumn.
                String newContent = "There are " + result + " students who studying CS in 25-29 age group.";

                System.out.println(newContent);
                contents.setContent(newContent);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JMenuItem option5 = new JMenuItem("How many female asian studying IS?");
        option5.addActionListener((e)->{
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019 WHERE gender = 'F' AND home = 'Asia' AND major = 'Information Systems'");
                rs.next();
                int result = rs.getInt(1);// the number in the parenthsis refers to collumn.
                String newContent = "There are " + result + " female asian students who studying IS.";

                System.out.println(newContent);
                contents.setContent(newContent);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
//        option1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Just clicked option 1");
//                try {
////                    Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
////                    Statement stmt = conn.createStatement();
//                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cis2019");
//                    rs.next();
//                    int abigail = rs.getInt(1);// the number in the parenthsis refers to collumn.
//                    System.out.println("There are " + abigail + " rows in the table.");
//                    String newContent = "There are " + abigail + " rows in the table.";
//                    System.out.println(contents.getContent());
//                    contents.setContent(newContent);
//                    System.out.println(contents.getContent());
//                    conn.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }
//        });

        file.add(option1);
        file.add(option2);
        file.add(option3);
        file.add(option4);
        file.add(option5);

        mb.add(file);
        return mb;
    }

    public static void main(String[] argv){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Database();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
