package fr.poweroff.labyrinthe.model;

/**
 * @author emeline
 */

public class Coordinate {

    private int coorX;
    private int coorY;

    /**
     * Constructeur des coordonnees du joueur
     * @param x position horizontale
     * @param y position verticale
     */
    public Coordinate(int x, int y){
        this.coorX = x;
        this.coorY = y;
    }

    /**
     * Renvoie de la propriete de position horizontale
     * @return la coordonnee horizontale
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Renvoie de la propriete de position verticale
     * @return la coordonnee verticale
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Definit de la propriete de position horizontale
     * @param coorX la coordonnee horizontale
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Definit de la propriete de position verticale
     * @param coorY la coordonnee verticale
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "coorX=" + coorX +
                ", coorY=" + coorY +
                '}';
    }
}
