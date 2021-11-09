package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected final int             MOVE_SPEED = 4;
    protected final Coordinate      coordinate;
    protected final BufferedImage[] sprite;

    public Entity(Coordinate coordinate, BufferedImage sprite) {
        this.coordinate = coordinate;
        this.sprite     = new BufferedImage[]{ sprite };
    }

    public Entity(Coordinate coordinate, BufferedImage... sprites) {
        this.coordinate = coordinate;
        this.sprite     = sprites;
    }

    public BufferedImage[] getSprite() {
        return sprite;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public abstract void draw(Graphics2D graphics);

    public abstract void evolve(Cmd cmd, LevelEvolve levelEvolve);
}
