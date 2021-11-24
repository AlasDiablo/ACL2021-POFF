package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.GamePainter;
import fr.poweroff.labyrinthe.level.Level;

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
    static final int WIDTH  = 550;
    static final int HEIGHT = 550;

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

        crayon.setColor(Color.BLACK);
        crayon.fillRect(Level.TITLE_SIZE - 2, 2, Level.TITLE_SIZE * 23 + 2, 18);
        crayon.setColor(Color.ORANGE);

        crayon.drawLine(Level.TITLE_SIZE - 2, 2, Level.TITLE_SIZE * 24, 2);
        crayon.drawLine(Level.TITLE_SIZE - 2, 18, Level.TITLE_SIZE * 24 + 2, 18);

        crayon.drawLine(Level.TITLE_SIZE * 24, 2, Level.TITLE_SIZE * 24, 18);
        crayon.drawLine(Level.TITLE_SIZE - 2, 2, Level.TITLE_SIZE - 2, 18);

        crayon.setColor(Color.white);


        Font font = new Font("Courier New", Font.BOLD, 17);
        crayon.setFont(font);
        crayon.drawString(pacmanGame.getCountdown().getMinutesSeconds(), Level.TITLE_SIZE, 15);

        crayon.drawString("Score: ", Level.TITLE_SIZE * 4, 15);
        crayon.drawString(String.valueOf(pacmanGame.getScore()), Level.TITLE_SIZE * 7, 15);
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
