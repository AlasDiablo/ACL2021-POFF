package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.Labyrinthe;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.Game;
import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.event.TimeOutEvent;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.entity.Monster;
import fr.poweroff.labyrinthe.level.entity.Player;
import fr.poweroff.labyrinthe.level.tile.TileBonus;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.Countdown;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * Version avec personnage qui peut se deplacer. A completer dans les
 * versions suivantes.
 */
public class PacmanGame implements Game {

    public static final Random     RANDOM;
    private static      PacmanGame INSTANCE;

    static {
        var seed = (long) (Math.sqrt(Math.exp(Math.random() * 65)) * 100);
        Labyrinthe.LOGGER.info(String.format("Seed use: %d", seed));
        RANDOM = new Random(seed);
    }

    /**
     * Minuteur du niveau
     */
    public final  Countdown     countdown;
    final         Level         level;
    final         Player        player;
    private final Coordinate    pacmanPosition = new Coordinate(0, 0);
    private final List<Monster> monsters;
    protected     int           score;
    private       boolean       finish         = false;
    private       boolean       pause; //Vérifie si le jeu est en pause
    private       boolean       win            = false;
    private       int           difficult      = 1;

    /**
     * constructeur avec fichier source pour le help
     */
    public PacmanGame(String source) {
        FilesUtils.setClassLoader(this.getClass().getClassLoader());
        INSTANCE      = this;
        this.level    = new Level();
        this.player   = new Player(new Coordinate(11, 11));
        this.monsters = new ArrayList<>();

        BufferedReader helpReader;
        try {
            helpReader = new BufferedReader(new FileReader(source));
            String ligne;
            while ((ligne = helpReader.readLine()) != null) {
                Labyrinthe.LOGGER.info(ligne);
            }
            helpReader.close();
        } catch (IOException e) {
            Labyrinthe.LOGGER.warning("Help not available");
        }
        countdown  = new Countdown(600);
        score      = 0;
        this.pause = false; //Met le jeu non en pause au départ
    }

    public static void onEvent(Event<?> event) {
        Labyrinthe.LOGGER.debug(event.getName());

        if (event.getName().equals("TimeOut")) {
            INSTANCE.setFinish(true);
        } else if (event.getName().equals("PlayerOnBonusTile")) {
            INSTANCE.score++;
            TileBonus tb = (TileBonus) event.getData();
            tb.changeType();
            Labyrinthe.LOGGER.debug("SCORE: " + INSTANCE.score);
        } else if (event.getName().equals("PlayerOnEndTile")) {
            // INSTANCE.setDifficult(INSTANCE.difficult);
            // INSTANCE.level.init(PacmanPainter.WIDTH, PacmanPainter.HEIGHT, INSTANCE.player);
            INSTANCE.setWin(true);
            INSTANCE.setFinish(true);
        }
    }

    @Override
    public void setDifficult(int difficult) {
        this.difficult = difficult;
        this.monsters.clear();
        for (int i = 0; i < this.difficult * 2; i++) {
            monsters.add(new Monster(new Coordinate(0, 0)));
        }
        this.level.init(
                PacmanPainter.WIDTH, PacmanPainter.HEIGHT, this.difficult, this.player, this.monsters.toArray(new Entity[]{ })
        );
        this.compteur();
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
     * Mais en route le compteur
     */
    @Override
    public void compteur() {
        countdown.start();
    }

    /**
     * faire evoluer le jeu suite a une commande
     *
     * @param commande la commande faite par le joueur
     */
    @Override
    public void evolve(Cmd commande) {

        //Ne met à jour le jeu que si nous ne somme pas en pause
        //Sinon elle le remet à jour qu'à la prochaine fois qu'on clic sur pause
        if (!pause) this.level.evolve(commande);
        else if (commande == Cmd.PAUSE) this.level.evolve(commande);

        // arret du jeu
        if (commande == Cmd.EXIT) {
            this.setFinish(true);
        }

        //Tester si le timer est fini
        if (this.countdown.isFinish()) {
            PacmanGame.onEvent(new TimeOutEvent());
        }


        //Met en pause le jeu
        if (commande == Cmd.PAUSE) {
            Labyrinthe.LOGGER.debug("Pause !");
            if (this.pause) {
                this.compteur();
                this.setPause(false);
            } else {
                this.pause(); //Met en pause
                this.setPause(true); //signal que le jeu est en pause
            }
        }

    }

    @Override
    public boolean isWin() {
        return this.win;
    }

    @Override
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * Modifie le jeu (jeu en marche ou à l'arret)
     *
     * @param finish l'etat du jeu
     */
    @Override
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

    public void pause() {
        this.countdown.pause();
    }

    @Override
    public boolean getPause() {
        return this.pause;
    }

    @Override
    public void setPause(boolean p) {
        this.pause = p;
    }

    public int getScore() {
        return score;
    }
}
