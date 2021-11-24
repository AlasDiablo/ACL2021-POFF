package fr.poweroff.labyrinthe;

import fr.poweroff.labyrinthe.engine.GameEngineGraphical;
import fr.poweroff.labyrinthe.model.PacmanController;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.model.PacmanPainter;

/**
 * lancement du moteur avec le jeu
 */
public class Labyrinthe {

    //public static final ILogger LOGGER = new AnsiLogger(System.out, true, true, true, true, true);

    public static void main(String[] args) throws InterruptedException {

        // creation du jeu particulier et de son afficheur
        PacmanGame       game       = new PacmanGame("helpFilePacman.txt");
        PacmanPainter    painter    = new PacmanPainter(game);
        PacmanController controller = new PacmanController();

        // classe qui lance le moteur de jeu generique
        GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
        engine.run();
    }

}
