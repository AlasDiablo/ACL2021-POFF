package fr.poweroff.labyrinthe.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaseFin extends Case{
    private static String FIN = "FIN";
    CaseFin(){
        super();
    }
    CaseFin(int x, int y){
        super(x, y,FIN);
    }
    CaseFin(BufferedImage sp, int x, int y){
        super(sp,x, y,FIN);
    }
    @Override
    public void draw(Graphics2D crayon) {
        int x = super.getCoordX();
        int y = super.getCoordY();
        int s = super.SIZE_PIXEL;
        crayon.setColor(Color.BLACK);
        crayon.fillRect(x, y, super.SIZE_CASE * s, super.SIZE_CASE * s);
        crayon.setColor(Color.RED);

        //diagonales
        for(int i = 0 ; i<7;i++)
            crayon.fillRect(x + s * (2 + i ), y + s * (2 + i), s,s);

        for(int i = 0 ; i<7;i++)
            crayon.fillRect(x + s * (2 + i ), y + s * (8 - i), s,s);

        crayon.fillRect(x + s * 2, y + s, s * 7, s);
        crayon.fillRect(x + s * 2, y + s * 9, s * 7, s);

        crayon.fillRect(x + s, y + s * 2, s, s * 7);

    }

    @Override
    public boolean OnCaseEvent() {
        return true;
    }
}


