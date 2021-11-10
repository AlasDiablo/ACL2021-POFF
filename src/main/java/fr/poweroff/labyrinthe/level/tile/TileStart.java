package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class TileStart extends Tile {

    public TileStart(int x, int y) {
        super(x, y, ImageUtils.getImage("tile_start.png"));
    }

    public TileStart(Coordinate coordinate) {
        super(coordinate, ImageUtils.getImage("tile_start.png"));
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[0], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Type getType() {
        return Type.START;
    }
}


