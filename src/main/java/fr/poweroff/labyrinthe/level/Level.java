package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;

public class Level {
    // Size on a title in pixel
    public static final int TITLE_SIZE = 10;

    private final Map<Pair, Integer> levelDisposition;

    public Level() {
        ImmutableMap.Builder<Pair, Integer> builder = new ImmutableMap.Builder<>();
        // Create this level
        // Wall  Wall  Wall
        // Wall Ground Wall
        // Wall  Wall  Wall

        // Wall  Wall  Wall
        builder.put(new Pair(0, 0), 1);
        builder.put(new Pair(1, 0), 1);
        builder.put(new Pair(2, 0), 1);

        // Wall Ground Wall
        builder.put(new Pair(0, 1), 1);
        builder.put(new Pair(1, 1), 0);
        builder.put(new Pair(2, 1), 1);

        // Wall  Wall  Wall
        builder.put(new Pair(0, 2), 1);
        builder.put(new Pair(1, 2), 1);
        builder.put(new Pair(2, 2), 1);

        this.levelDisposition = builder.build();
    }

    public Map<Pair, Integer> getLevelDisposition() {
        return this.levelDisposition;
    }

    public static class Pair { // TODO With for the functionality with coordinated
        public int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return x == pair.x && y == pair.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
