package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

public class Player extends Entity {

    private String direction;

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

    @Override
    public void draw(Graphics2D graphics) {
        switch (direction) {
            case "UP":
                graphics.drawImage(this.getSprite()[3], this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE, null);
                break;
            case "DOWN":
                graphics.drawImage(this.getSprite()[1], this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE, null);
                break;
            case "LEFT":
                graphics.drawImage(this.getSprite()[2], this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE, null);
                break;
            case "RIGHT":
                graphics.drawImage(this.getSprite()[0], this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE, null);
                break;
        }
    }

    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();

        if (cmd == Cmd.UP || cmd == Cmd.LEFT_UP || cmd == Cmd.RIGHT_UP) {
            var newY = y - MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(newY);
            else if (levelEvolve.notOverlap(x, y - 1, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(y - 1);
            this.direction = Cmd.UP.name();
        }

        if (cmd == Cmd.DOWN || cmd == Cmd.LEFT_DOWN || cmd == Cmd.RIGHT_DOWN) {
            var newY = y + MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(newY);
            else if (levelEvolve.notOverlap(x, y + 1, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setY(y + 1);
            this.direction = Cmd.DOWN.name();
        }

        if (cmd == Cmd.LEFT || cmd == Cmd.LEFT_DOWN || cmd == Cmd.LEFT_UP) {
            var newX = x - MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(newX);
            else if (levelEvolve.notOverlap(x - 1, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(x - 1);
            this.direction = Cmd.LEFT.name();
        }

        if (cmd == Cmd.RIGHT || cmd == Cmd.RIGHT_DOWN || cmd == Cmd.RIGHT_UP) {
            var newX = x + MOVE_SPEED;
            if (levelEvolve.notOverlap(newX, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(newX);
            else if (levelEvolve.notOverlap(x + 1, y, ENTITY_SIZE, ENTITY_SIZE)) this.coordinate.setX(x + 1);
            this.direction = Cmd.RIGHT.name();
        }
    }
}
