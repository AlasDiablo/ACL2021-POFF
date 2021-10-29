package fr.poweroff.labyrinthe.model;

import com.sun.jdi.IntegerType;
import java.awt.image.BufferedImage;

public class Case {
    private final String NOTYPE = "NONE"; // a case with no type
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
    public Case(BufferedImage sp,int x,int y){
        this.sprite = sp;
        this.coordX = x;
        this.coordY = y;
        this.type = NOTYPE;
    }
    public Case(BufferedImage sp,int x,int y,String t){
        this.sprite = sp;
        this.coordX = x;
        this.coordY = y;
        this.type = t;
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
