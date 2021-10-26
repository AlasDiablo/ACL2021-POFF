package fr.poweroff.labyrinthe;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.level.Level;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    public Level                    level;
    public Map<Level.Pair, Integer> entries;

    @Before
    public void setup() {
        this.level   = new Level();
        this.entries = ImmutableMap.of(
                new Level.Pair(0, 0), 1,
                new Level.Pair(1, 0), 1,
                new Level.Pair(2, 0), 1,
                new Level.Pair(0, 1), 1,
                new Level.Pair(1, 1), 0,
                new Level.Pair(2, 1), 1,
                new Level.Pair(0, 2), 1,
                new Level.Pair(1, 2), 1,
                new Level.Pair(2, 2), 1
        );
    }

    @Test
    public void same_level() {
        var levelEntrySet = this.level.getLevelDisposition().entrySet();

        levelEntrySet.forEach(pairIntegerEntry -> {
            var pair  = pairIntegerEntry.getKey();
            var value = pairIntegerEntry.getValue();
            assertEquals("", this.entries.get(pair), value);
        });
    }
}
