package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.model.Coordinate;

import java.util.Map;

public class Level {
    // Size on a title in pixel
    public static final int TITLE_SIZE = 10;

    private final Map<Coordinate, Integer> levelDisposition;

    public Level() {
        ImmutableMap.Builder<Coordinate, Integer> builder = new ImmutableMap.Builder<>();
        // Create this level
        // Wall  Wall  Wall
        // Wall Ground Wall
        // Wall  Wall  Wall

        // Wall  Wall  Wall
        builder.put(new Coordinate(0, 0), 1);
        builder.put(new Coordinate(1, 0), 1);
        builder.put(new Coordinate(2, 0), 1);

        // Wall Ground Wall
        builder.put(new Coordinate(0, 1), 1);
        builder.put(new Coordinate(1, 1), 0);
        builder.put(new Coordinate(2, 1), 1);

        // Wall  Wall  Wall
        builder.put(new Coordinate(0, 2), 1);
        builder.put(new Coordinate(1, 2), 1);
        builder.put(new Coordinate(2, 2), 1);

        this.levelDisposition = builder.build();
    }

    public Map<Coordinate, Integer> getLevelDisposition() {
        return this.levelDisposition;
    }
}
