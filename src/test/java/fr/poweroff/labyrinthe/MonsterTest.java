package fr.poweroff.labyrinthe;

import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.entity.Monster;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileGround;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileWall;
import fr.poweroff.labyrinthe.utils.Coordinate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class MonsterTest {
    public Level level;
    public List<Tile> levelDisposition;

    public Entity[] entites;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 550;

    private static final int TILE_SIZE = 11;
    @Ignore
    @Before
    public void setup() {
        TileWall wall = new TileWall(0,0);
        this.level = new Level();
        this.levelDisposition = new ArrayList<Tile>();
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
             /**   if (y == 0 || y == 10 || x == 0 || x == 10) {
                    this.levelDisposition.add(new TileWall(x * TILE_SIZE, y * TILE_SIZE));
                } else {
                    this.levelDisposition.add(new TileGround(x * TILE_SIZE, y * TILE_SIZE));
                }*/
             this.levelDisposition.add(new TileGround(0,0));
            }
        }

        this.entites = new Entity[]{
                new Monster(new Coordinate(0, 0)),
                new Monster(new Coordinate(0, 0)),
                new Monster(new Coordinate(0,0))
        };

        this.level.initMonster(WIDTH, HEIGHT, levelDisposition, entites);
    }
    @Ignore
    @Test
    public void positionOnGroundTest() {
        System.out.println(this.level.getLevelDisposition());
    }
}
