package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableList;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.event.PlayerOnBonusTileEvent;
import fr.poweroff.labyrinthe.event.PlayerOnEndTileEvent;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Level {
    // Size on a title in pixel
    public static final int          TITLE_SIZE = 11 * 2;
    private final       LevelEvolve  levelEvolve;
    private             List<Tile>   levelDisposition;
    private             List<Entity> entities;
    private             Tile         endTile;
    private             Entity       player;

    public Level() {
        this.levelDisposition = List.of();
        this.entities         = List.of();
        this.endTile          = null;
        this.levelEvolve      = new LevelEvolve();
        this.player           = null;
    }

    public void init(int width, int height, Entity player, Entity... entities) {
        ImmutableList.Builder<Tile> levelBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder  = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> bonusBuilder = new ImmutableList.Builder<>();
        this.player = player;

        Coordinate tb1 = new Coordinate(10, 10);
        Coordinate tb2 = new Coordinate(20, 15);
        Coordinate tb3 = new Coordinate(10, 20);

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
                if ((x == tb1.getX() && y == tb1.getY()) || (x == tb2.getX() && y == tb2.getY()) || (x == tb3.getX() && y == tb3.getY())) {
                    currentTile = new TileBonus(rx, ry);
                    bonusBuilder.add(currentTile);
                } else if ((x == 0 || x == sizeX - 1) || (y == 0 || y == sizeY - 1)) {
                    currentTile = new TileWall(rx, ry);
                    wallBuilder.add(currentTile);
                } else {
                    if (beforeStart == startPos) {
                        currentTile = new TileStart(rx, ry);
                        player.getCoordinate().setX(rx);
                        player.getCoordinate().setY(ry);
                    } else if (beforeEnd == endPos) {
                        currentTile  = new TileEnd(rx, ry);
                        this.endTile = currentTile;
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
        this.levelDisposition       = levelBuilder.build();
        this.levelEvolve.wallTiles  = wallBuilder.build();
        this.levelEvolve.bonusTiles = bonusBuilder.build();
    }

    public void draw(Graphics2D graphics) {
        this.levelDisposition.forEach(tile -> tile.draw(graphics));
        this.entities.forEach(entity -> entity.draw(graphics));
    }

    public void evolve(Cmd cmd) {
        this.entities.forEach(entity -> entity.evolve(cmd, this.levelEvolve));
        if (this.levelEvolve.overlap(
                this.player.getCoordinate().getX(),
                this.player.getCoordinate().getY(),
                20, 20, List.of(this.endTile)
        )
        ) {
            PacmanGame.onEvent(new PlayerOnEndTileEvent(this.endTile));
        }

        this.levelEvolve.overlapFindAny(
                this.player.getCoordinate().getX(),
                this.player.getCoordinate().getY(),
                20, 20, this.levelEvolve.bonusTiles
        ).ifPresent(tile -> {
            if (tile.getType() == Tile.Type.BONUS) {PacmanGame.onEvent(new PlayerOnBonusTileEvent(tile));}
        });
    }

    public List<Tile> getLevelDisposition() {
        return this.levelDisposition;
    }

    public static class LevelEvolve {
        private List<Tile> wallTiles;
        private List<Tile> bonusTiles;

        public boolean overlap(int x, int y, int w, int h) {
            return overlap(x, y, w, h, this.wallTiles);
        }

        public boolean overlap(int x, int y, int w, int h, List<Tile> tiles) {
            return tiles
                    .stream()
                    .anyMatch(tile -> !(x + w < tile.getCoordinate().getX() ||
                            tile.getCoordinate().getX() + TITLE_SIZE < x ||
                            y + h < tile.getCoordinate().getY() ||
                            tile.getCoordinate().getY() + TITLE_SIZE < y)
                    );
        }

        public Optional<Tile> overlapFindAny(int x, int y, int w, int h, List<Tile> tiles) {
            return tiles
                    .stream()
                    .filter(tile -> !(x + w < tile.getCoordinate().getX() ||
                            tile.getCoordinate().getX() + TITLE_SIZE < x ||
                            y + h < tile.getCoordinate().getY() ||
                            tile.getCoordinate().getY() + TITLE_SIZE < y)
                    )
                    .findAny();
        }
    }
}
