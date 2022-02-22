package univ.cis.cs490.ParallelCoordination;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class Visual extends JPanel implements ActionListener, MouseInputListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        String sql = e.getActionCommand();
        try{
            Connection con = DriverManager.getConnection("jdbc:derby:pollster");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int columnNum = meta.getColumnCount();

            System.out.println(columnNum);
            ArrayList<Axis> datum = new ArrayList<>();
            for(int i = 1; i<= columnNum; i++){
                Axis column;
                String label = meta.getColumnLabel(i);
                int type = meta.getColumnType(i);
                if(type == Types.CHAR || type == Types.VARCHAR){
                    column = new Axis(label, Axis.ColumnType.TEXT);
                }else{
                    column = new Axis(label, Axis.ColumnType.NUMERIC);
                }
                datum.add(column);
            }

            while(rs.next()){
                for(Axis ele:datum){
                    if(ele.type == Axis.ColumnType.TEXT){
                        ele.stringData.add(rs.getString(ele.columnName));
                    }
                    if(ele.type == Axis.ColumnType.NUMERIC){
                        ele.numberData.add(rs.getDouble(ele.columnName));
                    }
                }
            }

            con.close();
        }catch(SQLException se){
            System.out.println(se);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
