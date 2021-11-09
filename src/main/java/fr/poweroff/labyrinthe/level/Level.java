package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableList;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    // Size on a title in pixel
    public static final int          TITLE_SIZE = 11 * 2;
    private final       LevelEvolve  levelEvolve;
    private             List<Tile>   levelDisposition;
    private             List<Entity> entities;
    private             Coordinate   endCoordinate;

    public Level() {
        this.levelDisposition = List.of();
        this.entities         = List.of();
        this.endCoordinate    = null;
        this.levelEvolve      = new LevelEvolve();
    }

    public void init(int width, int height, Entity player, Entity... entities) {
        ImmutableList.Builder<Tile> levelBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder  = new ImmutableList.Builder<>();

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
                    wallBuilder.add(currentTile);
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
                levelBuilder.add(currentTile);
            }
        }
        this.entities = new ArrayList<>(List.of(entities));
        this.entities.add(player);
        this.levelDisposition  = levelBuilder.build();
        this.levelEvolve.tiles = wallBuilder.build();
    }

    public void draw(Graphics2D graphics) {
        this.levelDisposition.forEach(tile -> tile.draw(graphics));
        this.entities.forEach(entity -> entity.draw(graphics));
    }

    public void evolve(Cmd cmd) {
        this.entities.forEach(entity -> entity.evolve(cmd, this.levelEvolve));

    }

    public List<Tile> getLevelDisposition() {
        return this.levelDisposition;
    }

    public static class LevelEvolve {
        private List<Tile> tiles;

        public boolean canGoHere(int x, int y, int w, int h) {
            return tiles
                    .stream()
                    .filter(tile -> !(x + w < tile.getCoordinate().getX() ||
                            tile.getCoordinate().getX() + TITLE_SIZE < x ||
                            y + h < tile.getCoordinate().getY() ||
                            tile.getCoordinate().getY() + TITLE_SIZE < y)
                    )
                    .findAny()
                    .isEmpty();
        }
    }
}
