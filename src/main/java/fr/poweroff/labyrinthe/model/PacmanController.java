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
public class PacmanController implements GameController {

    /**
     * commande en cours
     */
    private boolean menu;
    private boolean niveaux;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Cmd     other;
    private Cmd     pause;

    /**
     * construction du controleur par defaut le controleur n'a pas de commande
     */
    public PacmanController() {
        this.left  = false;
        this.right = false;
        this.up    = false;
        this.down  = false;
        this.other = null;
        this.pause = null;
    }

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    public Cmd getCommand() {
        if (this.other != null) return this.other;
        if (this.pause != null) {
            this.pause = null;
            return Cmd.PAUSE;
        }
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
            case KeyEvent.VK_P:
                this.pause = Cmd.PAUSE;
                break;
            case KeyEvent.VK_ESCAPE:
                this.other = Cmd.EXIT;
                break;
        }
    }

    @Override
    /*
      met a jour les commandes quand le joueur relache une touche
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
            default:
                this.other = null;
                break;
        }
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
        if (menu) {
            //Clic sur "JOUER"
            if (x > 197 && y > 174 && x < 332 && y < 215) {
                this.other = Cmd.PLAY;
            }
            //Clic sur "NIVEAUX"
            if (x > 183 && y > 234 && x < 349 && y < 275) {
                this.other = Cmd.LEVELS;
            }
            //Clic sur "SCORES"
            if (x > 193 && y > 291 && x < 335 && y < 329) {
                this.other = Cmd.SCORES;
            }
            //Clic sur "QUITTER"
            if (x > 180 && y > 349 && x < 352 && y < 388) {
                this.other = Cmd.QUIT;
            }
        }

        if (niveaux) {
            //Condition d'emplacement de la souris sur le menu de difficulté
            //Clic sur "Facile"
            if (x > 220 && y > 165 && x < 348 && y < 202) {
                this.other = Cmd.LEVEL1;
            }
            //Clic sur "Normal"
            if (x > 204 && y > 234 && x < 360 && y < 273) {
                this.other = Cmd.LEVEL2;
            }
            //Clic sur "Difficile"
            if (x > 191 && y > 303 && x < 369 && y < 346) {
                this.other = Cmd.LEVEL3;
            }
            //Clic sur "Extreme"
            if (x > 193 && y > 375 && x < 367 && y < 416) {
                this.other = Cmd.LEVEL4;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.other = null;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
