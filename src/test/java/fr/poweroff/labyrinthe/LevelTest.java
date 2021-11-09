package fr.poweroff.labyrinthe;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileGround;
import fr.poweroff.labyrinthe.level.tile.TileWall;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    public Level level;
    public Map<Coordinate, Tile> entries;

    @Before
    public void setup() {
        ImageUtils.setClassLoader(this.getClass().getClassLoader());
        this.level   = new Level(33, 33);
        this.entries = ImmutableMap.of(
                new Coordinate(0, 0), new TileWall(0, 0),
                new Coordinate(1, 0), new TileWall(11, 0),
                new Coordinate(2, 0), new TileWall(22, 0),
                new Coordinate(0, 1), new TileWall(0, 11),
                new Coordinate(1, 1), new TileGround(11, 11),
                new Coordinate(2, 1), new TileWall(22, 11),
                new Coordinate(0, 2), new TileWall(0, 22),
                new Coordinate(1, 2), new TileWall(11, 22),
                new Coordinate(2, 2), new TileWall(22, 22)
        );
    }

    @Test
    public void same_level() {
        var levelEntrySet = this.level.getLevelDisposition().entrySet();

        levelEntrySet.forEach(coordinateIntegerEntry -> {
            var coordinate = coordinateIntegerEntry.getKey();
            var value      = coordinateIntegerEntry.getValue();
            assertEquals("", this.entries.get(coordinate), value);
        });
    }
}
