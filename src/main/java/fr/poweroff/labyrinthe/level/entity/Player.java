package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class how define the player entity
 */
public class Player extends Entity {
    /**
     * The player sprite path
     */
    private static final String SPRITE_PATH = "assets/textures/player/";

    /**
     * The current player sprite
     */
    private int spriteIndex;

    /**
     * Default constructor of the player
     */
    public Player() {
        super(
                new Coordinate(0, 0),
                3,
                Player.getSprite("pacman_right.png"),
                Player.getSprite("pacman_down.png"),
                Player.getSprite("pacman_left.png"),
                Player.getSprite("pacman_up.png"),
                Player.getSprite("pacman_right_up.png"),
                Player.getSprite("pacman_right_down.png"),
                Player.getSprite("pacman_left_up.png"),
                Player.getSprite("pacman_left_down.png")
        );
        this.spriteIndex = 0;
    }

    /**
     * Funcion use to get the player sprite
     *
     * @param name the name of the sprite
     *
     * @return the sprite as a buffered image
     */
    private static BufferedImage getSprite(String name) {
        return FilesUtils.getImage(SPRITE_PATH + name);
    }

    /**
     * Fonction used to draw the player
     *
     * @param graphics Drawing object
     */
    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                this.getSprite()[this.spriteIndex],
                this.getCoordinate().getX(),
                this.getCoordinate().getY(),
                ENTITY_SIZE, ENTITY_SIZE, null
        );
    }

    /**
     * Function used to tick the player
     *
     * @param cmd         the current command
     * @param levelEvolve the level evolve instance use for overlapping check
     */
    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();

        switch (cmd) {
            case DOWN:
                this.spriteIndex = 1;
                break;
            case RIGHT:
                this.spriteIndex = 0;
                break;
            case LEFT:
                this.spriteIndex = 2;
                break;
            case UP:
                this.spriteIndex = 3;
                break;
            case LEFT_DOWN:
                this.spriteIndex = 7;
                break;
            case LEFT_UP:
                this.spriteIndex = 6;
                break;
            case RIGHT_DOWN:
                this.spriteIndex = 5;
                break;
            case RIGHT_UP:
                this.spriteIndex = 4;
                break;
        }

        if (cmd == Cmd.UP || cmd == Cmd.LEFT_UP || cmd == Cmd.RIGHT_UP) {
            var newY = y - MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(newY);
            else if (levelEvolve.notOverlap(x, y - 1, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(y - 1);
        }

        if (cmd == Cmd.DOWN || cmd == Cmd.LEFT_DOWN || cmd == Cmd.RIGHT_DOWN) {
            var newY = y + MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(newY);
            else if (levelEvolve.notOverlap(x, y + 1, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(y + 1);
        }

        if (cmd == Cmd.LEFT || cmd == Cmd.LEFT_DOWN || cmd == Cmd.LEFT_UP) {
            var newX = x - MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(newX);
            else if (levelEvolve.notOverlap(x - 1, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(x - 1);
        }

        if (cmd == Cmd.RIGHT || cmd == Cmd.RIGHT_DOWN || cmd == Cmd.RIGHT_UP) {
            var newX = x + MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(newX);
            else if (levelEvolve.notOverlap(x + 1, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(x + 1);
        }
    }
}
