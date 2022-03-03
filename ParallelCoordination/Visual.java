package univ.cis.cs490.ParallelCoordination;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.*;
import java.util.List;

public class Visual extends JPanel implements ActionListener, MouseInputListener {

    private ArrayList<Axis> datum;
    private ArrayList<Axis> ratiosDatum;
    private List<CurvedLine> lines;
    private List<Point2D> points;
    private int rowNum;
    private Point mouseDown;
    private Rectangle box;


    Visual(){
        datum = new ArrayList<>();
        ratiosDatum = new ArrayList<>();
        lines = new ArrayList<>();
        points = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        datum.clear();
        lines.clear();
        rowNum = 0;
        String sql = e.getActionCommand();
        try{
            Connection con = DriverManager.getConnection("jdbc:derby:pollster");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int columnNum = meta.getColumnCount();
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
                rowNum ++ ;
            }
            con.close();
            getRatios(datum);
            repaint();

        }catch(SQLException se){
            System.out.println(se);
        }

    }

    public void getRatios(ArrayList<Axis> data){
        ratiosDatum.clear();

        for(Axis ele: data){
            Axis newData;
            if(ele.type == Axis.ColumnType.NUMERIC) {
                newData = new Axis(ele.columnName, Axis.ColumnType.NUMERIC);
                double max = Double.MIN_VALUE;
                double min = Double.MAX_VALUE;
                // To get the Max number.
                for (double value : ele.numberData) {
                    if (value > max) max = value;
                    if(value < min) min = value;
                }
                for (double value : ele.numberData) {
                    double ratio = (value - min)/ (max - min);
                    newData.numberData.add(ratio);
                }
                ele.MaxMin[0] = max;
                ele.MaxMin[1] = min;
                ratiosDatum.add(newData);
            }else if(ele.type == Axis.ColumnType.TEXT){
                newData = new Axis(ele.columnName, Axis.ColumnType.TEXT);
                for(String value:ele.stringData){
                    newData.numberData.add((double) 0);
                    newData.stringData.add(value);
                }
                ratiosDatum.add(newData);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        int startX = 80;
        int startY = 15;
        int w = getWidth()-(startX*2);
        int h = getHeight()-(startY*4);
        int letterW = 4;
        //Start to draw the X and Y Asix.
        int numOfCul = ratiosDatum.size();
        int x = startX;
        double[] xPoints = new double[numOfCul];

        for(int i=0;i<numOfCul;i++){
            String culName = ratiosDatum.get(i).columnName;
            int size = culName.length();
            g.drawLine(x,startY,x,h); // Draw X axis.
            g.drawString(culName, (x-(letterW*size)),h+(startY)); // draw labels.

            g.setColor(Color.RED);
            if(ratiosDatum.get(i).type == Axis.ColumnType.NUMERIC){
                // draw yLabels...
                int yStartPos = startY;
                double yValuePosIncreament = h/4;
                double startYlabel = datum.get(i).MaxMin[0];
                double labelDecreament =(datum.get(i).MaxMin[0]-datum.get(i).MaxMin[1])/4;

                g.drawString(String.valueOf(startYlabel), x+2, yStartPos);
                for(int j=0; j<3; j++){
                    yStartPos += yValuePosIncreament;
                    startYlabel -= labelDecreament;
                    g.drawString(String.valueOf(Math.round(startYlabel*100.0)/100.0), x+2, yStartPos);
                }
                g.drawString(String.valueOf(datum.get(i).MaxMin[1]), x+2, h);
            }else{
                Set<String> textCategory = new HashSet<>();
                for(String ele:ratiosDatum.get(i).stringData){
                    textCategory.add(ele);
                }
                double yStartPos = h/(textCategory.size()+1);
                double yIncreament = h/(textCategory.size()+1);

                //converting the data for drawing...
                for(String ele:textCategory){
                    g.drawString(ele, x+2, (int) yStartPos);
                    for(int j=0; j<ratiosDatum.get(i).stringData.size();j++){
                        String str = ratiosDatum.get(i).stringData.get(j);
                        if(str.equals(ele)){
                            ratiosDatum.get(i).numberData.set(j, yStartPos);
                        }
                    }
                    yStartPos += yIncreament;
                }
            }
            xPoints[i] = x;
            x+=w/(numOfCul-1);
            g.setColor(Color.BLACK);
        }
        lines.clear();
        for(int i=0; i<rowNum; i++){
            CurvedLine cl = new CurvedLine();
//            points.clear();
            for(int j=0; j<numOfCul; j++) {
                double point;
                if(ratiosDatum.get(j).type == Axis.ColumnType.NUMERIC){
                    point = h - (ratiosDatum.get(j).numberData.get(i) * (h-startY));
                }else{
                    point = ratiosDatum.get(j).numberData.get(i)*1;
                }
                cl.setPoint(new Point2D.Double(xPoints[j], point));
            }

            lines.add(cl);
        }

        for(CurvedLine ele:lines){
            ele.draw(g);
        }

        if(box != null){
            g.setColor(new Color(255,0,0,20));
            g.fill(box);
        }
     }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouseDown = new Point(x,y );
        box = new Rectangle();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }
}
