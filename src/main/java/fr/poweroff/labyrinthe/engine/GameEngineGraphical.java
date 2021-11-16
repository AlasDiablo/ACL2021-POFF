package fr.poweroff.labyrinthe.engine;

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
    public void run() throws InterruptedException {

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
                menuEnCour = false;
            }

            if (c.name().equals("QUIT"))
                this.gui.quit();

            if (c.name().equals("LEVELS")) {
                niveau     = true;
                menuEnCour = false;
            }

        }

        this.gameController.setMenu(false);
    }

    private void niveau() {
        while (niveau) {
            Cmd c = this.gameController.getCommand();
            this.gameController.setNiveau(true);
            this.gui.paintNiveau();

            if (c.name().equals("LEVEL1"))
                niveau = false;
        }
        this.gameController.setNiveau(false);
    }

    private void jouer() throws InterruptedException {
        // boucle de game
        while (!this.game.isFinished()) {
            // demande controle utilisateur
            Cmd c = this.gameController.getCommand();
            // fait evoluer le game
            this.game.evolve(c);
            //Mise en route du compteur
            this.game.Compteur();
            // affiche le game
            this.gui.paint();
            // met en attente
            Thread.sleep(33, 333);

            if (this.game.isFinishCompteur()) {
                this.gui.paintPerdu(); //Affichage de la page game_over
                this.game.setFinish(true); //Pour mettre fin au jeu
            }
        }
    }

}
