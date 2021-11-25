package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.event.PlayerOnBonusTileEvent;
import fr.poweroff.labyrinthe.event.PlayerOnEndTileEvent;
import fr.poweroff.labyrinthe.event.cases.PlayerOnLifeBonusTileEvent;
import fr.poweroff.labyrinthe.event.cases.PlayerOnMunitionBonusTileEvent;
import fr.poweroff.labyrinthe.event.cases.PlayerOnTimeBonusTileEvent;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.level.tile.special.TileLife;
import fr.poweroff.labyrinthe.level.tile.special.TileMunitions;
import fr.poweroff.labyrinthe.level.tile.special.TileTime;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class use tu create a level and handle each game tick
 */
public class Level {
    /**
     * Size of a title in pixel
     */
    public static final int          TITLE_SIZE   = 11 * 2;
    /**
     * Number of bonus in a generated level
     */
    public static final int          BONUS_NUMBER = 3;
    /**
     * Minimal number of inner wall in a generated level
     */
    public static final int          WALL_NUMBER  = 6;

    /**
     * Nombre de timer sur la map
     */
    public static final int          TIMER_NUMBER = 1;

    public static final int          MUNITION_NUMBER = 6;

    /**
     * Level evolve used to check tile and entities overlapping and more
     */
    private final       LevelEvolve  levelEvolve;
    /**
     * List of all level tiles
     */
    private             List<Tile>   levelDisposition;
    /**
     * List of all level entities
     */
    private             List<Entity> entities;
    /**
     * The end tile of the level
     */
    private             Tile         endTile;
    /**
     * The player instances
     */
    private             Entity       player;

    public Level() {
        this.levelEvolve = new LevelEvolve();
        this.init();
    }

    /**
     * Function use to init and re-init the level
     */
    private void init() {
        this.levelDisposition = List.of();
        this.entities         = List.of();
        this.endTile          = null;
        this.player           = null;
    }

    /**
     * Initialize the level from a level file
     *
     * @param levelFile file containing the level
     * @param player    player instances
     * @param entities  the list of all entities in the game (excluded from player)
     */
    public void init(String levelFile, Entity player, Entity... entities) {
        // Variable containing the tile liste
        ImmutableMap.Builder<Coordinate, Tile.Type> levelMap = new ImmutableMap.Builder<>();
        // Json object containing an array who represent the level
        var json = FilesUtils.getJson(levelFile);
        // Json array containing the level
        var level = json.getAsJsonArray();
        // Current position of the current tile
        var x = new AtomicInteger();
        var y = new AtomicInteger();
        // Create the tile list (read line)
        level.forEach(lineElement -> {
            var line = lineElement.getAsJsonArray();
            // read column
            line.forEach(tile -> {
                var tileName = tile.getAsString();
                levelMap.put(new Coordinate(x.getAndIncrement(), y.get()), Tile.Type.valueOf(tileName));
            });
            y.getAndIncrement();
            x.set(0);
        });
        // Call an other init function to finish the work
        this.init(levelMap.build(), player, entities);
    }

    /**
     * Initialize the level from a map of coordinate and tile
     *
     * @param level    map containing the coordinate and tile
     * @param player   player instances
     * @param entities list of all entities in the game (excluded from player)
     */
    public void init(Map<Coordinate, Tile.Type> level, Entity player, Entity... entities) {
        // Initialize or re-initialize variable
        this.init();

        // Create level tile list
        ImmutableList.Builder<Tile> levelBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder  = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> bonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> specialBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> munitionBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> timeBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> trapBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> treasureBonusBuilder = new ImmutableList.Builder<>();

        // Read the level map and create tile
        level.forEach((coordinate, type) -> {
            // Current working tile
            Tile currentTile;
            // Position of the current tile
            var rx = coordinate.getX() * TITLE_SIZE;
            var ry = coordinate.getY() * TITLE_SIZE;
            switch (type) {
                // Create wall tile
                case WALL:
                    currentTile = new TileWall(rx, ry);
                    wallBuilder.add(currentTile);
                    break;
                // Create bonus tile
                case BONUS:
                    currentTile = new TileBonus(rx, ry);
                    bonusBuilder.add(currentTile);
                    break;
                // Create end tile
                case END:
                    currentTile = new TileEnd(rx, ry);
                    this.endTile = currentTile;
                    break;
                // Create start tile and place the player onto
                case START:
                    currentTile = new TileStart(rx, ry);
                    player.getCoordinate().setX(rx + 2);
                    player.getCoordinate().setY(ry + 2);
                    break;
                    //Create tile for add life
                case ADDLIFE:
                    currentTile = new TileLife(rx , ry);
                    specialBonusBuilder.add(currentTile);
                    break;
                    //Create tile for add time
                case ADDTIME:
                    currentTile = new TileTime(rx, ry);
                    timeBonusBuilder.add(currentTile);
                    break;
                    //Creat tile for munitions
                case ADDMUNITION:
                    currentTile = new TileMunitions(rx, ry);
                    munitionBonusBuilder.add(currentTile);
                // Create ground tile
                default:
                    currentTile = new TileGround(rx, ry);
                    break;
            }
            levelBuilder.add(currentTile);
        });

        // Store all local variable into the level
        this.player   = player;
        this.entities = new ArrayList<>(List.of(entities));
        this.entities.add(player);
        this.levelDisposition       = levelBuilder.build();
        this.levelEvolve.wallTiles  = wallBuilder.build();
        this.levelEvolve.bonusTiles = bonusBuilder.build();
        this.levelEvolve.specialBonusTiles = specialBonusBuilder.build();
        this.levelEvolve.timeBonusTiles = timeBonusBuilder.build();
        this.levelEvolve.munitionBonusTiles = munitionBonusBuilder.build();
    }

