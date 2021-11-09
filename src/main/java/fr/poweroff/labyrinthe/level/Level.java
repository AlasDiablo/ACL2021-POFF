package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level {
    // Size on a title in pixel
    public static final int TITLE_SIZE = 11 * 2;

    private Map<Coordinate, Tile> levelDisposition;

    private List<Entity> entities;

    private Coordinate endCoordinate;

    public Level() {
        this.levelDisposition = new ImmutableMap.Builder<Coordinate, Tile>().build();
        this.entities         = List.of();
        this.endCoordinate    = null;
    }

    public void init(int width, int height, Entity player, Entity... entities) {
        ImmutableMap.Builder<Coordinate, Tile> builder = new ImmutableMap.Builder<>();

        var sizeX = (int) Math.floor((float) width / (float) TITLE_SIZE);
        var sizeY = (int) Math.floor((float) height / (float) TITLE_SIZE);

        var perimeter = sizeX * 2 + sizeY * 2;
        var surface   = sizeX * sizeY;

        var startPos = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        var endPos   = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());

        while (startPos == endPos) {
            endPos = (int) Math.floor((surface - perimeter) * Math.random());
        }

        var beforeStart = 0;
        var beforeEnd   = 0;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Tile currentTile;
                var  rx = x * TITLE_SIZE;
                var  ry = y * TITLE_SIZE;
                if ((x == 0 || x == sizeX - 1) || (y == 0 || y == sizeY - 1)) {
                    currentTile = new TileWall(rx, ry);
                } else {
                    if (beforeStart == startPos) {
                        currentTile = new TileStart(rx, ry);
                        player.getCoordinate().setX(rx);
                        player.getCoordinate().setY(ry);
                    } else if (beforeEnd == endPos) {
                        currentTile        = new TileEnd(rx, ry);
                        this.endCoordinate = new Coordinate(rx, ry);
                    } else {
                        currentTile = new TileGround(rx, ry);
                    }
                    beforeStart++;
                    beforeEnd++;
                }
                builder.put(new Coordinate(x, y), currentTile);
            }
        }
        this.entities = new ArrayList<>(List.of(entities));
        this.entities.add(player);
        this.levelDisposition = builder.build();
    }

    public void draw(Graphics2D graphics) {
        this.levelDisposition.values().forEach(tile -> tile.draw(graphics));
        this.entities.forEach(entity -> entity.draw(graphics));
    }

    public void evolve(Cmd cmd) {
        this.entities.forEach(entity -> entity.evolve(cmd));

    }

    public Map<Coordinate, Tile> getLevelDisposition() {
        return this.levelDisposition;
    }
}
