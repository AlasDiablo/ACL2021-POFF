package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.event.HandleEvent;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Tile implements HandleEvent {

    private final Coordinate coordinate;

    private final BufferedImage sprite;

    //------------------------------------------------
    public Tile() {
        this.coordinate = new Coordinate(-1, -1);
        this.sprite     = null;
    }

    public Tile(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        this.sprite     = null;
    }

    public Tile(Coordinate coordinate) {
        this(coordinate.getX(), coordinate.getY());
    }

    public Tile(BufferedImage sp, int x, int y) {
        this.sprite     = sp;
        this.coordinate = new Coordinate(x, y);
    }

    public Tile(BufferedImage sprite, Coordinate coordinate) {
        this(sprite, coordinate.getX(), coordinate.getY());
    }

    //------------------------------------------------

    public abstract void draw(Graphics2D crayon);

    public BufferedImage getSprite() {
        return sprite;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public abstract Type getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return coordinate.equals(tile.coordinate) && getType().equals(tile.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, getType());
    }

    protected enum Type {
        GROUND, WALL, START, END
    }
}
