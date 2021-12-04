package fr.poweroff.labyrinthe.engine.menu;

import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BestScore {
    public static final int TITLE_SIZE = 275 * 2;

    private final Coordinate    coordinate;
    private final BufferedImage sprites = FilesUtils.getImage("game_score.jpg");

    public BestScore(int x, int y) {
        coordinate = new Coordinate(x, y);
    }


    /**
     * Fonction de dessin de l'image
     *
     * @param graphics2D le graphic sur lequel dessiner
     */
    public void draw(Graphics2D graphics2D) {
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        graphics2D.drawImage(this.sprites, x, y, TITLE_SIZE, TITLE_SIZE, null);
    }

    public BufferedImage getSprites() {
        return sprites;
    }
}
