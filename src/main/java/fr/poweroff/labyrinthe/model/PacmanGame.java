package fr.poweroff.labyrinthe.model;

import com.google.common.collect.Lists;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.Game;
import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.event.TimeOutEvent;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.entity.Monster;
import fr.poweroff.labyrinthe.level.entity.Player;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileBonus;
import fr.poweroff.labyrinthe.level.tile.special.*;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.Countdown;
import fr.poweroff.labyrinthe.utils.FilesUtils;

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
        RANDOM = new Random(seed);
    }

    /**
     * Minuteur du niveau
     */
    public final  Countdown     countdown;
    final         Level         level;
    protected     int           score;
    private       boolean       finish         = false;
    private       boolean       pause; //Vérifie si le jeu est en pause
    private       boolean       win            = false;
    protected     int           life; //nb de vie
    protected     int           munition; //Nombre de munition qu'a le joueur

    /**
     * constructeur avec fichier source pour le help
     */
    public PacmanGame() {
        FilesUtils.setClassLoader(this.getClass().getClassLoader());
        INSTANCE   = this;
        this.level = new Level();
        countdown  = new Countdown(60);
        score      = 0;
        this.pause = false; //Met le jeu non en pause au départ
        this.life = 3; //Mettre un max de 10 environ
        this.munition = 0;
    }

    public static void onEvent(Event<?> event) {
        if (event.getName().equals("TimeOut")) {
            INSTANCE.setFinish(true);
        } else if (event.getName().equals("PlayerOnBonusTile")) {
            INSTANCE.score++;
            TileBonus tb = (TileBonus) event.getData();
            tb.changeType();
        } else if (event.getName().equals("PlayerOnLifeBonusTile")) {
            INSTANCE.life++;
            TileLife tb = (TileLife) event.getData();
            tb.changeType();
        }else if(event.getName().equals("PlayerOnTimeBonusTile")){
            INSTANCE.countdown.setTime();
            TileTime tb = (TileTime) event.getData();
            tb.changeType();
        } else if(event.getName().equals("PlayerOnMunitionBonusTile")){
            INSTANCE.munition ++;
            TileMunitions tm = (TileMunitions) event.getData();
            tm.changeType();
        } else if(event.getName().equals("PlayerOnTreasureBonusTile")){
            INSTANCE.score += 5;
            TileTreasure tt = (TileTreasure) event.getData();
            tt.changeType();
        } else if(event.getName().equals("PlayerOnTrapTile")){
            INSTANCE.score -= 5;
            INSTANCE.life--;
            TileTrap tt = (TileTrap) event.getData();
            tt.changeType();
        }
        else if (event.getName().equals("PlayerOnEndTile")) {
            // INSTANCE.setDifficult(INSTANCE.difficult);
            // INSTANCE.level.init(PacmanPainter.WIDTH, PacmanPainter.HEIGHT, INSTANCE.player);

            //Si le jeu est terminé, le joueur augmente son score avec les munitions qui lui restait
            if(INSTANCE.getMunition() > 0){
                INSTANCE.score += INSTANCE.getMunition();
            }
            INSTANCE.setWin(true);
            INSTANCE.setFinish(true);
        }
    }

    @Override
    public void setDifficult(int difficult) {
        List<Entity> monsters = Lists.newArrayList();
        for (int i = 0; i < difficult * 2 - 2; i++) {
            monsters.add(new Monster(new Coordinate(0, 0)));
        }
        this.level.init(
                PacmanPainter.WIDTH, PacmanPainter.HEIGHT, difficult, new Player(), monsters.toArray(new Entity[]{ })
        );
        this.compteur();
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

    public int getLife() {
        return life;
    }

    public int getMunition() {
        return munition;
    }

    public void minuslife(){
        this.life--;
    }
}
