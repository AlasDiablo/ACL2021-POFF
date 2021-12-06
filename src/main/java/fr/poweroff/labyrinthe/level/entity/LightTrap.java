package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.tile.special.TileLightTrap;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.Segment;

import java.awt.*;
import java.util.ArrayList;

public class LightTrap extends Entity {
    int w;
    int h;
    private final ArrayList<Segment> rayLight;
    private final int direction;

    public LightTrap(Coordinate coordinate, int direction) {
        super(new Coordinate(coordinate));
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        if (direction == TileLightTrap.UP) {
            x += 7;
            y += 7;
        } else if (direction == TileLightTrap.DOWN) {
            x += 7;
            y += 14;
        } else if (direction == TileLightTrap.LEFT) {
            x += 7;
            y += 7;
        } else if (direction == TileLightTrap.RIGHT) {
            x += 14;
            y += 7;
        }
        this.direction = direction;
        this.coordinate.setX(x);
        this.coordinate.setY(y);
        rayLight = null;
        this.w = 0;
        this.h = 0;
    }

    public void evolve(Cmd cmd, Level.LevelEvolve levelEvolve) {

    }

    @Override
    public Cmd getDirection() {
        return null;
    }

    public void draw(Graphics2D graph) {
        graph.fillRect(this.coordinate.getX(), this.coordinate.getY(), this.w, this.h);
        graph.setColor(Color.YELLOW);
        graph.drawRect(this.coordinate.getX(), this.coordinate.getY(), this.w - 1, this.h - 1);
        graph.setColor(Color.CYAN);
        graph.drawRect(this.coordinate.getX() + 3, this.coordinate.getY() + 3, this.w - 6, this.h - 6);
        graph.setColor(Color.WHITE);
    }

    public void set_rayLight(Level.LevelEvolve levelEvolve) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (direction == TileLightTrap.UP) {
            y -= 7;
            this.w += 7;
            h += 7;
        } else if (direction == TileLightTrap.DOWN) {
            this.w += 7;
            this.h += 7;
        } else if (direction == TileLightTrap.LEFT) {
            x -= 7;
            this.w += 7;
            this.h += 7;
        } else if (direction == TileLightTrap.RIGHT) {
            this.w += 7;
            this.h += 7;
        }
        coordinate.setX(x);
        coordinate.setY(y);
        int tmpW = this.w;
        int tmpH = this.h;
        while (levelEvolve.notOverlap(x, y, w, h)) {
            coordinate.setX(x);
            coordinate.setY(y);
            this.w = tmpW - 1;
            this.h = tmpH - 1;
            if (direction == TileLightTrap.UP) {
                y -= 22;
                tmpH += 22;
            } else if (direction == TileLightTrap.DOWN) {
                tmpH += 22;

            } else if (direction == TileLightTrap.LEFT) {
                x -= 22;
                tmpW += 22;
            } else if (direction == TileLightTrap.RIGHT) {
                tmpW += 22;
            }
        }
        if (direction == TileLightTrap.LEFT) {
            this.coordinate.setX(this.coordinate.getX() - 21);
            this.w += 21;
        } else if (direction == TileLightTrap.RIGHT)
            this.w -= 21;
        else if (direction == TileLightTrap.UP) {
            this.coordinate.setY(this.coordinate.getY() - 21);
            this.h += 21;
        } else if (direction == TileLightTrap.DOWN)
            this.h -= 21;

    }


}
