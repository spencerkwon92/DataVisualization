package univ.cis.cs490.chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Visual extends JPanel implements ActionListener {

    private String greeting;
    private Map<String, Double> ratios;
    private double max;
    private boolean type;

    public Visual(){
        greeting = "Aloha User!!";
        ratios = new HashMap<>();
        max = 0;
        type = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sql = e.getActionCommand();
        try{
            Connection con = DriverManager.getConnection("jdbc:derby:pollster");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Map<String, Integer> datas = new HashMap<>();
            ArrayList<Integer> values = new ArrayList();
            while(rs.next()){
                int count = rs.getInt(1);
                String major = rs.getString(2);
                datas.put(major, count);
                values.add(count);
            }

            Collections.sort(values);
            max = values.get(datas.size()-1);
            setData(datas);
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){

        int winH = getHeight() - 30;
        int winW = getWidth();

        if(ratios.size() != 0){
            int size = ratios.size();

            int X_space = 100;
            int Y_space = 30;

            double maxForY = max;
            double valueDecreasment = maxForY/4;

            //draw X, Y
            g.setColor(Color.BLACK);
            // draw X.
            g.drawLine(X_space,winH, winW-Y_space, winH);
            // draw Y.
            g.drawLine(X_space,winH, X_space, Y_space);
            //draw Y values.
            int realY = 30;
            for(int i=0; i<4;i++){
                g.drawString(String.valueOf(maxForY), 50, realY);
                maxForY -=valueDecreasment;
                realY += winH/5;
            }
            g.drawString(String.valueOf(0.0), 50, winH);

            int barWidth = (winW-X_space -(Y_space * (size+1))) / ratios.size();

            int adder = barWidth+Y_space;
            int xPos = X_space+Y_space;

            ArrayList<Integer> temp = new ArrayList();
            for(String ele:ratios.keySet()) {

                double ratio = ratios.get(ele);
                double barH = winH * ratio;

                int yPos = (winH - (int) barH) + Y_space;
                int realBarH = (int) (barH-Y_space-5);

                // Start to draw chart...
                g.setColor(Color.BLUE);
                if(type){
                    g.fillRect(xPos, yPos, barWidth, realBarH);
                }else{
                    int diameter = 20;
                    g.fillOval((xPos+barWidth/2)-diameter/2, (winH - (int) barH) + Y_space, diameter, diameter);

                    temp.add(xPos+barWidth/2);
                    temp.add(winH - (int) barH+40);
                    if(temp.size()==4){
                        int x1 = temp.get(0);
                        int y1 = temp.get(1);
                        int x2 = temp.get(2);
                        int y2 = temp.get(3);

                        g.setColor(Color.BLUE);
                        g.drawLine(x1, y1, x2, y2);

                        temp.remove(0);
                        temp.remove(0);
                    }
                }
                g.setColor(Color.BLACK);
                g.drawString(ele, xPos+(int)(barWidth*0.2), winH+25);

                xPos += adder;
            }
        }
        repaint();
    }

    public void setData(Map<String, Integer> dataList){
        ratios.clear();
        double max = Double.MIN_VALUE;

        for(int ele:dataList.values()){
            if (ele>max) max = ele;
        }
        for(String label:dataList.keySet()){
            int i = dataList.get(label);
            double ratio = i / max;
            ratios.put(label, ratio);
        }
        repaint();
    }

    public void setType(boolean input) {
        type = input;
    }

}
