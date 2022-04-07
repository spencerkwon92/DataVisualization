package univ.cis.cs490.treeMap;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

enum Direction{
    HORIZENTAL,
    VIRTICAL
}

class Node {

    List<Node> fileList;
    String fileName, address;
    private double size;
    Direction direction;

    Node(File file){
        fileList = new ArrayList<>();
        this.fileName = file.getName();
        this.address = file.getAbsolutePath();
        if(file.isFile()){
            this.size = file.length();
        }else{
            for(File ele:file.listFiles()){
                Node child = new Node(ele);
                if(this.direction == Direction.VIRTICAL){
                    child.direction = Direction.HORIZENTAL;
                }else{
                    child.direction = Direction.VIRTICAL;
                }
                this.size +=child.size;
                fileList.add(child);
            }
        }
    }

    public void setSize(double s){
        this.size = s;
    }

    public double getSize(){
        return size;
    }

    public void draw(double xPos, double yPos, double width, double hight, Graphics2D g){
        double ratio = width / size;
        double startPoint = xPos;
        if(fileList.size() == 0){
            g.drawRect((int) xPos, (int) yPos, (int) width, (int) hight);
        }else{
            for(Node ele:fileList){
                if(direction == Direction.VIRTICAL){
                    double childWidth = ele.size / ratio;
                    ele.draw(startPoint, yPos,childWidth, hight, g);
                    startPoint += childWidth;

                }else if(direction == Direction.VIRTICAL){

                }
            }
        }
    }

}
