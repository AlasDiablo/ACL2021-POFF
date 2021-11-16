package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

/**
 * End tile
 */
public class TileEnd extends Tile {

    /**
     * Constructor how take coordinate
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public TileEnd(int x, int y) {
        super(x, y, FilesUtils.getImage("tile_end.png"));
    }

    /**
     * Constructor how take coordinate
     *
     * @param coordinate Coordinate object
     */
    public TileEnd(Coordinate coordinate) {
        super(coordinate, FilesUtils.getImage("tile_end.png"));
    }

    /**
     * Function how draw the tile
     *
     * @param graphics2D drawing object
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        int x = super.getCoordinate().getX();
        int y = super.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[0], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    /**
     * Function used to return the type of the tile
     *
     * @return Type on the tile
     */
    @Override
    public Type getType() {
        return Type.END;
    }
}


