package fr.poweroff.labyrinthe.utils;

import java.util.Objects;

/**
 * @author emeline
 */

public class Coordinate {

    private int x;
    private int y;

    /**
     * Constructeur des coordonnees du joueur
     *
     * @param x position horizontale
     * @param y position verticale
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Renvoie de la propriete de position horizontale
     *
     * @return la coordonnee horizontale
     */
    public int getX() {
        return x;
    }

    /**
     * Definit de la propriete de position horizontale
     *
     * @param x la coordonnee horizontale
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Renvoie de la propriete de position verticale
     *
     * @return la coordonnee verticale
     */
    public int getY() {
        return y;
    }

    /**
     * Definit de la propriete de position verticale
     *
     * @param y la coordonnee verticale
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}

