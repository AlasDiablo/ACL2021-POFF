package fr.poweroff.labyrinthe.model;

import com.sun.jdi.IntegerType;
import java.awt.image.BufferedImage;

public class Case {
    private final String NOTYPE = new String("NONE"); // a case with no type
    private int coordX,coordY; //Coordonnées of this case

    private BufferedImage sprite;
    private String type;

    //------------------------------------------------
    public Case(){
        coordY = -1;
        coordX = -1;
        sprite = null;
        type = NOTYPE;
    }
    public Case(BufferedImage sp,int x,int y){
        sprite = sp;
        coordX = x;
        coordY = y;
        type = NOTYPE;
    }
    public Case(BufferedImage sp,int x,int y,String t){
        sprite = sp;
        coordX = x;
        coordY = y;
        type = new String(t);
    }
    //------------------------------------------------
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
    public void modifyCase(String t,BufferedImage sp){
        type = new String(t);
        sprite = sp;
    }
}
