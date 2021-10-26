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
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 500;
	protected static final int HEIGHT = 500;

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
		/*Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.blue);
		//Modification de la position du personnage selon ses coordonnées
		crayon.fillOval(pacmanGame.getPacmanPosition().getCoorX(),pacmanGame.getPacmanPosition().getCoorY(), 10,10);*/

		String nameFile = "pacmanRight.png";

		switch (pacmanGame.getDirection()){
			case "UP":
				nameFile = "pacmanUp.png";
				break;
			case "DOWN":
				nameFile = "pacmanDown.png";
				break;
			case "LEFT":
				nameFile = "pacmanLeft.png";
				break;
			case "RIGHT":
				nameFile = "pacmanRight.png";
				break;
		}


		Graphics2D surface = (Graphics2D) im.getGraphics();
		try {
			Image image = ImageIO.read(new File(Objects.requireNonNull(getClass().getClassLoader().getResource(nameFile)).getFile()));
			int positionX = pacmanGame.getPacmanPosition().getCoorX();
			int positionY = pacmanGame.getPacmanPosition().getCoorY();
			surface.drawImage(image, positionX, positionY, 20, 20, null);
		}
		catch (IOException e) {
			surface.drawString("Image inexistante", 10, 10);
		}
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
