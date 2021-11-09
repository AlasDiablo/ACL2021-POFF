package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class TileEnd extends Tile {

    public TileEnd(int x, int y) {
        super(ImageUtils.getImage("tile_end.png"), x, y);
    }

    public TileEnd(Coordinate coordinate) {
        super(ImageUtils.getImage("tile_end.png"), coordinate);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = super.getCoordinate().getX();
        int y = super.getCoordinate().getY();
        graphics2D.drawImage(this.getSprite(), x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Event onTileEvent(Event event) {
        return event;
    }

    @Override
    public Type getType() {
        return Type.END;
    }
}


