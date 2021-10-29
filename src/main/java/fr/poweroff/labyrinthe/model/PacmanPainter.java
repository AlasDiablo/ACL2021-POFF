package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.GamePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 100;
	protected static final int HEIGHT = 100;

	private PacmanGame pacmanGame;
	/**
	 * appelle constructeur parent
	 * 
	 * @param pacmanGame
	 *            le jeutest a afficher
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
		crayon.setColor(Color.blue);
		crayon.fillOval(0,0,10,10);
		crayon.drawString(pacmanGame.getCountdown(), WIDTH/2, 10);
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
