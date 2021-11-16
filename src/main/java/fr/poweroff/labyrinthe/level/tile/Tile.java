package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Abstract class how define the base of a tile
 */
public abstract class Tile {

    /**
     * Coordinate of the tile
     */
    private final Coordinate coordinate;

    /**
     * List of sprites use by the tile
     */
    private final BufferedImage[] sprites;

    /**
     * Constructor how take coordinate and spirits
     *
     * @param x       X coordinate
     * @param y       Y coordinate
     * @param sprites Array of sprite
     */
    public Tile(int x, int y, BufferedImage... sprites) {
        this.sprites    = sprites;
        this.coordinate = new Coordinate(x, y);
    }

    /**
     * Constructor how take coordinate and spirits
     *
     * @param coordinate Coordinate object
     * @param sprites    Array of sprite
     */
    public Tile(Coordinate coordinate, BufferedImage... sprites) {
        this(coordinate.getX(), coordinate.getY(), sprites);
    }

    /**
     * Constructor how take coordinate and spirit
     *
     * @param x      X coordinate
     * @param y      Y coordinate
     * @param sprite Array of sprite
     */
    public Tile(int x, int y, BufferedImage sprite) {
        this.sprites    = new BufferedImage[]{ sprite };
        this.coordinate = new Coordinate(x, y);
    }

    /**
     * Constructor how take coordinate and spirits
     *
     * @param coordinate Coordinate object
     * @param sprite     Array of sprite
     */
    public Tile(Coordinate coordinate, BufferedImage sprite) {
        this(coordinate.getX(), coordinate.getY(), sprite);
    }

    /**
     * Abstract function use to draw the tile
     *
     * @param graphics2D drawing object
     */
    public abstract void draw(Graphics2D graphics2D);

    /**
     * Get the sprites list
     *
     * @return Return the sprites list as an array
     */
    public BufferedImage[] getSprites() {
        return sprites;
    }

    /**
     * Get the coordinate of the tile
     *
     * @return Coordinate object
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Abstract function use to return the type of the tile
     *
     * @return Type on the tile
     */
    public abstract Type getType();

    /**
     * Function used to check if a tile is equals to another
     *
     * @param o Tile to compare
     *
     * @return <ul><li>True if this is equals to o</li><li>False if this is not equals to o</li></ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return coordinate.equals(tile.coordinate) && getType().equals(tile.getType());
    }

    /**
     * Create a hash code with the coordinate and the type
     *
     * @return Return the hash of the tile
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinate, getType());
    }

    /**
     * Enum used to define the type of the tile
     */
    public enum Type {
        GROUND, WALL, START, END, BONUS
    }
}
