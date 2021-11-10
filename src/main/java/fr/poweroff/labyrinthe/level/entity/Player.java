package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class Player extends Entity {

    private String direction;

    public Player(Coordinate coordinate) {
        super(
                coordinate,
                ImageUtils.getImage("pacman_right.png"),
                ImageUtils.getImage("pacman_down.png"),
                ImageUtils.getImage("pacman_left.png"),
                ImageUtils.getImage("pacman_up.png")
        );
        this.direction = "RIGHT";
    }

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

    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();

        if (cmd == Cmd.UP || cmd == Cmd.LEFT_UP || cmd == Cmd.RIGHT_UP) {
            var newY = y - MOVE_SPEED;
            if (!levelEvolve.overlap(x, newY, 20, 20)) this.coordinate.setY(newY);
            this.direction = Cmd.UP.name();
        }

        if (cmd == Cmd.DOWN || cmd == Cmd.LEFT_DOWN || cmd == Cmd.RIGHT_DOWN) {
            var newY = y + MOVE_SPEED;
            if (!levelEvolve.overlap(x, newY, 20, 20)) this.coordinate.setY(newY);
            this.direction = Cmd.DOWN.name();
        }

        if (cmd == Cmd.LEFT || cmd == Cmd.LEFT_DOWN || cmd == Cmd.LEFT_UP) {
            var newX = x - MOVE_SPEED;
            if (!levelEvolve.overlap(newX, y, 20, 20)) this.coordinate.setX(newX);
            this.direction = Cmd.LEFT.name();
        }

        if (cmd == Cmd.RIGHT || cmd == Cmd.RIGHT_DOWN || cmd == Cmd.RIGHT_UP) {
            var newX = x + MOVE_SPEED;
            if (!levelEvolve.overlap(newX, y, 20, 20)) this.coordinate.setX(newX);
            this.direction = Cmd.RIGHT.name();
        }
    }
}
