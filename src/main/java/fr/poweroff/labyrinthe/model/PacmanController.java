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
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Cmd     other;

    /**
     * construction du controleur par defaut le controleur n'a pas de commande
     */
    public PacmanController() {
        this.left  = false;
        this.right = false;
        this.up    = false;
        this.down  = false;
        this.other = null;
    }

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    public Cmd getCommand() {
        if (this.other != null) return this.other;
        if (this.left && this.up) return Cmd.LEFT_UP;
        if (this.left && this.down) return Cmd.LEFT_DOWN;
        if (this.right && this.up) return Cmd.RIGHT_UP;
        if (this.right && this.down) return Cmd.RIGHT_DOWN;
        if (this.left) return Cmd.LEFT;
        if (this.right) return Cmd.RIGHT;
        if (this.up) return Cmd.UP;
        if (this.down) return Cmd.DOWN;
        return Cmd.IDLE;
    }

    @Override
    /**
     * met a jour les commandes en fonctions des touches appuyees
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q: // azerty
            case KeyEvent.VK_A: // qwerty
            case KeyEvent.VK_LEFT: // arrow
                this.left = true;
                break;
            case KeyEvent.VK_D: // azerty and qwerty
            case KeyEvent.VK_RIGHT: // arrow
                this.right = true;
                break;
            case KeyEvent.VK_Z: // azerty
            case KeyEvent.VK_W: // qwerty
            case KeyEvent.VK_UP: // arrow
                this.up = true;
                break;
            case KeyEvent.VK_S: // azerty and qwerty
            case KeyEvent.VK_DOWN: // arrow
                this.down = true;
                break;
            case KeyEvent.VK_ESCAPE:
                this.other = Cmd.EXIT;
                break;
        }
    }

    @Override
    /**
     * met a jour les commandes quand le joueur relache une touche
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q: // azerty
            case KeyEvent.VK_A: // qwerty
            case KeyEvent.VK_LEFT: // arrow
                this.left = false;
                break;
            case KeyEvent.VK_D: // azerty and qwerty
            case KeyEvent.VK_RIGHT: // arrow
                this.right = false;
                break;
            case KeyEvent.VK_Z: // azerty
            case KeyEvent.VK_W: // qwerty
            case KeyEvent.VK_UP: // arrow
                this.up = false;
                break;
            case KeyEvent.VK_S: // azerty and qwerty
            case KeyEvent.VK_DOWN: // arrow
                this.down = false;
                break;
            case KeyEvent.VK_ESCAPE:
                this.other = Cmd.EXIT;
                break;
        }
    }

    @Override
    /**
     * ne fait rien
     */
    public void keyTyped(KeyEvent e) {

    }

}
