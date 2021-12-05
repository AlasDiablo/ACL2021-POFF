package fr.poweroff.labyrinthe.engine;

import javax.swing.*;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * interface graphique avec son controller et son afficheur
 */
public class GraphicalInterface {

    /**
     * le Panel pour l'afficheur
     */
    private final DrawingPanel panel;

    private final JFrame f;

    /**
     * la construction de l'interface graphique: JFrame avec panel pour le game
     *
     * @param gamePainter    l'afficheur a utiliser dans le moteur
     * @param gameController l'afficheur a utiliser dans le moteur
     */
    public GraphicalInterface(GamePainter gamePainter, GameController gameController) {
        f = new JFrame();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // attacher le panel contenant l'afficheur du game
        this.panel = new DrawingPanel(gamePainter);
        f.setContentPane(this.panel);

        // attacher controller au panel du game
        this.panel.addKeyListener(gameController);
        this.panel.addMouseListener(gameController);

        f.pack();
        f.setVisible(true);
        f.getContentPane().setFocusable(true);
        f.getContentPane().requestFocus();
    }

    /**
     * mise a jour du dessin
     *
     * @param menuPosition
     */
    public void paintMenu(int menuPosition) {
        this.panel.drawMenu(menuPosition);
    }

    /**
     * mise a jour du dessin
     */
    public void paint() {
        this.panel.drawGame();
    }

    /**
     * Fonction pour quitter la map
     */
    public void quit() {
        this.f.dispose();
    }

    public void paintNiveau(int menuPosition) {
        this.panel.drawNiveau(menuPosition);
    }

    public void paintPerdu() {
        this.panel.drawPerdu();
    }

    public void paintPause() {
        this.panel.drawPause();
    }

    public void paintGagne() {
        this.panel.drawGagne();
    }

    public void paintScore() {
        this.panel.drawBestScore();
    }
}