    /**
     * Initialize the level from the window size
     *
     * @param width     window width
     * @param height    window height
     * @param difficult current game difficulty
     * @param player    player instances
     * @param entities  list of all entities in the game (excluded from player)
     */
    public void init(int width, int height, int difficult, Entity player, Entity... entities) {
        // Initialize or re-initialize variable
        this.init();

        // Create level tile list
        ImmutableList.Builder<Tile> levelBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder  = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> bonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> specialBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> munitionBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> timeBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> trapBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> treasureBonusBuilder = new ImmutableList.Builder<>();

        // Calculate the level size
        var sizeX = (int) Math.floor((float) width / (float) TITLE_SIZE);
        var sizeY = (int) Math.floor((float) height / (float) TITLE_SIZE);

        // Calculate the level perimeter and surface
        var perimeter = sizeX * 2 + sizeY * 2;
        var surface   = sizeX * sizeY;

        // Create a random index for the end and start tile
        var startPos = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        var endPos   = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());

        // Recreate the end tile index if it is overlapping the start tile
        while (startPos == endPos) {
            endPos = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
        }

        // Create the list of bonus tile index
        List<Integer> bonusTile = new ArrayList<>();
        for (int i = 0; i < BONUS_NUMBER; i++) {
            int bonusIndex;
            do {
                bonusIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (bonusTile.contains(bonusIndex) || bonusIndex == startPos || bonusIndex == endPos);
            bonusTile.add(bonusIndex);
        }

        // Create the list of special bonus tile index (life)
        List<Integer> specialBonusTile = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int bonusIndex;
            do {
                bonusIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (specialBonusTile.contains(bonusIndex) || bonusIndex == startPos || bonusIndex == endPos);
            specialBonusTile.add(bonusIndex);
        }

        // Create the list of special bonus tile index (time)
        List<Integer> timeBonusTile = new ArrayList<>();
        for (int i = 0; i < TIMER_NUMBER; i++) {
            int bonusIndex;
            do {
                bonusIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (timeBonusTile.contains(bonusIndex) ||bonusIndex == startPos || bonusIndex == endPos);
            timeBonusTile.add(bonusIndex);
        }

        // Create the list of special bonus tile index (munition)
        List<Integer> munitionBonusTile = new ArrayList<>();
        for (int i = 0; i < MUNITION_NUMBER; i++) {
            int bonusIndex;
            do {
                bonusIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (munitionBonusTile.contains(bonusIndex) ||bonusIndex == startPos || bonusIndex == endPos);
            munitionBonusTile.add(bonusIndex);
        }

        var wallNumberWithDifficult = WALL_NUMBER + (WALL_NUMBER / 2 * (difficult - 1));

        // Create a random amount of wall
        var wallNumber = PacmanGame.RANDOM.ints(
                1,
                wallNumberWithDifficult,
                Math.max(wallNumberWithDifficult, (int) Math.floor(surface / 16f))
        ).findAny().orElse(wallNumberWithDifficult);

        // Create the list of wall tile index
        List<Integer> wallTile = new ArrayList<>();
        for (int i = 0; i < wallNumber; i++) {
            int wallIndex;
            do {
                wallIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (wallTile.contains(wallIndex) || bonusTile.contains(wallIndex) || specialBonusTile.contains(wallIndex)
                    || timeBonusTile.contains(wallIndex) || munitionBonusTile.contains(wallIndex) || wallIndex == startPos || wallIndex == endPos);
            wallTile.add(wallIndex);
        }

        // Create all tile of the level
        var levelIndex = 0;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                // Current working tile
                Tile currentTile;
                // Position of the current tile
                var rx = x * TITLE_SIZE;
                var ry = y * TITLE_SIZE;
                // Create the side wall
                if ((x == 0 || x == sizeX - 1) || (y == 0 || y == sizeY - 1)) {
                    currentTile = new TileWall(rx, ry);
                    wallBuilder.add(currentTile);
                    // Add the other tile into the level
                } else {
                    // Create bonus tile
                    if (bonusTile.contains(levelIndex)) {
                        currentTile = new TileBonus(rx, ry);
                        bonusBuilder.add(currentTile);
                        // Create inner wall tile
                    } else if (wallTile.contains(levelIndex)) {
                        currentTile = new TileWall(rx, ry);
                        wallBuilder.add(currentTile);
                        // Create start tile and place player onto
                    } else if (levelIndex == startPos) {
                        currentTile = new TileStart(rx, ry);
                        player.getCoordinate().setX(rx + 2);
                        player.getCoordinate().setY(ry + 2);
                        // Create end tile
                    } else if (levelIndex == endPos) {
                        currentTile  = new TileEnd(rx, ry);
                        this.endTile = currentTile;
                        // Create ground tile
                    } else if(specialBonusTile.contains(levelIndex)){
                        currentTile = new TileLife(rx , ry);
                        specialBonusBuilder.add(currentTile);
                    } else if(timeBonusTile.contains(levelIndex)){
                        currentTile = new TileTime(rx, ry);
                        timeBonusBuilder.add(currentTile);
                    } else if(munitionBonusTile.contains(levelIndex)){
                        currentTile = new TileMunitions(rx, ry);
                        munitionBonusBuilder.add(currentTile);
                    }
                    else {
                        currentTile = new TileGround(rx, ry);
                    }
                    levelIndex++;
                }
                levelBuilder.add(currentTile);
            }
        }
        // Store all local variable into the level
        this.player   = player;
        this.entities = new ArrayList<>(List.of(entities));
        this.entities.add(player);

        this.levelDisposition       = levelBuilder.build();
        this.levelEvolve.wallTiles  = wallBuilder.build();
        this.levelEvolve.bonusTiles = bonusBuilder.build();
        this.levelEvolve.specialBonusTiles = specialBonusBuilder.build();
        this.levelEvolve.timeBonusTiles = timeBonusBuilder.build();
        this.levelEvolve.munitionBonusTiles = munitionBonusBuilder.build();

        this.initMonster(width, height, levelDisposition, entities);
    }

    public void initMonster(int width, int height, List<Tile> levelDisposition, Entity... entities) {
        List<Entity> entitiesList = List.of(entities);

        var sizeX     = (int) Math.floor((float) width / (float) TITLE_SIZE);
        var sizeY     = (int) Math.floor((float) height / (float) TITLE_SIZE);
        var perimeter = sizeX * 2 + sizeY * 2;
        var surface   = sizeX * sizeY;

        int           randomIndex;
        List<Integer> randomIndexList = new ArrayList<>();

        for (int i = 0; i < entitiesList.size(); i++) {
            Tile tile = levelDisposition.get(0);

            while (tile.getType() != Tile.Type.GROUND) {
                randomIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
                tile        = levelDisposition.get(randomIndex);
                if (tile.getType() == Tile.Type.GROUND) {
                    randomIndexList.add(randomIndex);
                }
            }
        }

        for (int i = 0; i < entitiesList.size(); i++) {
            Tile tile = levelDisposition.get(randomIndexList.get(i));
            entitiesList.get(i).getCoordinate().setX(tile.getCoordinate().getX() + 2);
            entitiesList.get(i).getCoordinate().setY(tile.getCoordinate().getY() + 2);
        }
    }

    /**
     * Draw the level
     *
     * @param graphics drawing object
     */
    public void draw(Graphics2D graphics) {
        // Draw all tiles
        this.levelDisposition.forEach(tile -> tile.draw(graphics));
        // Draw all entities
        this.entities.forEach(entity -> entity.draw(graphics));
        // this.drawHitBox(graphics);
    }

    /**
     * Function used to draw the hit box of each element
     *
     * @param graphics Drawing object
     */
    private void drawHitBox(Graphics2D graphics) {
        this.levelDisposition.stream().filter(tile -> !tile.getType().equals(Tile.Type.GROUND)).forEach(tile -> {
            var pos = tile.getCoordinate();
            graphics.setColor(Color.RED);
            graphics.drawRect(pos.getX(), pos.getY(), TITLE_SIZE, TITLE_SIZE);
        });
        this.entities.forEach(entity -> {
            var pos = entity.getCoordinate();
            graphics.setColor(Color.BLUE);
            graphics.drawRect(pos.getX(), pos.getY(), Entity.ENTITY_SIZE, Entity.ENTITY_SIZE);
        });
    }

    /**
     * Tick the level
     *
     * @param cmd game command
     */
    public void evolve(Cmd cmd) {
        // tick all entities
        this.entities.forEach(entity -> entity.evolve(cmd, this.levelEvolve));

        // Check if player is on the end tile
        if (this.levelEvolve.overlap(
                this.player.getCoordinate().getX(),
                this.player.getCoordinate().getY(),
                Entity.ENTITY_SIZE, Entity.ENTITY_SIZE, List.of(this.endTile)
        )
        ) {
            PacmanGame.onEvent(new PlayerOnEndTileEvent(this.endTile));
        }

        List<Tile> liste = new ArrayList<>();
        liste.addAll(this.levelEvolve.bonusTiles);
        liste.addAll(this.levelEvolve.specialBonusTiles);
        liste.addAll(this.levelEvolve.timeBonusTiles);
        liste.addAll(this.levelEvolve.munitionBonusTiles);


        // Check if player is on a bonus tile
        this.levelEvolve.overlapFindAny(
                this.player.getCoordinate().getX(),
                this.player.getCoordinate().getY(),
                Entity.ENTITY_SIZE, Entity.ENTITY_SIZE, liste
        ).ifPresent(tile -> {
            if (tile.getType() == Tile.Type.BONUS) { // check if the tile has already been visited
                PacmanGame.onEvent(new PlayerOnBonusTileEvent(tile));
            }
            if (tile.getType() == Tile.Type.ADDLIFE) { // check if the tile has already been visited
                PacmanGame.onEvent(new PlayerOnLifeBonusTileEvent(tile));
            }
            if (tile.getType() == Tile.Type.ADDTIME) { // check if the tile has already been visited
                PacmanGame.onEvent(new PlayerOnTimeBonusTileEvent(tile));
            }
            if (tile.getType() == Tile.Type.ADDMUNITION) { // check if the tile has already been visited
                PacmanGame.onEvent(new PlayerOnMunitionBonusTileEvent(tile));
            }
        });
    }

    /**
     * Getter used to get the tile list
     *
     * @return list of all tile
     */
    public List<Tile> getLevelDisposition() {
        return this.levelDisposition;
    }

    /**
     * Inner class use for describe interaction with the level
     */
    public static class LevelEvolve {
        /**
         * List of all wall tile
         */
        private List<Tile> wallTiles;
        /**
         * List of all bonus tile
         */
        private List<Tile> bonusTiles;

        /**
         * Liste of bonus life
         */
        private List<Tile> specialBonusTiles;

        /**
         * Liste of bonus time
         */
        private List<Tile> timeBonusTiles;

        /**
         * Liste of bonus munition
         */
        private List<Tile> munitionBonusTiles;

        /**
         * Liste of trap
         */
        private List<Tile> trapTiles;

        /**
         * Liste of treasure bonus tiles
         */
        private List<Tile> treasureBonusTiles;

        /**
         * Check if a custom rectangle is not overlapping a wall
         *
         * @param x X position of the rectangle
         * @param y Y position of the rectangle
         * @param w Width of the rectangle
         * @param h Height of the rectangle
         *
         * @return <ul><li>true if it is not overlapping a wall</li><li>false if it is overlapping a wall</li></ul>
         */
        public boolean notOverlap(int x, int y, int w, int h) {
            return !overlap(x, y, w, h, this.wallTiles);
        }

        /**
         * Check if a custom rectangle is overlapping a list of tile
         *
         * @param x     X position of the rectangle
         * @param y     Y position of the rectangle
         * @param w     Width of the rectangle
         * @param h     Height of the rectangle
         * @param tiles list of tile to be checked
         *
         * @return <ul><li>true if it is overlapping a wall</li><li>false if it is not overlapping a wall</li></ul>
         */
        public boolean overlap(int x, int y, int w, int h, List<Tile> tiles) {
            return tiles
                    .stream()
                    .anyMatch(tile -> !(x + w < tile.getCoordinate().getX() ||
                            tile.getCoordinate().getX() + TITLE_SIZE < x ||
                            y + h < tile.getCoordinate().getY() ||
                            tile.getCoordinate().getY() + TITLE_SIZE < y)
                    );
        }

        /**
         * Check if a custom rectangle is overlapping a list of tile and return the corresponding tile
         *
         * @param x     X position of the rectangle
         * @param y     Y position of the rectangle
         * @param w     Width of the rectangle
         * @param h     Height of the rectangle
         * @param tiles list of tile to be checked
         *
         * @return return the overlapping tile
         */
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
