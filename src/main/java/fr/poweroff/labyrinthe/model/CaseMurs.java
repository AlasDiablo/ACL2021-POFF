package fr.poweroff.labyrinthe.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaseMurs extends Case{
    private static String MURS = "MURS";
    CaseMurs(){
        super();
    }
    CaseMurs(int x, int y){
        super(x, y,MURS);
    }
    CaseMurs(BufferedImage sp, int x, int y){
        super(sp,x, y,MURS);
    }
    @Override
    public void draw(Graphics2D crayon) {
        int x = super.getCoordX();
        int y = super.getCoordY();
        int s = super.SIZE_PIXEL;
        crayon.setColor(Color.BLACK);
        crayon.fillRect(x, y, super.SIZE_CASE * s, super.SIZE_CASE * s);
        crayon.setColor(Color.ORANGE);


        crayon.fillRect(x + s, y + s, s, 4 * s);
        crayon.fillRect(x + s, y + s,  9 * s, s);
        crayon.fillRect(x , y + s * 5,  11 * s, s);
        crayon.fillRect(x + s, y + s * 9,  9 * s, s);
        crayon.fillRect(x + s * 9, y + s * 5, s, 4 * s);

        crayon.fillRect(x + s * 5, y, s, s);
        crayon.fillRect(x + s * 5, y + s * 10, s, s);

        crayon.fillRect(x + s * 6, y + s * 3, s, 3 * s);
        crayon.fillRect(x + s * 4, y + s * 5, s, 3 * s);
        crayon.fillRect(x + s * 4, y + s * 3, 3 * s, s);
        crayon.fillRect(x + s * 4, y + s * 7, 3 * s, s);
    }

    @Override
    public boolean OnCaseEvent() {
        return false;
    }
}


