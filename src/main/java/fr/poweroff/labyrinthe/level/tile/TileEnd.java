package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class TileEnd extends Tile {

    public TileEnd(int x, int y) {
        super(x, y, ImageUtils.getImage("tile_end.png"));
    }

    public TileEnd(Coordinate coordinate) {
        super(coordinate, ImageUtils.getImage("tile_end.png"));
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = super.getCoordinate().getX();
        int y = super.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[0], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Type getType() {
        return Type.END;
    }
}


