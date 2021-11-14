package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.Game;
import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.event.TimeOutEvent;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.entity.Monster;
import fr.poweroff.labyrinthe.level.entity.Player;
import fr.poweroff.labyrinthe.level.tile.TileBonus;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.Countdown;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * Version avec personnage qui peut se deplacer. A completer dans les
 * versions suivantes.
 */
public class PacmanGame implements Game {

    public static final Random RANDOM;

    static {
        var seed = (long) (Math.sqrt(Math.exp(Math.random() * 65)) * 100);
        System.out.printf("Seed use: %d\n", seed);
        RANDOM = new Random(seed);
    }

    final          Level         level;
    private static PacmanGame    INSTANCE;
    final          Player        player;
    private     ArrayList<Monster> monsters;

    private final Coordinate pacmanPosition = new Coordinate(0, 0);
    /**
     * Minuteur du niveau
     */
    private final Countdown  countdown;
    private       boolean    finish         = false;
    protected     int        score;

    /**
     * Renvoie les coordonnees du pacman
     *
     * @return les coordonnee du pacman
     */
    public Coordinate getPacmanPosition() {
        return pacmanPosition;
    }

    /**
     * constructeur avec fichier source pour le help
     */
    public PacmanGame(String source) {
        ImageUtils.setClassLoader(this.getClass().getClassLoader());
        INSTANCE    = this;
        this.level  = new Level();
        this.player = new Player(new Coordinate(11, 11));
        this.monsters = new ArrayList<>() {{
                add(new Monster(new Coordinate(20, 20)));
                add(new Monster(new Coordinate(21, 21)));
                add(new Monster(new Coordinate(22, 22)));
        }};

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
        this.level.init(PacmanPainter.WIDTH, PacmanPainter.HEIGHT, this.player, this.monsters.get(0), this.monsters.get(1), this.monsters.get(2));
        countdown.start();
        score = 0;
    }

    public static void onEvent(Event<?> event) {
        System.out.println(event.getName());
        if (event.getName().equals("TimeOut")) {
            INSTANCE.setFinish(true);
        }else if (event.getName().equals("PlayerOnBonusTile")) {
            INSTANCE.score ++;
            TileBonus tb = (TileBonus) event.getData();
            tb.changeType();
            System.out.println("SCORE: " + INSTANCE.score);
        }
    }

    /**
     * faire evoluer le jeu suite a une commande
     *
     * @param commande la commande faite par le joueur
     */
    @Override
    public void evolve(Cmd commande) {

        this.level.evolve(commande);

        // arret du jeu
        if (commande == Cmd.EXIT) {
            this.setFinish(true);
        }

        //Tester si le timer est fini
        if (this.countdown.isFinish()) {
            PacmanGame.onEvent(new TimeOutEvent());
        }


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

    public int getScore() {
        return score;
    }
}
