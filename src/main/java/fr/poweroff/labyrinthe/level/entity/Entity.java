package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract class how define entity
 */
public abstract class Entity {

    /**
     * Default entity size
     */
    public static final int ENTITY_SIZE = 18;
    /**
     * Default move speed of an entity
     */
    protected final int MOVE_SPEED = 4;
    /**
     * Coordinate of the entity
     */
    protected final Coordinate coordinate;
    /**
     * List of spirits of the entity
     */
    protected final BufferedImage[] sprite;

    /**
     * Default constructor of the entity how take one sprite
     *
     * @param coordinate Coordinate of the entity
     * @param sprite     Sprite of the entity
     */
    public Entity(Coordinate coordinate, BufferedImage sprite) {
        this.coordinate = coordinate;
        this.sprite = new BufferedImage[]{sprite};
    }

    /**
     * Default constructor of the entity how take two or more sprite
     *
     * @param coordinate Coordinate of the entity
     * @param sprites    Array of sprite use by the entity
     */
    public Entity(Coordinate coordinate, BufferedImage... sprites) {
        this.coordinate = coordinate;
        this.sprite = sprites;
    }

    /**
     * Default fonction used to get the sprites
     *
     * @return An array of sprite with one or more element
     */
    public BufferedImage[] getSprite() {
        return sprite;
    }

    /**
     * Default fonction used to get the coordinate
     *
     * @return Coordinate of the entity
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Current direction of the entity
     *
     * @return Command
     */
    public abstract Cmd getDirection();

    /**
     * Function used to draw the enetity
     *
     * @param graphics Drawing object
     */
    public abstract void draw(Graphics2D graphics);

    /**
     * Function used to tick an entity
     *
     * @param cmd         Current game command
     * @param levelEvolve Level evolve object
     */
    public abstract void evolve(Cmd cmd, LevelEvolve levelEvolve);


    public void drawHitBox(Graphics2D graphics) {
        graphics.setColor(Color.BLUE);
        graphics.drawRect(this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE);
    }
}
