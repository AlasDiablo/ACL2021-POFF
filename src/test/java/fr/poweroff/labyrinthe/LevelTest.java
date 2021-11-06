package fr.poweroff.labyrinthe;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.model.Coordinate;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    public Level level;
    public Map<Coordinate, Integer> entries;

    @Before
    public void setup() {
        this.level   = new Level();
        this.entries = ImmutableMap.of(
                new Coordinate(0, 0), 1,
                new Coordinate(1, 0), 1,
                new Coordinate(2, 0), 1,
                new Coordinate(0, 1), 1,
                new Coordinate(1, 1), 0,
                new Coordinate(2, 1), 1,
                new Coordinate(0, 2), 1,
                new Coordinate(1, 2), 1,
                new Coordinate(2, 2), 1
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
