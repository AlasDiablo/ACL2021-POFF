package fr.poweroff.labyrinthe.model;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.engine.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 * <p>
 * controleur de type KeyListener
 */
public class PacmanController implements GameController  {

    /**
     * commande en cours
     */
    private Cmd commandeEnCours;
    private boolean menu;
    private boolean niveaux;

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
    public boolean menu() {
        return menu;
    }

    @Override
    public boolean niveau() {
        return niveaux;
    }

    @Override
    public void setMenu(boolean m) {
        this.menu = m;
    }

    @Override
    public void setNiveau(boolean n) {
        this.niveaux = n;
    }

    @Override
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
    /*
      met a jour les commandes quand le joueur relache une touche
     */
    public void keyReleased(KeyEvent e) {
        this.commandeEnCours = Cmd.IDLE;
    }

    @Override
    /*
      ne fait rien
     */
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        //Condition d'emplacement de la souris sur le menu principal
        if(menu) {
            //Clic sur "JOUER"
            if (x > 197 && y > 174 && x < 332 && y < 215) {
                this.commandeEnCours = Cmd.PLAY;
            }
            //Clic sur "NIVEAUX"
            if (x > 183 && y > 234 && x < 349 && y < 275) {
                this.commandeEnCours = Cmd.LEVELS;
            }
            //Clic sur "SCORES"
            if (x > 193 && y > 291 && x < 335 && y < 329) {
                this.commandeEnCours = Cmd.SCORES;
            }
            //Clic sur "QUITTER"
            if (x > 180 && y > 349 && x < 352 && y < 388) {
                this.commandeEnCours = Cmd.QUIT;
            }
        }

        if(niveaux) {
            //Condition d'emplacement de la souris sur le menu de difficulté
            //Clic sur "Facile"
            if (x > 220 && y > 165 && x < 348 && y < 202) {
                this.commandeEnCours = Cmd.LEVEL1;
            }
            //Clic sur "Normal"
            if (x > 204 && y > 234 && x < 360 && y < 273) {
                this.commandeEnCours = Cmd.LEVEL2;
            }
            //Clic sur "Difficile"
            if (x > 191 && y > 303 && x < 369 && y < 346) {
                this.commandeEnCours = Cmd.LEVEL3;
            }
            //Clic sur "Extreme"
            if (x > 193 && y > 375 && x < 367 && y < 416) {
                this.commandeEnCours = Cmd.LEVEL4;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
