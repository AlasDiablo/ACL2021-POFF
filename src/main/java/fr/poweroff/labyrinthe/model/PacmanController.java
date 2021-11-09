package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.GameController;

import java.awt.event.KeyEvent;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * controleur de type KeyListener
 */
public class PacmanController implements GameController {

    /**
     * commande en cours
     */
    private Cmd commandeEnCours;

    /**
     * construction du controleur par defaut le controleur n'a pas de commande
     */
    public PacmanController() {
        this.commandeEnCours = Cmd.IDLE;
    }

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    public Cmd getCommand() {
        return this.commandeEnCours;
    }

    @Override
    /**
     * met a jour les commandes en fonctions des touches appuyees
     */
    public void keyPressed(KeyEvent e) {
        //Mouvement avec les touches des lettres
        switch (e.getKeyChar()) {
            // si on appuie sur 'q',commande joueur est gauche
            case 'q':
            case 'Q':
                this.commandeEnCours = Cmd.LEFT;
                break;
            case 'd':
            case 'D':
                this.commandeEnCours = Cmd.RIGHT;
                break;
            case 'z':
            case 'Z':
                this.commandeEnCours = Cmd.UP;
                break;
            case 's':
            case 'S':
                this.commandeEnCours = Cmd.DOWN;
                break;
        }
        //Mouvement avec les touches des flèches
        switch (e.getKeyCode()) {
            // si on appuie sur 'q',commande joueur est gauche
            case KeyEvent.VK_LEFT:
                this.commandeEnCours = Cmd.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                this.commandeEnCours = Cmd.RIGHT;
                break;
            case KeyEvent.VK_UP:
                this.commandeEnCours = Cmd.UP;
                break;
            case KeyEvent.VK_DOWN:
                this.commandeEnCours = Cmd.DOWN;
                break;
            case KeyEvent.VK_ESCAPE:
                this.commandeEnCours = Cmd.EXIT;
                break;
        }

    }

    @Override
    /**
     * met a jour les commandes quand le joueur relache une touche
     */
    public void keyReleased(KeyEvent e) {
        this.commandeEnCours = Cmd.IDLE;
    }

    @Override
    /**
     * ne fait rien
     */
    public void keyTyped(KeyEvent e) {

    }

}
