package fr.poweroff.labyrinthe.model;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * @author nicolas
 *  Minuteur du jeu
 */
public class Countdown {

    private Timer timer;

    /**
     * Duree du decompte
     */
    private int timeLimit;

    /**
     * Temps restant
     */
    private int time;

    /**
     * Delai de une seconde
     */
    private int DELAY = 1000;

    public Countdown( int timeLimit) {
        this.timeLimit = timeLimit;
        this.time = timeLimit;

        this.timer = new Timer(DELAY, e -> {
            if (time > 0){
                time--;
            }else {
                time = timeLimit;
            }
        });
    }

    /**
     * Demarre le decompte
     */
    public void start(){
        this.timer.start();
    }

    /**
     *
     * @return le
     */
    public int getTime() {
        return this.time;
    }
}
