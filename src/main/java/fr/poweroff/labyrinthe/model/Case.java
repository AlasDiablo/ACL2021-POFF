package fr.poweroff.labyrinthe.model;

import com.sun.jdi.IntegerType;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Case {
    private final String NOTYPE = "NONE"; // a case with no type
    public static int SIZE_CASE = 11; // size of a case
    public static int SIZE_PIXEL = 2; // Size of a pixel
    private int coordX,coordY; //Coordonnées of this case

    private BufferedImage sprite;
    private String type;

    //------------------------------------------------
    public Case(){
        this.coordY = -1;
        this.coordX = -1;
        this.sprite = null;
        this.type = NOTYPE;
    }
    public Case(int x,int y,String type){
        this.sprite = null;
        this.coordX = x;
        this.coordY = y;
        this.type = type;
    }
    public Case(BufferedImage sp,int x,int y,String t){
        this.sprite = sp;
        this.coordX = x;
        this.coordY = y;
        this.type = t;
    }
    //------------------------------------------------

    public abstract void draw(Graphics2D crayon);
    public abstract boolean OnCaseEvent();

    public BufferedImage getSprite() {
        return sprite;
    }
    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String getType() {
        return new String(type);
    }
    /*
    public void modifyCase(String t,BufferedImage sp){
        type = new String(t);
        sprite = sp;
    }*/
}
