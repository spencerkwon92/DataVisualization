package univ.cis.cs490.SimpleGUI;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JFrame;

import javax.swing.Timer;


public class Vis extends JPanel {

    Color backbroundColor = Color.blue;
    Color houseColor = Color.pink;
    int move;
    int couldMoving;
    Timer animationTimer;
    int counter;

    public Vis() {
        super();
        this.move = 0;
        this.couldMoving = 0;
        counter = 0;
        startTimer();

    }

    public void startTimer(){
        ActionListener cloudMoving = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                couldMoving += 10;
                repaint();

            }
        };

        repaint();
        animationTimer = new Timer(1000, cloudMoving);
        animationTimer.setInitialDelay(0);
        animationTimer.start();
    }

    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;

//        //draw blank background
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        //render visualization
//        g.setColor(Color.BLACK);
//        g.drawString("Your content here", 10, 20);


        var w = getWidth();
        var h = getHeight();

        //background//
        g.setColor(backbroundColor);
        g.fillRect(0,0,w,h);

        //cloud1//
        g.setColor(Color.WHITE);
        g.fillOval(50+couldMoving,100,50,50);
        g.fillOval(80+couldMoving,100,50,50);
        g.fillOval(110+couldMoving,100,50,50);

        //cloud2//
        g.setColor(Color.WHITE);
        g.fillOval(200+couldMoving,50,80,80);
        g.fillOval(250+couldMoving,50,80,80);
        g.fillOval(300+couldMoving,50,80,80);
        g.fillOval(230+couldMoving,25,70,70);
        g.fillOval(280+couldMoving,25,70,70);

        //cloud3//
        g.setColor(Color.WHITE);
        g.fillOval(400+couldMoving,70,70,70);
        g.fillOval(440+couldMoving,70,70,70);
        g.fillOval(480+couldMoving,70,70,70);

        //draw land/
        g.setColor(Color.YELLOW);
        g.fillRect(0,400,600,200);
        //draw grass//
        g.setColor(Color.GREEN);
        g.fillRect(0,230,600,200);
        g.fillOval(140,125,150,300);
        g.fillOval(55,200,150,200);
        g.fillOval(-100,165,200,200);
        g.fillOval(230,170,165,200);
        g.fillOval(300,165,250,250);
        g.fillOval(500,125,200,300);

        //Beach//
        g.setColor(Color.BLUE);
        g.fillOval(-120,490,800,200);
        //g.fillOval(-120,490,h+600,h);//

        //house//
        g.setColor(houseColor);
        g.fillRect(180+move,300,150,160);
        //house roof//
        g.setColor(Color.YELLOW);
        g.fillRect(155+move,280,200,20);
        g.fillRect(180+move,265,150,15);
        g.fillRect(195+move,255,120,15);
        //door&window//
        g.setColor(Color.BLACK);
        g.fillRect(200+move,360,50,100);
        g.fillRect(270+move,320,30,30);

    }
}