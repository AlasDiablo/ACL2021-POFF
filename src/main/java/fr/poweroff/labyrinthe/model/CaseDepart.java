package fr.poweroff.labyrinthe.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaseDepart extends Case{
    private static String DEPART = "DEPART";
    CaseDepart(){
        super();
    }
    CaseDepart(int x, int y){
        super(x, y,DEPART);
    }
    CaseDepart(BufferedImage sp, int x, int y){
        super(sp,x, y,DEPART);
    }
    @Override
    public void draw(Graphics2D crayon) {
        int x = super.getCoordX();
        int y = super.getCoordY();
        int s = super.SIZE_PIXEL;
        crayon.setColor(Color.BLACK);
        crayon.fillRect(x, y, super.SIZE_CASE * s, super.SIZE_CASE * s);
        crayon.setColor(Color.GREEN);
        for(int i = 0 ; i<5;i++)
            crayon.fillRect(x + s * (5 - i ), y + s * (1 + i), s,s);

        for(int i = 0 ; i<5;i++)
            crayon.fillRect(x + s * (5 + i ), y + s * (1 + i), s,s);

        for(int i = 0 ; i<4;i++)
            crayon.fillRect(x + s * (5 - i ), y + s * (9 - i), s,s);

        for(int i = 0 ; i<4;i++)
            crayon.fillRect(x + s * (5 + i ), y + s * (9 - i), s,s);
    }

    @Override
    public boolean OnCaseEvent() {
        return true;
    }
}


