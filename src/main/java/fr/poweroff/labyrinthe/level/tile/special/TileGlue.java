package fr.poweroff.labyrinthe.level.tile.special;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

public class TileGlue extends Tile {


    public TileGlue(int x, int y) {
        super(x, y, FilesUtils.getImage("assets/textures/tile/TileGlue.png"));
    }

    public TileGlue(Coordinate coordinate) {
        super(coordinate, FilesUtils.getImage("assets/textures/tile/TileGlue.png"));
    }

    /**
     * Function how draw the tile
     *
     * @param graphics2D drawing object
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[0], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    /**
     * Function used to return the type of the tile
     *
     * @return Type on the tile
     */
    @Override
    public Type getType() {
        return Type.GLUE;
    }


}
