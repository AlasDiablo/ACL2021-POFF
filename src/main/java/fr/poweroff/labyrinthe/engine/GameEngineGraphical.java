package fr.poweroff.labyrinthe.engine;

import fr.poweroff.labyrinthe.utils.AudioDriver;

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
    private int     menuPosition = 0;
    private boolean keyInputWait = false;

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
        this.niveau         = false;
        this.menuEnCour     = true;
    }

    private void playLevelMusic() {
        AudioDriver.playMusic(AudioDriver.Music.IN_MOTION);
    }

    private void setNiveau(boolean niveau) {
        this.niveau = niveau;
        this.gameController.setNiveau(niveau);
    }

    private void setMenuEnCour(boolean menuEnCour) {
        this.menuEnCour = menuEnCour;
        this.gameController.setMenu(menuEnCour);
    }

    /**
     * permet de lancer le game
     */
    public void run() throws InterruptedException {

        // creation de l'interface graphique
        this.gui = new GraphicalInterface(this.gamePainter, this.gameController);

        AudioDriver.playMusic(AudioDriver.Music.AMIGA);

        while (!this.game.isFinished()) {
            if (this.menuEnCour && !this.niveau) menu();
            else if (!this.menuEnCour && this.niveau) niveau();
            else {
                jouer();
            }

            Thread.sleep(33, 333);
        }
    }

    private void handleKeyboardOnMenu(Cmd c) {
        if (c == Cmd.IDLE && this.keyInputWait) {
            this.keyInputWait = false;
        }

        if (c == Cmd.UP && !this.keyInputWait) {
            this.keyInputWait = true;
            AudioDriver.playSelect();

            this.menuPosition--;
            if (this.menuPosition < 0) this.menuPosition = 3;
        }

        if (c == Cmd.DOWN && !this.keyInputWait) {
            this.keyInputWait = true;
            AudioDriver.playSelect();

            this.menuPosition++;
            if (this.menuPosition > 3) this.menuPosition = 0;
        }
    }

    private void menu() {
        Cmd c = this.gameController.getCommand();

        this.handleKeyboardOnMenu(c);

        this.gui.paintMenu(this.menuPosition);

        if (c == Cmd.ENTER && !this.keyInputWait) {
            this.keyInputWait = true;
            AudioDriver.playSelect();

            switch (this.menuPosition) {
                case 0:
                    this.setMenuEnCour(false);
                    this.playLevelMusic();
                    this.game.setDifficult(1);
                    break;
                case 1:
                    this.menuPosition = 0;
                    this.setNiveau(true);
                    this.setMenuEnCour(false);
                    break;
                case 2:
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }

        if (c.name().equals("PLAY")) {
            this.game.setDifficult(1);
            this.setMenuEnCour(false);
        }

        if (c.name().equals("QUIT")) System.exit(0);

        if (c.name().equals("LEVELS")) {
            this.menuPosition = 0;
            this.setNiveau(true);
            this.setMenuEnCour(false);
        }
    }

    private void niveau() {
        Cmd c = this.gameController.getCommand();

        this.handleKeyboardOnMenu(c);

        this.gui.paintNiveau(this.menuPosition);

        if (c == Cmd.ENTER && !this.keyInputWait) {
            this.keyInputWait = true;
            AudioDriver.playSelect();
            this.playLevelMusic();

            switch (this.menuPosition) {
                case 0:
                    this.game.setDifficult(1);
                    this.setNiveau(false);
                    break;
                case 1:
                    this.game.setDifficult(2);
                    this.setNiveau(false);
                    break;
                case 2:
                    this.game.setDifficult(3);
                    this.setNiveau(false);
                    break;
                case 3:
                    this.game.setDifficult(4);
                    this.setNiveau(false);
                    break;
            }
        }

        switch (c.name()) {
            case "LEVEL1":
                this.game.setDifficult(1);
                this.setNiveau(false);
                break;
            case "LEVEL2":
                this.game.setDifficult(2);
                this.setNiveau(false);
                break;
            case "LEVEL3":
                this.game.setDifficult(3);
                niveau = false;
                break;
            case "LEVEL4":
                this.game.setDifficult(4);
                this.setNiveau(false);
                break;
        }
    }

    private void jouer() {
        // demande controle utilisateur
        Cmd c = this.gameController.getCommand();
        // fait evoluer le game
        this.game.evolve(c);

        // affiche le game
        if (this.game.getPause()) {
            //this.gui.paintPause();
        } else if (this.game.isFinished()) {
            if (this.game.isWin()) this.gui.paintGagne(); //Affichage de la page game_win
            else this.gui.paintPerdu(); //Affichage de la page game_over
        } else {
            // affiche le game
            this.gui.paint();
        }
        // met en attente
    }

}
