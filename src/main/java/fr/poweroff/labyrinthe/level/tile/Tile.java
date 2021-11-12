package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Tile {

    private final Coordinate coordinate;

    private final BufferedImage[] sprites;

    //------------------------------------------------

    public Tile(int x, int y, BufferedImage... sp) {
        this.sprites    = sp;
        this.coordinate = new Coordinate(x, y);
    }

    public Tile(Coordinate coordinate, BufferedImage... sprites) {
        this(coordinate.getX(), coordinate.getY(), sprites);
    }

    public Tile(int x, int y, BufferedImage sp) {
        this.sprites    = new BufferedImage[]{ sp };
        this.coordinate = new Coordinate(x, y);
    }

    public Tile(Coordinate coordinate, BufferedImage sprites) {
        this(coordinate.getX(), coordinate.getY(), sprites);
    }

    //------------------------------------------------

    public abstract void draw(Graphics2D crayon);

    public BufferedImage[] getSprites() {
        return sprites;
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

    public enum Type {
        GROUND, WALL, START, END, BONUS
    }
}
