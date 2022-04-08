package univ.cis.cs490.treeMap;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

enum Direction{
    HORIZENTAL,
    VIRTICAL
}
enum ColorTheme{
    RANDOM, FILETYPE, AGE, DEFAULT
}

class Node {
    List<Node> fileList;
    String fileName, address, fileType;
    int dayOld;
    private double size;
    Rectangle box;

    Node(File file){
        fileList = new ArrayList<>();
        this.fileName = file.getName();
        this.address = file.getAbsolutePath();
        this.fileType = getFileType(address);
        this.dayOld = setDayOld(file);

        if(file.isFile()){
            this.size = file.length()/1000000;
        }else{
            // Create the tree..
            for(File ele:file.listFiles()){
                Node child = new Node(ele);
                this.size += child.size;
                fileList.add(child);
            }
        }
    }

    public double getSize(){
        return size;
    }

    public String getFileType(String file){
        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "folder" : fileName.substring(dotIndex + 1);
    }

    public int milliToDay(long milli) {
        return (int) ((double) milli / (1000 * 24 * 60 * 60));
    }
    //Helper method to get the age of file.
    public  int setDayOld(File f) {
        if (f.lastModified() < 1) {
            return 0;
        } else {
            return milliToDay(Calendar.getInstance().getTimeInMillis() - f.lastModified());
        }
    }

    public void draw(double xPos, double yPos, double width, double hight, Direction direction, ColorTheme ct, Graphics2D g){
        double ratio;
        double startPoint;
        box = new Rectangle((int) xPos, (int) yPos, (int) width, (int) hight);

        if(fileList.size() == 0){
            g.setColor(Color.BLACK);
            g.draw(box);
            if(ct == ColorTheme.DEFAULT){
                g.setColor(getDefaultColor());
            }else if(ct == ColorTheme.AGE){
                g.setColor(getOldColor());
            }else if(ct == ColorTheme.FILETYPE){
                g.setColor(getFileTypeColor());
            }else if(ct == ColorTheme.RANDOM){
                g.setColor(getRandomColor());
            }
            g.fillRect((int) xPos, (int) yPos, (int) width, (int) hight);
        }
        else{
            if(direction == Direction.VIRTICAL){
                ratio = width / size;
                startPoint = xPos;
                for(Node ele:fileList){
                    double childWidth = ele.size * ratio;
                    ele.draw(startPoint, yPos, childWidth ,hight ,Direction.HORIZENTAL,ct ,g);
                    startPoint += childWidth;
                }
            }else if(direction == Direction.HORIZENTAL){
                ratio = hight / size;
                startPoint = yPos;
                for(Node ele:fileList){
                    double childHight = ele.size*ratio;
                    ele.draw(xPos,startPoint,width, childHight,Direction.VIRTICAL, ct, g);
                    startPoint += childHight;
                }
            }
        }
    }
    public Color getDefaultColor(){
        return Color.white;
    }
    // get the color when it show by the age.
    public Color getOldColor(){
        if(dayOld< 100){
            return Color.WHITE;
        }else if(dayOld>=100 && dayOld < 200){
            return Color.cyan;
        }else if(dayOld>=200 && dayOld < 400){
            return Color.yellow;
        }else if(dayOld>=400 && dayOld < 600){
            return Color.GREEN;
        }else if(dayOld>=600 && dayOld < 800){
            return Color.BLUE;
        }else{
            return Color.RED;
        }
    }
    //get the random color.
     public Color getRandomColor(){
        int r  = (int)(Math.random()*256);
        int g  = (int)(Math.random()*256);
        int b  = (int)(Math.random()*256);

        Color result = new Color(r,g,b);
        return result;
     }
    //get the color by filetype.
    public Color getFileTypeColor(){

        if(fileType.equals("docx")){
            return Color.pink;
        }else if(fileType.equals("xlsx")){
            return Color.GREEN;
        }else if(fileType.equals("pptx")){
            return Color.orange;
        }else if(fileType.equals("txt")){
            return Color.white;
        }else if(fileType.equals("png")|| fileType.equals("jpeg")){
            return Color.BLUE;
        }else if(fileType.equals("mp3")||fileType.equals("mp4")){
            return Color.MAGENTA;
        }else{
            return Color.YELLOW;
        }
    }
}
