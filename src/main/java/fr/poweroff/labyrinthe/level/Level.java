package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.event.PlayerOnBonusTileEvent;
import fr.poweroff.labyrinthe.event.PlayerOnEndTileEvent;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.entity.Monster;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Level {
    // Size on a title in pixel
    public static final int          TITLE_SIZE = 11 * 2;
    public static final int          BONUS_NUMBER = 3;
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

        ArrayList<Monster> monsters = new ArrayList<>() {{
            add((Monster) entities[0]);
            add((Monster) entities[1]);
            add((Monster) entities[2]);
        }};

        var sizeX = (int) Math.floor((float) width / (float) TITLE_SIZE);
        var sizeY = (int) Math.floor((float) height / (float) TITLE_SIZE);

        var perimeter = sizeX * 2 + sizeY * 2;
        var surface   = sizeX * sizeY;

        var startPos = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        var endPos   = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());

        while (startPos == endPos) {
            endPos = (int) Math.floor((surface - perimeter) * Math.random());
        }

        int[] bonusTile = new int[BONUS_NUMBER];
        for (int i = 0; i < BONUS_NUMBER;  i++) {
           bonusTile[i] = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        }
        int[] monstersPositon = new int[monsters.size()];
        for (int i = 0; i < monsters.size();  i++) {
           monstersPositon[i] = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        }

        var beforeStart = 0;
        var beforeEnd   = 0;
        int beforeBonus = 0;
        int beforeMonster = 0;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Tile currentTile;
                var  rx = x * TITLE_SIZE;
                var  ry = y * TITLE_SIZE;
                int finalBeforeBonus1 = beforeBonus;
                if (IntStream.of(bonusTile).anyMatch(i -> i == finalBeforeBonus1)) {
                    currentTile = new TileBonus(rx, ry);
                    bonusBuilder.add(currentTile);
                    beforeBonus++;
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
                    }else {
                        currentTile = new TileGround(rx, ry);
                        int finalBeforeMonster = beforeMonster;
                        if (IntStream.of(monstersPositon).anyMatch(i -> i == finalBeforeMonster)) {
                            System.out.println("MONSTER");
                            int index = Ints.indexOf(monstersPositon, finalBeforeMonster);
                            monsters.get(index).getCoordinate().setX(rx);
                            monsters.get(index).getCoordinate().setY(ry);
                        }
                    }
                    beforeStart++;
                    beforeEnd++;
                    beforeBonus++;
                    beforeMonster++;
                }
                levelBuilder.add(currentTile);
            }
        }
        this.entities = new ArrayList<>(List.of(entities));
        this.entities.add(player);
        this.entities.addAll(Arrays.asList(entities));

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
