package univ.cis.cs490.scatterplot;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Visual extends JPanel implements ActionListener, MouseInputListener {
    private List<Double> xValues, xRatios, yValues, yRatios;
    private double[] xLabels, xMaxMin, yLabels, yMaxMin;
    private List<String> gender;
    private Point mouseDown;
    private Rectangle box;
    private List<Ellipse2D> dots;
    private int w,h;
    private boolean genderView;
    private String xName, yName;
    int realW;
    int realH;

    public Visual(){
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        xRatios = new ArrayList<>();
        yRatios = new ArrayList<>();
        gender = new ArrayList<>();
        xLabels = new double[5];
        yLabels = new double[5];

        xMaxMin = new double[2];
        yMaxMin = new double[2];
        addMouseListener(this);
        addMouseMotionListener(this);
        dots = new ArrayList<>();

        genderView = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String sql = e.getActionCommand();
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:pollster");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            xValues.clear();
            yValues.clear();
            gender.clear();
            String[] lName = sql.split(" ");

            xName =lName[1];
            yName =lName[3];

            while(rs.next()){
                xValues.add(rs.getDouble(1));
                yValues.add(rs.getDouble(2));
                gender.add(rs.getString(3));
            }

            dataTrans(xValues, xRatios, xMaxMin);
            dataTrans(yValues, yRatios, yMaxMin);

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;

        w = getWidth();
        h = getHeight();

        int d = 6;
        g.setColor(Color.BLACK);

        int xGraphSpace = 100;
        int yGraphSpace = 60;
        int size= xRatios.size();

        g.setColor(Color.BLACK);
        g.drawLine(80,h-40, w-20, h-40); // x 축
        g.drawLine(80,20, 80,h-40); // y 축

        realW = w-xGraphSpace;
        realH = h-yGraphSpace;
        dots.clear();
        for(int i=0; i < size; i++){
            if(genderView && gender.get(i).equals("F")){
                g.setColor(Color.red);
            }else{
                g.setColor(Color.black);
            }

            int xPoint = (int)((xRatios.get(i) * realW) - d/2) + xGraphSpace-20;
            int yPoint = (int)(h - ((yRatios.get(i) * realH) - d/2)) - yGraphSpace +12;

            Ellipse2D dot = new Ellipse2D.Double(xPoint, yPoint, d, d);
            dots.add(dot);
            g.fill(dot);
        }

        //start to draw Labels...
        double xMax = xMaxMin[1];
        double yMax = yMaxMin[1];
        double xMin = xMaxMin[0];
        double yMin = yMaxMin[0];
        double xLabelIncreament = (xMax-xMin)/5;
        double yLabelIncreament = (yMax-yMin)/5;

        for(int i=0; i<xLabels.length;i++){
            if(i==0){
                xLabels[i]=xMax;
                yLabels[i] = yMax;
            }else if(i ==1){
                xLabels[i] = xMin;
                yLabels[i] = yMin;
            }else{
                xMax -= xLabelIncreament;
                yMax -= yLabelIncreament;
                xLabels[i] = Math.round(xMax*100.0) / 100.0;
                yLabels[i] = Math.round(yMax*100.0) / 100.0;
            }
        }

        Arrays.sort(xLabels);
        Arrays.sort(yLabels);

        int xTerm = realW/4;
        int yTerm = realH/4;
        int xIncreasement=0;
        int yIncreasement=0;
        g.setColor(Color.BLACK);
        for(int i=0; i<xLabels.length; i++){
            g.drawString(String.valueOf(xLabels[i]), 80+xIncreasement, realH + yGraphSpace-25);
            g.drawString(String.valueOf(yLabels[yLabels.length-1-i]), 40, 20+ yIncreasement);
            xIncreasement += xTerm;
            yIncreasement += yTerm;
        }

        //Drawing axis name.
        AffineTransform defaultAt = g.getTransform();
        if(xName != null && yName != null){
            g.drawString(xName, w/2, realH+yGraphSpace-5);
            AffineTransform tran = new AffineTransform();
            tran.rotate(-Math.PI /2);
            g.setTransform(tran);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(yName, -h/2-200, 30);
        }

        g.setTransform(defaultAt);

        if(box != null){
            g.setColor(new Color(255,0,0,20));
            g.fill(box);
        }
    }

    public void dataTrans(List<Double> data, List<Double> ratios, double[] empty){
        ratios.clear();
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for(int i=0; i<data.size(); i++){
            double ele = data.get(i);
            if(ele > max){
                max = ele;
            }
            if(ele < min){
                min = ele;
            }
        }
        for(double ele:data){
            double ratio = (ele - min)/(max - min);
            ratios.add(ratio);
        }

        empty[0] = 0;
        empty[1] = max;
        repaint();
    }

    public void zoomedDotTrans(List<Double> data, List<Double> ratios, double max, double min){
        ratios.clear();
        for(double ele:data){
            double ratio = (ele - min)/(max - min);
            ratios.add(ratio);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouseDown = new Point(x, y);
        box = new Rectangle();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;

        System.out.println(mouseDown.x+","+mouseDown.y+","+e.getX()+","+e.getY());
        List<Double> newX = new ArrayList();
        List<Double> newY = new ArrayList();
        double xMin = (mouseDown.x/(double)w)*(xMaxMin[1]-xMaxMin[0])+xMaxMin[0];
        double xMax = (e.getX()/(double)w)*(xMaxMin[1]-xMaxMin[0])+xMaxMin[0];
        double yMin = ((h - mouseDown.y)/(double)h)*(yMaxMin[1]-yMaxMin[0])+yMaxMin[0];
        double yMax = ((h - e.getY())/(double)h)*(yMaxMin[1]-yMaxMin[0])+yMaxMin[0];

        for(int i=0; i<dots.size(); i++){
            Ellipse2D ele = dots.get(i);

            if(ele.getX()>= mouseDown.x && ele.getX() < e.getX() && ele.getY()>= mouseDown.y && getY()<e.getY()){
                newX.add(xValues.get(i));
                newY.add(yValues.get(i));
            }
        }
        zoomedDotTrans(newX, xRatios, xMax, xMin);
        zoomedDotTrans(newY, yRatios, yMax, yMin);

        xMaxMin[0] = Math.round(xMin*100.0)/100.0;
        xMaxMin[1] = Math.round(xMax*100.0)/100.0;
        yMaxMin[0] = Math.round(yMin*100.0)/100.0;
        yMaxMin[1] = Math.round(yMax*100.0)/100.0;

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
        int x = e.getX();
        int y = e.getY();
        for(int i=0; i<dots.size(); i++){
            double dotX = xValues.get(i);
            double dotY = yValues.get(i);

            if(dots.get(i).contains(x,y)){
                setToolTipText("("+dotX+", "+dotY+")");
                break;
            }else{
                setToolTipText("");
            }
        }
    }
    public void setGenderView(boolean input){
        genderView = input;
    }

    public void reset(){
        dataTrans(xValues, xRatios, xMaxMin);
        dataTrans(yValues, yRatios, yMaxMin);

        repaint();
    }
}