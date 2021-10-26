package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {

	/**
	 * La vitesse de dépalcement du personnage
	 */
	protected int SPEEDMOVE = 4;

	private String direction = "RIGHT";
	private final Coordinate pacmanPosition = new Coordinate(0,0);
	private boolean finish = false;

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public PacmanGame(String source) {
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}
	}

	/**
	 * Renvoie les coordonnees du pacman
	 * @return les coordonnee du pacman
	 */
	public Coordinate getPacmanPosition() {
		return pacmanPosition;
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande la commande faite par le joueur
	 */
	@Override
	public void evolve(Cmd commande) {
		//System.out.println("Execute "+commande);
		//récupération des coordonnes du pacman
		int x = this.pacmanPosition.getCoorX();
		int y = this.pacmanPosition.getCoorY();
		//Modification des coordonnees du pacman ou arret du jeu
		switch (commande) {
			case UP:
				this.pacmanPosition.setCoorY(y - SPEEDMOVE);
				System.out.println("Position Pacman :  "+this.pacmanPosition);
				this.direction = commande.name();
				break;
			case DOWN:
				this.pacmanPosition.setCoorY(y + SPEEDMOVE);
				System.out.println("Position Pacman :  "+this.pacmanPosition);
				this.direction = commande.name();
				break;
			case LEFT:
				this.pacmanPosition.setCoorX(x - SPEEDMOVE);
				System.out.println("Position Pacman :  "+this.pacmanPosition);
				this.direction = commande.name();
				break;
			case RIGHT:
				this.pacmanPosition.setCoorX(x + SPEEDMOVE);
				System.out.println("Position Pacman :  "+this.pacmanPosition);
				this.direction = commande.name();
				break;
			case EXIT:
				this.setFinish(true);
				break;
		}
	}

	/**
	 * Renvoie de la propriete de direction
	 * @return la direction du pacman
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Modifie le jeu (jeu en marche ou à l'arret)
	 * @param finish l'etat du jeu
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		return this.finish;
	}

}
