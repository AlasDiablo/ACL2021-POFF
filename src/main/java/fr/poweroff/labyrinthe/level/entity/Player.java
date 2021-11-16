package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private static final String SPRITE_PATH = "assets/player/";
    private              int    spriteIndex;

    public Player(Coordinate coordinate) {
        super(
                coordinate,
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

    private static BufferedImage getSprite(String name) {
        return FilesUtils.getImage(SPRITE_PATH + name);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                this.getSprite()[this.spriteIndex],
                this.getCoordinate().getX(),
                this.getCoordinate().getY(),
                ENTITY_SIZE, ENTITY_SIZE, null
        );
    }

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
