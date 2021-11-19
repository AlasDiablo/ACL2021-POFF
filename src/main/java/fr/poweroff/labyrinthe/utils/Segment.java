package fr.poweroff.labyrinthe.utils;

import com.sun.jdi.request.BreakpointRequest;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;

/**
     * this class is a segment
     * isn't drawable if isn't a horizontal, vertical or diagonal segment
    */
public class Segment {
    public static String DIAG = "DIAG";
    public static String VERT = "VERT";
    public static String HORI = "HORI";
    public static String NONE = "NONE";

    private Coordinate firstPoint;
    private Coordinate secondPoint;
    private String type;

    Segment(Coordinate fp, Coordinate sp){
        this.firstPoint = fp;
        this.secondPoint = sp;
        setType();
        setOrder();
    }

    Segment(Coordinate fp, int x,int y){
        this.firstPoint = fp;
        this.secondPoint = new Coordinate(x,y);
        setType();
        setOrder();
    }

    Segment(Coordinate fp, int size,String type){
        this.firstPoint = fp;
        if(type.equals(DIAG))
            this.secondPoint = new Coordinate(fp.getX() + size,fp.getY() + size);
        else if(type.equals(VERT))
            this.secondPoint = new Coordinate(fp.getX(),fp.getY() + size);
        else if(type.equals(HORI))
            this.secondPoint = new Coordinate(fp.getX() + size,fp.getY());
        else
            this.secondPoint = new Coordinate(0,0); // it's clearly an error
        this.type = type;
        setOrder();
    }

    Segment(int x,int y, int size, String type){
        this.firstPoint = new Coordinate(x,y);
        if(type.equals(DIAG))
            this.secondPoint = new Coordinate(x + size,y + size);
        else if(type.equals(VERT))
            this.secondPoint = new Coordinate( x,y + size);
        else if(type.equals(HORI))
            this.secondPoint = new Coordinate(x + size,y);
        else
            this.secondPoint = new Coordinate(0,0); // it's clearly an error too
        this.type = type;
        setOrder();
    }
    private void setType(){
        if(this.firstPoint.getX() == this.secondPoint.getX()) this.type = VERT; //vertical

        else if (this.firstPoint.getY() == this.secondPoint.getY()) this.type = HORI;// horizontal

        else if ((this.firstPoint.getX() - this.secondPoint.getX() == this.firstPoint.getY() - this.secondPoint.getY())
        || this.firstPoint.getX() - this.secondPoint.getX() == -(this.firstPoint.getY() - this.secondPoint.getY()))
            this.type = DIAG; // diagonal
        else
            this.type = NONE;
    }

    // verify to know if it's drawable
    private boolean isDrawable(){
        return this.type.equals(DIAG)
                || this.type.equals(VERT)
                || this.type.equals(HORI);
    }

    //swap firstPoint and secondPoint
    private void swapCoordinate(){
        int x = firstPoint.getX();
        int y = firstPoint.getY();
        firstPoint = new Coordinate(secondPoint.getX(), secondPoint.getY());
        secondPoint = new Coordinate(x,y);
    }

    // set the firstPoint in the corner left up if it's possible
    private void setOrder(){
        if (!isDrawable()) return;

        if(((this.type.equals(HORI) || this.type.equals(DIAG)) && firstPoint.getX()> secondPoint.getX())
        || (this.type.equals(VERT) && firstPoint.getY()> secondPoint.getY() ))
            swapCoordinate();
    }


    public void draw(Graphics2D graph){
        if (!isDrawable()) return;

        else if(this.type.equals(HORI))
            for (int i = 0;i<firstPoint.getX() - secondPoint.getX() ; i++)
                graph.fillRect(firstPoint.getX() + i, firstPoint.getY(), 1,1);

        else if(this.type.equals(VERT))
            for (int i = 0;i<firstPoint.getY() - secondPoint.getY() ; i++)
                graph.fillRect(firstPoint.getX(), firstPoint.getY() + i, 1,1);
        else if (this.type.equals(DIAG)){
            int sens = 1;
            if(firstPoint.getY()> secondPoint.getY()) sens = -1; /* to draw in the good vertical sens, up or down*/
            for (int i = 0;i<firstPoint.getX() - secondPoint.getX() ; i++)
                graph.fillRect(firstPoint.getX() + i, firstPoint.getY() + sens * i, 1,1);
        }
    }
    //to know if a coordinate is on this segment
    //only available on a drawable segment
    public boolean isOn(Coordinate x){
        if (!isDrawable()) return false;
        if      (this.type == HORI && x.getX()>= firstPoint.getX() && x.getX() <= secondPoint.getX() && x.getY() == firstPoint.getY() )
                return true;
        else if (this.type == VERT && x.getY()>= firstPoint.getY() && x.getY() <= secondPoint.getY() && x.getX() == firstPoint.getX())
                return true;
        else if (this.type == DIAG && (this.firstPoint.getX() - x.getX() == this.firstPoint.getY() - x.getY())
                                    || this.firstPoint.getX() - x.getX() == -(this.firstPoint.getY() - x.getY()))
                return true;
        else
            return true;
    }
    public Coordinate getFirstPoint() {
        return firstPoint;
    }

    public Coordinate getSecondPoint() {
        return secondPoint;
    }
}
