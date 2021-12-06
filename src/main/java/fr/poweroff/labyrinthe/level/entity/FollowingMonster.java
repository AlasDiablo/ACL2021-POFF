package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.model.PacmanPainter;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;

public class FollowingMonster extends Monster {
    public FollowingMonster(Coordinate coordinate) {
        super(coordinate, 4);
    }

    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        this.changeDirectionRandomly();

        var top = levelEvolve.overlapWithPlayer(this.coordinate.getX(), Level.TITLE_SIZE, ENTITY_SIZE, this.coordinate.getY() - Level.TITLE_SIZE);
        var left = levelEvolve.overlapWithPlayer(Level.TITLE_SIZE, this.coordinate.getY(), this.coordinate.getX() - Level.TITLE_SIZE, ENTITY_SIZE);
        var right = levelEvolve.overlapWithPlayer(
                this.coordinate.getX(), this.coordinate.getY(), PacmanPainter.WIDTH - this.coordinate.getX() - Level.TITLE_SIZE, ENTITY_SIZE);
        var bottom = levelEvolve.overlapWithPlayer(
                this.coordinate.getX(), this.coordinate.getY() + ENTITY_SIZE, ENTITY_SIZE,
                PacmanPainter.HEIGHT - this.coordinate.getY() - Level.TITLE_SIZE * 2 + 2
        );

        if (top) {
            this.direction = Cmd.UP;
            this.vertical = true;
        }
        if (right) {
            this.direction = Cmd.RIGHT;
            this.vertical = false;
        }
        if (bottom) {
            this.direction = Cmd.DOWN;
            this.vertical = true;
        }
        if (left) {
            this.direction = Cmd.LEFT;
            this.vertical = false;
        }

        this.move(levelEvolve);
    }

    @Override
    public void drawHitBox(Graphics2D graphics) {
        super.drawHitBox(graphics);
        graphics.setColor(Color.GREEN); // TOP
        graphics.drawRect(this.coordinate.getX(), Level.TITLE_SIZE, ENTITY_SIZE, this.coordinate.getY() - Level.TITLE_SIZE);
        graphics.setColor(Color.MAGENTA); // LEFT
        graphics.drawRect(Level.TITLE_SIZE, this.coordinate.getY(), this.coordinate.getX() - Level.TITLE_SIZE, ENTITY_SIZE);
        graphics.setColor(Color.CYAN); // RIGHT
        graphics.drawRect(this.coordinate.getX(), this.coordinate.getY(), PacmanPainter.WIDTH - this.coordinate.getX() - Level.TITLE_SIZE, ENTITY_SIZE);
        graphics.setColor(Color.YELLOW); // DOWN
        graphics.drawRect(
                this.coordinate.getX(), this.coordinate.getY() + ENTITY_SIZE, ENTITY_SIZE,
                PacmanPainter.HEIGHT - this.coordinate.getY() - Level.TITLE_SIZE * 2 + 2
        );
    }
}
