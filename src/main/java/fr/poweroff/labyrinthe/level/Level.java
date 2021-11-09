package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileBonus;
import fr.poweroff.labyrinthe.level.tile.TileGround;
import fr.poweroff.labyrinthe.level.tile.TileWall;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Level {
    // Size on a title in pixel
    public static final int TITLE_SIZE = 11 * 2;

    private final Map<Coordinate, Tile> levelDisposition;
    private ArrayList bonusList;

    public Level(int width, int height) {
        ImmutableMap.Builder<Coordinate, Tile> builder = new ImmutableMap.Builder<>();


        Coordinate tb1  = new Coordinate(10, 10);
        Coordinate tb2  = new Coordinate(20, 15);
        Coordinate tb3  = new Coordinate(10, 20);

        var sizeX = (int) Math.floor((float) width / (float) TITLE_SIZE);
        var sizeY = (int) Math.floor((float) height / (float) TITLE_SIZE);

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Tile currentTile;
                var rx = x * TITLE_SIZE;
                var ry = y * TITLE_SIZE;

                if((x == tb1.getX() && y == tb1.getY()) || (x == tb2.getX() && y == tb2.getY()) || (x == tb3.getX() && y == tb3.getY())) {
                    currentTile = new TileBonus(rx, ry);
                }else if ((x == 0 || x == sizeX - 1) || (y == 0 || y == sizeY - 1)) {
                    currentTile = new TileWall(rx, ry);
                } else {
                    currentTile = new TileGround(rx, ry);
                }
                builder.put(new Coordinate(x, y), currentTile);
            }
        }
        this.levelDisposition = builder.build();
    }

    public void draw(Graphics2D graphics) {
        this.levelDisposition.values().forEach(tile -> tile.draw(graphics));
    }

    public Map<Coordinate, Tile> getLevelDisposition() {
        return this.levelDisposition;
    }
}
