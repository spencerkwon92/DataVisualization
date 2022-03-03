package univ.cis.cs490.ParallelCoordination;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class CurvedLine {
    private GeneralPath line;
    private List<Point2D> points = new ArrayList<>();

    CurvedLine(){
        line = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

    }
    public List<Point2D> getPoints(){
        return points;
    }
    public void setPoint(Point2D p){
        points.add(p);
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
        g.draw(line);
    }
}
