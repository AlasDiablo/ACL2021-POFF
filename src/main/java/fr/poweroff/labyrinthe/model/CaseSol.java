package fr.poweroff.labyrinthe.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaseSol extends Case{
    private static String SOL = "SOL";
    CaseSol(){
        super();
    }
    CaseSol(int x, int y){
        super(x, y,SOL);
    }
    CaseSol(BufferedImage sp, int x, int y){
        super(sp,x, y,SOL);
    }
    @Override
    public void draw(Graphics2D crayon) {
        int x = super.getCoordX();
        int y = super.getCoordY();
        int s = super.SIZE_PIXEL;
        crayon.setColor(Color.BLACK);
        crayon.fillRect(x, y, super.SIZE_CASE * s, super.SIZE_CASE * s);
    }

    @Override
    public boolean OnCaseEvent() {
        return true;
    }
}


