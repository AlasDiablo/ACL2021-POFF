package fr.poweroff.labyrinthe.engine;

import fr.poweroff.labyrinthe.utils.AudioDriver;
import fr.poweroff.labyrinthe.utils.Score;

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

    private int menuPosition = 0;
    private boolean keyInputWait = false;
    private boolean quitGame = false;
    private GameState currentGameState;

    private boolean cleanNextUnprocessedFrame;
    private String name;

    /**
     * construit un moteur
     *
     * @param game           game a lancer
     * @param gamePainter    afficheur a utiliser
     * @param gameController controlleur a utiliser
     */
    public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController) {
        // creation du game
        this.game = game;
        this.gamePainter = gamePainter;
        this.gameController = gameController;
        this.cleanNextUnprocessedFrame = false;
        this.name = "";
        this.setCurrentGameState(GameState.IN_MENU);
    }

    private void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
        this.menuPosition = 0;
        if (currentGameState == GameState.IN_GAME) {
            this.gameController.setMenu(false);
            this.gameController.setNiveau(false);
            this.gameController.setBestScore(false);
        }
        if (currentGameState == GameState.IN_MENU) {
            this.gameController.setMenu(true);
            this.gameController.setNiveau(false);
            this.gameController.setBestScore(false);

        }
        if (currentGameState == GameState.IN_LEVEL) {
            this.gameController.setMenu(true);
            this.gameController.setNiveau(false);
            this.gameController.setBestScore(false);
        }
        if (currentGameState == GameState.IN_SCORE) {
            this.gameController.setMenu(false);
            this.gameController.setNiveau(false);
            this.gameController.setBestScore(true);
        }
        if (currentGameState == GameState.IN_SCORE || currentGameState == GameState.IN_MENU || currentGameState == GameState.IN_LEVEL) {
            if (AudioDriver.getCurrentMusic() != AudioDriver.Music.AMIGA) {
                AudioDriver.stopMusic();
                AudioDriver.playMusic(AudioDriver.Music.AMIGA);
            }
        }

        if (currentGameState == GameState.IN_GAME) {
            if (AudioDriver.getCurrentMusic() != AudioDriver.Music.IN_MOTION) {
                AudioDriver.stopMusic();
                AudioDriver.playMusic(AudioDriver.Music.IN_MOTION);
            }
        }
    }

    private void startGame(int difficult) {
        this.setCurrentGameState(GameState.IN_GAME);
        this.game.preStart();
        this.game.setDifficult(difficult, null);
        this.cleanNextUnprocessedFrame = true;
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

    /**
     * permet de lancer le game
     */
    public void run() {
        // creation de l'interface graphique
        this.gui = new GraphicalInterface(this.gamePainter, this.gameController);

        var frameTime = 1.0 / 30.0;
        var time = (double) System.nanoTime() / (double) 1000000000L;
        var unprocessed = 0.0;

        var cleanUnprocessedFrame = this.cleanNextUnprocessedFrame;

        while (!this.quitGame) {
            var currentTime = (double) System.nanoTime() / (double) 1000000000L;
            var passed = currentTime - time;
            unprocessed += passed;
            time = currentTime;
            if (this.cleanNextUnprocessedFrame) {
                this.cleanNextUnprocessedFrame = false;
                cleanUnprocessedFrame = true;
            }
            if (cleanUnprocessedFrame) {
                cleanUnprocessedFrame = false;
                unprocessed = 0.0;
            }
            while (unprocessed >= frameTime) {
                unprocessed -= frameTime;
                switch (this.currentGameState) {
                    case IN_MENU:
                        this.menu();
                        break;
                    case IN_LEVEL:
                        this.niveau();
                        break;
                    case IN_SCORE:
                        this.score();
                        break;
                    case IN_GAME:
                        this.jouer();
                        break;
                }
            }
        }
        System.exit(0);
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
                    this.startGame(1);
                    break;
                case 1:
                    this.setCurrentGameState(GameState.IN_LEVEL);
                    break;
                case 2:
                    this.setCurrentGameState(GameState.IN_SCORE);
                    break;
                case 3:
                    this.quitGame = true;
                    break;
            }
        }

        if (c.name().equals("PLAY")) {
            this.startGame(1);
        }

        if (c.name().equals("QUIT")) this.quitGame = true;

        if (c.name().equals("LEVELS")) {
            this.setCurrentGameState(GameState.IN_LEVEL);
        }

        if (c.name().equals("SCORES")) {
            this.setCurrentGameState(GameState.IN_SCORE);
//            this.gameController.setMenu(false);
//            boolean scoreEnCour = true;
//            while(scoreEnCour){
//                c = this.gameController.getCommand();
//                this.gameController.setBestScore(true);
//                this.gui.paintScore();
//                if(c.name().equals("RETOUR")) scoreEnCour = false;
//            }
//            this.gameController.setBestScore(false);
        }
    }

    private void niveau() {
        Cmd c = this.gameController.getCommand();

        this.handleKeyboardOnMenu(c);

        this.gui.paintNiveau(this.menuPosition);

        if (c == Cmd.RETURN) {
            this.setCurrentGameState(GameState.IN_MENU);
        }

        if (c == Cmd.ENTER && !this.keyInputWait) {
            this.keyInputWait = true;
            AudioDriver.playSelect();
            switch (this.menuPosition) {
                case 0:
                    this.startGame(1);
                    break;
                case 1:
                    this.startGame(2);
                    break;
                case 2:
                    this.startGame(3);
                    break;
                case 3:
                    this.startGame(4);
            }
        }

        switch (c.name()) {
            case "LEVEL1":
                this.startGame(1);
                break;
            case "LEVEL2":
                this.startGame(2);
                break;
            case "LEVEL3":
                this.startGame(3);
                break;
            case "LEVEL4":
                this.startGame(4);
                break;
        }
    }

    private void score() {
        Cmd c = this.gameController.getCommand();

        this.gui.paintScore();

        if (c == Cmd.RETURN || c.name().equals("RETOUR")) {
            this.setCurrentGameState(GameState.IN_MENU);
        }
    }

    private void jouer() {
        // demande controle utilisateur
        Cmd c = this.gameController.getCommand();
        // affiche le game
        if (this.game.isFinished()) {
            var ch = this.gameController.getRawCommand();

            if ((ch != (char) -1 && Character.isLetter(ch)) || c == Cmd.RETURN || c == Cmd.ENTER) {
                AudioDriver.playSelect();
            }

            if (this.name.length() <= 8) {
                if (ch != (char) -1 && Character.isLetter(ch)) {
                    var letter = Character.toString(ch);
                    this.name += letter;
                }
            }

            if (c == Cmd.RETURN && this.name.length() > 0) {
                this.name = this.name.substring(0, this.name.length() - 1);
            }

            if (c == Cmd.ENTER) {
                Score.addScore(this.name, this.game.getScore());
                this.name = "";
                this.setCurrentGameState(GameState.IN_MENU);
            }

            this.gui.paintFin(this.name);
        } else {
            // fait evoluer le game
            this.game.evolve(c);
            // affiche le game
            this.gui.paint();
        }
        // met en attente
    }

    private enum GameState {
        IN_GAME, IN_MENU, IN_SCORE, IN_LEVEL
    }

}
