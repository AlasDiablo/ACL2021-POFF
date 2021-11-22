package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

public class Monster extends Entity {

    private String direction;

    public Monster(Coordinate coordinate) {
        super(
                coordinate,
                FilesUtils.getImage("monster.png")
        );
        this.direction = "DOWN";
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.getSprite()[0], this.getCoordinate().getX(), this.getCoordinate().getY(), 20, 20, null);
        //TODO Orientation des sprite en fondtion de la direction de deplacement
    }

    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        //TODO Depalcement des monstre en ligne
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();

        if (this.direction.equals(Cmd.UP.name())) {
            var newY = y - MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, 20, 20)) {
                this.coordinate.setY(newY);
                this.direction = Cmd.UP.name();
            }else {
                this.direction = Cmd.DOWN.name();
            }
        } else if (this.direction.equals(Cmd.DOWN.name())) {
            var newY = y + MOVE_SPEED;
            if (levelEvolve.notOverlap(x, newY, 20, 20)) {
                this.coordinate.setY(newY);
                this.direction = Cmd.DOWN.name();
            }else {
                this.direction = Cmd.UP.name();
            }

        }

    }
}
