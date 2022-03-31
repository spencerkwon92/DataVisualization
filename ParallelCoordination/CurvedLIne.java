package univ.cis.cs490.ParallelCoordination;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class CurvedLine {
    private enum State{
        NORMAL,
        HIGHLIGHTED,
        INVISIBLE
    }
    private State currentState;
    private GeneralPath line;
    private List<Point2D> points = new ArrayList<>();

    CurvedLine(){
        line = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        currentState = State.NORMAL;
    }

    public List<Point2D> getPoints(){
        return points;
    }

    public void setPoint(Point2D p){
        points.add(p);
    }

    public GeneralPath getLine(){
        return line;
    }

    public void highlihgt(){
        currentState = State.HIGHLIGHTED;
    }

    public void invisible(){
        currentState = State.INVISIBLE;
    }
    public State getCurrentState(){
        return currentState;
    }

    public double getDistanceFromPoint(int x, int y){
        double min = Double.MAX_VALUE;
        for(int i=1; i<points.size(); i++){
            Point2D p1 = points.get(i-1);
            Point2D p2 = points.get(i);
            Line2D.Double segment = new Line2D.Double(p1,p2);
            double distance = segment.ptSegDist(x, y);
            if(distance < min){
                min = distance;
            }
        }
        return min;
    }

    public void draw(Graphics2D g){
        for(int i=0; i< points.size(); i++){
            double x = points.get(i).getX();
            double y = points.get(i).getY();

            if(i == 0){
                line.moveTo(x,y);
            }else{
                line.lineTo(x,y);
            }
        }
        if(currentState == State.HIGHLIGHTED){
            g.setStroke(new BasicStroke(2.0f));
            g.setColor(new Color(5, 195, 221));
        }
        if(currentState == State.INVISIBLE){
            g.setColor(new Color(0,0,0, 20));
        }
        g.draw(line);
    }
}
