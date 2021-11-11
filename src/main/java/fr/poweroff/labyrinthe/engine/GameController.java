package fr.poweroff.labyrinthe.engine;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * @author Horatiu Cirstea
 * <p>
 * controleur qui envoie des commandes au jeu
 */
public interface GameController extends KeyListener , MouseListener {

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    Cmd getCommand();

}
