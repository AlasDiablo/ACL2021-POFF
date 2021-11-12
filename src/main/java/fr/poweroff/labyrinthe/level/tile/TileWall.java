package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

public class TileWall extends Tile {

    public TileWall(int x, int y) {
        super(x, y, FilesUtils.getImage("tile_wall.png"));
    }

    public TileWall(Coordinate coordinate) {
        super(coordinate, FilesUtils.getImage("tile_wall.png"));
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[0], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Type getType() {
        return Type.WALL;
    }
}


