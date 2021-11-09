package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.GamePainter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * afficheur graphique pour le game
 */
public class PacmanPainter implements GamePainter {

    /**
     * la taille des cases
     */
    static final int WIDTH = 550;
    static final int HEIGHT = 550;

    private final PacmanGame pacmanGame;

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

        crayon.setColor(Color.blue);
        //Modification de la position du personnage selon ses coordonnées
        //crayon.fillOval(pacmanGame.getPacmanPosition().getCoorX(),pacmanGame.getPacmanPosition().getCoorY(), 10,10);

        String nameFile = "pacman_right.png";

        switch (pacmanGame.getDirection()) {
            case "UP":
                nameFile = "pacman_up.png";
                break;
            case "DOWN":
                nameFile = "pacman_down.png";
                break;
            case "LEFT":
                nameFile = "pacman_left.png";
                break;
            case "RIGHT":
                nameFile = "pacman_right.png";
                break;
        }


        Graphics2D surface = (Graphics2D) im.getGraphics();
        try {
            Image image = ImageIO.read(new File(Objects.requireNonNull(getClass().getClassLoader().getResource(nameFile)).getFile()));
            int positionX = pacmanGame.getPacmanPosition().getX();
            int positionY = pacmanGame.getPacmanPosition().getY();
            surface.drawImage(image, positionX, positionY, 20, 20, null);
        } catch (IOException e) {
            surface.drawString("Image inexistante", 10, 10);
        }

        Font font = new Font("Courier New", Font.BOLD, 17);
        crayon.setFont(font);
        crayon.drawString(pacmanGame.getCountdown().getMinutesSeconds(), WIDTH / 2, 20);
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
