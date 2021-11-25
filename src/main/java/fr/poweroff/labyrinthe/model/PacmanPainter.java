package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.GamePainter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * afficheur graphique pour le game
 */
public class PacmanPainter implements GamePainter {

    /**
     * la taille des cases
     */
    public static final int WIDTH  = 550;
    public static final int HEIGHT = 550;

    public final PacmanGame pacmanGame;

    /**
     * appelle constructeur parent
     *
     * @param pacmanGame le jeutest a afficher
     */
    public PacmanPainter(PacmanGame pacmanGame) {
        this.pacmanGame = pacmanGame;
    }

    /**
     * methode  redefinie de Afficheur retourne une image du jeu
     */
    @Override
    public void draw(BufferedImage im) {
        Graphics2D crayon = (Graphics2D) im.getGraphics();

        this.pacmanGame.level.draw(crayon);

        crayon.setColor(Color.white);


        Font font = new Font("Courier New", Font.BOLD, 17);
        crayon.setFont(font);
        crayon.drawString(pacmanGame.getCountdown().getMinutesSeconds(), WIDTH / 2, 50);

        crayon.drawString("Score: ", WIDTH / 2 - 25, 70);
        crayon.drawString(String.valueOf(pacmanGame.getScore()), WIDTH / 2 + 45, 70);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

}
