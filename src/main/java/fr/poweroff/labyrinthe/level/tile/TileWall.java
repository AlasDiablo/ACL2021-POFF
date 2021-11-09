package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class TileWall extends Tile {

    public TileWall(int x, int y) {
        super(ImageUtils.getImage("tile_wall.png"), x, y);
    }

    public TileWall(Coordinate coordinate) {
        super(ImageUtils.getImage("tile_wall.png"), coordinate);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprite(), x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Type getType() {
        return Type.WALL;
    }

    @Override
    public Event onEvent(Event event) {
        return event;
    }
}


