package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.Game;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.Countdown;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * Version avec personnage qui peut se deplacer. A completer dans les
 * versions suivantes.
 */
public class PacmanGame implements Game {

    final Level level;
    private final Coordinate pacmanPosition = new Coordinate(0, 0);
    /**
     * Minuteur du niveau
     */
    private final Countdown countdown;
    /**
     * La vitesse de dépalcement du personnage
     */
    protected int SPEEDMOVE = 4;
    private String direction = "RIGHT";
    private boolean finish = false;

    /**
     * constructeur avec fichier source pour le help
     */
    public PacmanGame(String source) {
        ImageUtils.setClassLoader(this.getClass().getClassLoader());
        this.level = new Level(PacmanPainter.WIDTH, PacmanPainter.HEIGHT);
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
        countdown = new Countdown(60);
        countdown.start();
    }

    /**
     * Renvoie les coordonnees du pacman
     *
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
        int x = this.pacmanPosition.getX();
        int y = this.pacmanPosition.getY();
        //Modification des coordonnees du pacman ou arret du jeu
        switch (commande) {
            case UP:
                this.pacmanPosition.setY(y - SPEEDMOVE);
                //System.out.println("Position Pacman :  "+this.pacmanPosition);
                this.direction = commande.name();
                break;
            case DOWN:
                this.pacmanPosition.setY(y + SPEEDMOVE);
                //System.out.println("Position Pacman :  "+this.pacmanPosition);
                this.direction = commande.name();
                break;
            case LEFT:
                this.pacmanPosition.setX(x - SPEEDMOVE);
                //System.out.println("Position Pacman :  "+this.pacmanPosition);
                this.direction = commande.name();
                break;
            case RIGHT:
                this.pacmanPosition.setX(x + SPEEDMOVE);
                //System.out.println("Position Pacman :  "+this.pacmanPosition);
                this.direction = commande.name();
                break;
            case EXIT:
                this.setFinish(true);
                break;
        }

        //Tester si le timer est fini
        this.setFinish(countdown.isFinish());
    }

    /**
     * Renvoie de la propriete de direction
     *
     * @return la direction du pacman
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Modifie le jeu (jeu en marche ou à l'arret)
     *
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

    /**
     * Convertit en minutes et secondes le temps restant du minuteur
     *
     * @return string minutes:secondes
     */
    public Countdown getCountdown() {
        return countdown;
    }
}
