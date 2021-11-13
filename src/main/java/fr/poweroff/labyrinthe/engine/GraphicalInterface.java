package fr.poweroff.labyrinthe.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

    private JFrame f ;

    /**
     * la construction de l'interface graphique: JFrame avec panel pour le game
     *  @param gamePainter    l'afficheur a utiliser dans le moteur
     * @param gameController l'afficheur a utiliser dans le moteur
     */
    public GraphicalInterface(GamePainter gamePainter, GameController gameController) {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // attacher le panel contenant l'afficheur du game
        this.panel = new DrawingPanel(gamePainter);
        f.setContentPane(this.panel);

        // attacher controller au panel du game
        this.panel.addKeyListener(gameController);
        this.panel.addMouseListener(gameController);

        // Créer la barre de menu
        JMenuBar menubar = new JMenuBar();
        // Créer le menu
        JMenu menu = new JMenu("Menu");
        // Créer les éléments du menu
        JMenuItem e1, e2, e3, e4;
        e1 = new JMenuItem("Rejouer");
        e2 = new JMenuItem("Niveaux");
        e3 = new JMenuItem("Scores");
        e4 = new JMenuItem("Quitter");


        //Quitte le jeu
        e4.addActionListener(actionEvent -> f.dispose());
        //Ajout des item au menu
        menu.add(e1);
        menu.add(e2);
        menu.add(e3);
        menu.add(e4);
        //Ajout du menu à la barre
        menubar.add(menu);

        menubar.setBackground(Color.GRAY);
        menu.setBackground(Color.GRAY);
        e1.setBackground(Color.GRAY);
        e2.setBackground(Color.GRAY);
        e3.setBackground(Color.GRAY);
        e4.setBackground(Color.GRAY);

        // Ajouter la barre de menu au frame
        f.setJMenuBar(menubar);

        f.pack();
        f.setVisible(true);
        f.getContentPane().setFocusable(true);
        f.getContentPane().requestFocus();
    }

    /**
     * mise a jour du dessin
     */
    public void paintMenu() {
        this.panel.drawMenu();
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
    public void quit(){
        this.f.dispose();
    }

    public void paintNiveau() {
        this.panel.drawNiveau();
    }

    public void paintPerdu() {
        this.panel.drawPerdu();
    }
}
