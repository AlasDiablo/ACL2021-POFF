package fr.poweroff.labyrinthe.engine;

import fr.poweroff.labyrinthe.utils.Score;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * moteur de game generique.
 * On lui passe un game et un afficheur et il permet d'executer un game.
 */
public class GameEngineGraphical {

    /**
     * le game a executer
     */
    private final Game game;

    /**
     * l'afficheur a utiliser pour le rendu
     */
    private final GamePainter gamePainter;

    /**
     * le controlleur a utiliser pour recuperer les commandes
     */
    private final GameController gameController;

    /**
     * l'interface graphique
     */
    private GraphicalInterface gui;

    private boolean menuEnCour;
    private boolean niveau;

    /**
     * construit un moteur
     *
     * @param game           game a lancer
     * @param gamePainter    afficheur a utiliser
     * @param gameController controlleur a utiliser
     */
    public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController) {
        // creation du game
        this.game           = game;
        this.gamePainter    = gamePainter;
        this.gameController = gameController;
    }

    /**
     * permet de lancer le game
     */
    public void run() throws InterruptedException, IOException {

        // creation de l'interface graphique
        this.gui = new GraphicalInterface(this.gamePainter, this.gameController);

        menuEnCour = true;
        niveau     = false;

        menu();

        niveau();

        jouer();

    }

    private void menu() {
        //boucle de menu
        while (menuEnCour) {
            Cmd c = this.gameController.getCommand();
            this.gameController.setMenu(true);
            //this.game.evolve(c);
            this.gui.paintMenu();

            //Lancement du jeu (arrêt de la boucle du menu)
            if (c.name().equals("PLAY")) {
                this.game.setDifficult(1);
                menuEnCour = false;
            }

            if (c.name().equals("QUIT"))
                this.gui.quit();

            if (c.name().equals("LEVELS")) {
                niveau     = true;
                menuEnCour = false;
            }
            if(c.name().equals("SCORES")){
                this.gameController.setMenu(false);
                boolean scoreEnCour = true;
                while(scoreEnCour){
                    c = this.gameController.getCommand();
                    this.gameController.setBestScore(true);
                    this.gui.paintScore();
                    if(c.name().equals("RETOUR")) scoreEnCour = false;
                }
                this.gameController.setBestScore(false);
            }

        }

        this.gameController.setMenu(false);
    }

    private void niveau() {
        while (niveau) {
            Cmd c = this.gameController.getCommand();
            this.gameController.setNiveau(true);
            this.gui.paintNiveau();

            switch (c.name()) {
                case "LEVEL1":
                    this.game.setDifficult(1);
                    niveau = false;
                    break;
                case "LEVEL2":
                    this.game.setDifficult(2);
                    niveau = false;
                    break;
                case "LEVEL3":
                    this.game.setDifficult(3);
                    niveau = false;
                    break;
                case "LEVEL4":
                    this.game.setDifficult(4);
                    niveau = false;
                    break;
            }
        }
        this.gameController.setNiveau(false);
    }

    private void jouer() throws InterruptedException, IOException {
        // boucle de game
        while (!this.game.isFinished()) {
            // demande controle utilisateur
            Cmd c = this.gameController.getCommand();
            // fait evoluer le game
            this.game.evolve(c);

            // affiche le game
            if(this.game.getPause()) {
                /*while(this.game.getPause()){
                    c = this.gameController.getCommand();
                    this.gui.paintPause();
                    // fait evoluer le game
                    this.game.evolve(c);
                }*/
                //this.gui.paintPause();
            } else if (this.game.isFinished()) {
                Score score = new Score();
                //Sauvegarde du score
                try {
                    score.addFile(this.game.getScore());
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (this.game.isWin()) this.gui.paintGagne(); //Affichage de la page game_win
                else this.gui.paintPerdu(); //Affichage de la page game_over
            } else {
                // affiche le game
                this.gui.paint();
            }
            // met en attente
            Thread.sleep(33, 333);
        }
    }

}
