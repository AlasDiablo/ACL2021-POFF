package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

/**
 * Class how define the player entity
 */
public class Player extends Entity {

    /**
     * The last direction of the player
     */
    private String direction;

    /**
     * Default constructor of the player
     *
     * @param coordinate the start position of the player
     */
    public Player(Coordinate coordinate) {
        super(
                coordinate,
                FilesUtils.getImage("pacman_right.png"),
                FilesUtils.getImage("pacman_down.png"),
                FilesUtils.getImage("pacman_left.png"),
                FilesUtils.getImage("pacman_up.png")
        );
        this.direction = "RIGHT";
    }

    /**
     * Fonction used to draw the player
     *
     * @param graphics Drawing object
     */
    @Override
    public void draw(Graphics2D graphics) {
        switch (direction) {
            case "UP":
                graphics.drawImage(this.getSprite()[3], this.getCoordinate().getX(), this.getCoordinate().getY(), 20, 20, null);
                break;
            case "DOWN":
                graphics.drawImage(this.getSprite()[1], this.getCoordinate().getX(), this.getCoordinate().getY(), 20, 20, null);
                break;
            case "LEFT":
                graphics.drawImage(this.getSprite()[2], this.getCoordinate().getX(), this.getCoordinate().getY(), 20, 20, null);
                break;
            case "RIGHT":
                graphics.drawImage(this.getSprite()[0], this.getCoordinate().getX(), this.getCoordinate().getY(), 20, 20, null);
                break;
        }
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

        if (cmd == Cmd.UP || cmd == Cmd.LEFT_UP || cmd == Cmd.RIGHT_UP) {
            var newY = y - MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, 20, 20)) this.coordinate.setY(newY);
            this.direction = Cmd.UP.name();
        }

        if (cmd == Cmd.DOWN || cmd == Cmd.LEFT_DOWN || cmd == Cmd.RIGHT_DOWN) {
            var newY = y + MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, 20, 20)) this.coordinate.setY(newY);
            this.direction = Cmd.DOWN.name();
        }

        if (cmd == Cmd.LEFT || cmd == Cmd.LEFT_DOWN || cmd == Cmd.LEFT_UP) {
            var newX = x - MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, 20, 20)) this.coordinate.setX(newX);
            this.direction = Cmd.LEFT.name();
        }

        if (cmd == Cmd.RIGHT || cmd == Cmd.RIGHT_DOWN || cmd == Cmd.RIGHT_UP) {
            var newX = x + MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, 20, 20)) this.coordinate.setX(newX);
            this.direction = Cmd.RIGHT.name();
        }
    }
}
