package fr.poweroff.labyrinthe.utils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * @author nicolas
 * Minuteur du jeu
 */
public class Countdown {

    /**
     * Delai de une seconde
     */
    private final int   DELAY = 1000;
    /**
     * Duree du decompte
     */
    private final int   timeLimit;
    private final Timer timer;
    /**
     * Temps restant
     */
    private       int   time;

    public Countdown(int timeLimit) {
        this.timeLimit = timeLimit;
        this.time      = timeLimit;

        this.timer = new Timer(DELAY, e -> {
            if (time > 0) {
                time--;
            } else {
                time = timeLimit;
            }
        });
    }

    /**
     * Demarre le decompte
     */
    public void start() {
        this.timer.start();
    }

    /**
     * Mettre en pause le decompte
     */
    public void pause() {
        this.timer.stop();
    }

    /**
     * @return le temps restant
     */
    public int getTime() {
        return this.time;
    }

    /**
     * @return True si le le decompte est a zero
     */
    public boolean isFinish() {
        return this.time == 0;
    }

    /**
     * @return une chaine contenant le nombre de minutes et de secondes restantes
     */
    public String getMinutesSeconds() {
        String minuteString = "";
        String secondString = "";

        long minute = TimeUnit.SECONDS.toMinutes(time) - TimeUnit.SECONDS.toMinutes(TimeUnit.SECONDS.toHours(time) * 60);
        long second = TimeUnit.SECONDS.toSeconds(time) - TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(time) * 60);

        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = Long.toString(minute);
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = Long.toString(second);
        }

        return minuteString + ":" + secondString;
    }
}
