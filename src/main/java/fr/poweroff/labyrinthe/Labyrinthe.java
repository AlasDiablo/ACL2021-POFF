package fr.poweroff.labyrinthe;

import fr.poweroff.labyrinthe.engine.GameEngineGraphical;
import fr.poweroff.labyrinthe.model.PacmanController;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.model.PacmanPainter;
import fr.poweroff.labyrinthe.utils.AudioDriver;

import java.io.IOException;

/**
 * lancement du moteur avec le jeu
 */
public class Labyrinthe {

    public static void main(String[] args) throws InterruptedException, IOException {

        // creation du jeu particulier et de son afficheur
        PacmanGame       game       = new PacmanGame();
        PacmanPainter    painter    = new PacmanPainter(game);
        PacmanController controller = new PacmanController();

        // classe qui lance le moteur de jeu generique
        GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
        AudioDriver.init();
        engine.run();
    }

}
