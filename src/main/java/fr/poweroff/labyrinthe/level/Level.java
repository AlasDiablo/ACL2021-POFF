package fr.poweroff.labyrinthe.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.event.PlayerOnBonusTileEvent;
import fr.poweroff.labyrinthe.event.PlayerOnEndTileEvent;
import fr.poweroff.labyrinthe.event.cases.*;
import fr.poweroff.labyrinthe.level.entity.Entity;
import fr.poweroff.labyrinthe.level.tile.*;
import fr.poweroff.labyrinthe.level.tile.special.*;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Class use tu create a level and handle each game tick
 */
public class Level {
    /**
     * Size of a title in pixel
     */
    public static final int TITLE_SIZE   = 11 * 2;
    /**
     * Number of bonus in a generated level
     */
    public static final int BONUS_NUMBER = 3;
    /**
     * Minimal number of inner wall in a generated level
     */
    public static final int WALL_NUMBER  = 6;
    public static       int LIFE_NUMBER;

    /**
     * Nombre de timer sur la map
     */
    public static int TIMER_NUMBER;

    public static int MUNITION_NUMBER;

    public static int TREASURE_NUMBER;

    public static int TRAP_NUMBER;
    public static int GLUE_NUMBER;

    /**
     * Level evolve used to check tile and entities overlapping and more
     */
    private final LevelEvolve  levelEvolve;
    /**
     * List of all level tiles
     */
    private       List<Tile>   levelDisposition;
    /**
     * List of all level entities
     */
    private       List<Entity> entities;
    /**
     * The end tile of the level
     */
    private       Tile         endTile;
    /**
     * The player instances
     */
    private       Entity       player;

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
        ImmutableList.Builder<Tile> levelBuilder         = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder          = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> bonusBuilder         = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> specialBonusBuilder  = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> munitionBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> timeBonusBuilder     = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> trapBuilder          = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> glueBuilder          = new ImmutableList.Builder<>();
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
                    currentTile = new TileLife(rx, ry);
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
                    break;
                //Create tile for treasure
                case ADDTREASURE:
                    currentTile = new TileTreasure(rx, ry);
                    treasureBonusBuilder.add(currentTile);
                    break;
                //Create tile trap
                case TRAP:
                    currentTile = new TileTrap(rx, ry);
                    trapBuilder.add(currentTile);
                    break;
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
        this.levelDisposition               = levelBuilder.build();
        this.levelEvolve.wallTiles          = wallBuilder.build();
        this.levelEvolve.bonusTiles         = bonusBuilder.build();
        this.levelEvolve.specialBonusTiles  = specialBonusBuilder.build();
        this.levelEvolve.timeBonusTiles     = timeBonusBuilder.build();
        this.levelEvolve.munitionBonusTiles = munitionBonusBuilder.build();
        this.levelEvolve.treasureBonusTiles = treasureBonusBuilder.build();
        this.levelEvolve.trapTiles          = trapBuilder.build();
        this.levelEvolve.glueTiles = glueBuilder.build();
        ImmutableList.Builder<Tile> interactionTiles = new ImmutableList.Builder<>();
        interactionTiles.addAll(this.levelEvolve.bonusTiles);
        interactionTiles.addAll(this.levelEvolve.specialBonusTiles);
        interactionTiles.addAll(this.levelEvolve.timeBonusTiles);
        interactionTiles.addAll(this.levelEvolve.munitionBonusTiles);
        interactionTiles.addAll(this.levelEvolve.treasureBonusTiles);
        interactionTiles.addAll(this.levelEvolve.trapTiles);
        interactionTiles.addAll(this.levelEvolve.glueTiles);
        this.levelEvolve.interactionTiles = interactionTiles.build();
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

        //Permet de doser le nombre de Tile selon le niveau
        switch (difficult) {
            case 1:
                LIFE_NUMBER = 0;
                TIMER_NUMBER = 0;
                MUNITION_NUMBER = 0;
                TREASURE_NUMBER = 1;
                TRAP_NUMBER = 30;
                GLUE_NUMBER = 5;
                break;
            case 2:
                LIFE_NUMBER = 0;
                TIMER_NUMBER = 0;
                MUNITION_NUMBER = 2;
                TREASURE_NUMBER = 1;
                TRAP_NUMBER = 15;
                GLUE_NUMBER = 10;
                break;
            case 3:
                LIFE_NUMBER = 1;
                TIMER_NUMBER = 1;
                MUNITION_NUMBER = 3;
                TREASURE_NUMBER = 2;
                TRAP_NUMBER = 20;
                GLUE_NUMBER = 10;
                break;
            default:
                LIFE_NUMBER = 1;
                TIMER_NUMBER = 1;
                MUNITION_NUMBER = 5;
                TREASURE_NUMBER = 4;
                TRAP_NUMBER = 25;
                GLUE_NUMBER = 15;
                break;

        }

        // Create level tile list
        ImmutableList.Builder<Tile> levelBuilder         = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> wallBuilder          = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> bonusBuilder         = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> specialBonusBuilder  = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> munitionBonusBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> timeBonusBuilder     = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> trapBuilder          = new ImmutableList.Builder<>();
        ImmutableList.Builder<Tile> glueBuilder          = new ImmutableList.Builder<>();
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

        Map<Integer, Tile.Type> innerTiles = Maps.newHashMap();
        innerTiles.put(startPos, Tile.Type.START);
        innerTiles.put(endPos, Tile.Type.END);

        // Create the list of bonus tile index
        this.createRandomIndexList(BONUS_NUMBER, innerTiles, Tile.Type.BONUS, surface, perimeter);

        // Create the list of glue tile index
        this.createRandomIndexList(GLUE_NUMBER, innerTiles, Tile.Type.ADDGLUE, surface, perimeter);

        // Create the list of special bonus tile index (life)
        this.createRandomIndexList(LIFE_NUMBER, innerTiles, Tile.Type.ADDLIFE, surface, perimeter);

        // Create the list of special bonus tile index (time)
        this.createRandomIndexList(TIMER_NUMBER, innerTiles, Tile.Type.ADDTIME, surface, perimeter);

        // Create the list of special bonus tile index (munition)
        this.createRandomIndexList(MUNITION_NUMBER, innerTiles, Tile.Type.ADDMUNITION, surface, perimeter);

        // Create the list of special bonus tile index (treasure)
        this.createRandomIndexList(TREASURE_NUMBER, innerTiles, Tile.Type.ADDTREASURE, surface, perimeter);

        // Create the list of special tile index (trap)
        this.createRandomIndexList(TRAP_NUMBER, innerTiles, Tile.Type.TRAP, surface, perimeter);

        var wallNumberWithDifficult = WALL_NUMBER + (WALL_NUMBER / 2 * (difficult - 1));

        // Create a random amount of wall
        var wallNumber = PacmanGame.RANDOM.ints(
                1,
                wallNumberWithDifficult,
                Math.max(wallNumberWithDifficult, (int) Math.floor(surface / 16f))
        ).findAny().orElse(wallNumberWithDifficult);

        // Create the list of wall tile index
        this.createRandomIndexList(wallNumber, innerTiles, Tile.Type.WALL, surface, perimeter);

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
                    if (innerTiles.containsKey(levelIndex)) {
                        var type = innerTiles.get(levelIndex);
                        switch (type) {
                            // Create start tile and place player onto
                            case START: {
                                currentTile = new TileStart(rx, ry);
                                player.getCoordinate().setX(rx + 2);
                                player.getCoordinate().setY(ry + 2);
                                break;
                            }
                            // Create end tile
                            case END: {
                                currentTile  = new TileEnd(rx, ry);
                                this.endTile = currentTile;
                                break;
                            }
                            // Create inner wall tile
                            case WALL: {
                                currentTile = new TileWall(rx, ry);
                                wallBuilder.add(currentTile);
                                break;
                            }
                            // Create bonus tile
                            case BONUS: {
                                currentTile = new TileBonus(rx, ry);
                                bonusBuilder.add(currentTile);
                                break;
                            }
                            case ADDLIFE: {
                                currentTile = new TileLife(rx, ry);
                                specialBonusBuilder.add(currentTile);
                                break;
                            }
                            case ADDTIME: {
                                currentTile = new TileTime(rx, ry);
                                timeBonusBuilder.add(currentTile);
                                break;
                            }
                            case ADDMUNITION: {
                                currentTile = new TileMunitions(rx, ry);
                                munitionBonusBuilder.add(currentTile);
                                break;
                            }
                            case ADDGLUE: {
                                currentTile = new TileGlue(rx, ry);
                                glueBuilder.add(currentTile);
                                break;
                            }
                            case ADDTREASURE: {
                                currentTile = new TileTreasure(rx, ry);
                                treasureBonusBuilder.add(currentTile);
                                break;
                            }
                            case TRAP: {
                                currentTile = new TileTrap(rx, ry);
                                trapBuilder.add(currentTile);
                                break;
                            }
                            // Create ground tile
                            default: {
                                currentTile = new TileGround(rx, ry);
                                break;
                            }
                        }
                    } else { // Create ground tile
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

        this.levelDisposition               = levelBuilder.build();
        this.levelEvolve.wallTiles          = wallBuilder.build();
        this.levelEvolve.bonusTiles         = bonusBuilder.build();
        this.levelEvolve.specialBonusTiles  = specialBonusBuilder.build();
        this.levelEvolve.timeBonusTiles     = timeBonusBuilder.build();
        this.levelEvolve.munitionBonusTiles = munitionBonusBuilder.build();
        this.levelEvolve.treasureBonusTiles = treasureBonusBuilder.build();
        this.levelEvolve.trapTiles          = trapBuilder.build();
        this.levelEvolve.glueTiles = glueBuilder.build();
        ImmutableList.Builder<Tile> interactionTiles = new ImmutableList.Builder<>();
        interactionTiles.addAll(this.levelEvolve.bonusTiles);
        interactionTiles.addAll(this.levelEvolve.specialBonusTiles);
        interactionTiles.addAll(this.levelEvolve.timeBonusTiles);
        interactionTiles.addAll(this.levelEvolve.munitionBonusTiles);
        interactionTiles.addAll(this.levelEvolve.treasureBonusTiles);
        interactionTiles.addAll(this.levelEvolve.trapTiles);
        interactionTiles.addAll(this.levelEvolve.glueTiles);
        this.levelEvolve.interactionTiles = interactionTiles.build();
        this.initMonster(width, height, levelDisposition, entities);
    }

    private void createRandomIndexList(int number, Map<Integer, Tile.Type> innerTiles, Tile.Type type, int surface, int perimeter) {
        for (int i = 0; i < number; i++) {
            int bonusIndex;
            do {
                bonusIndex = (int) Math.floor((surface - perimeter) * PacmanGame.RANDOM.nextFloat());
            } while (innerTiles.containsKey(bonusIndex));
            innerTiles.put(bonusIndex, type);
        }
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
        this.entities.forEach(entity -> entity.drawHitBox(graphics));
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

        // Check if player is on a bonus tile
        this.levelEvolve.overlapFindAny(
                this.player.getCoordinate().getX(),
                this.player.getCoordinate().getY(),
                Entity.ENTITY_SIZE, Entity.ENTITY_SIZE, this.levelEvolve.interactionTiles
        ).ifPresent(tile -> {
            switch (tile.getType()) {
                case BONUS: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnBonusTileEvent(tile));
                    break;
                }
                case ADDLIFE: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnLifeBonusTileEvent(tile));
                    break;
                }
                case ADDTIME: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnTimeBonusTileEvent(tile));
                    break;
                }
                case ADDMUNITION: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnMunitionBonusTileEvent(tile));
                    break;
                }
                case ADDTREASURE: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnTreasureBonusTileEvent(tile));
                    break;
                }
                case TRAP: { // check if the tile has already been visited
                    PacmanGame.onEvent(new PlayerOnTrapTileEvent(tile));
                    break;
                }
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
    public class LevelEvolve {
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
         *
         */
        private List<Tile> interactionTiles;
        /**
         *Liste of glue tiles
         */
        private List<Tile> glueTiles;

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
         *  Check if a custom rectangle is overlapping a glue tile
         *
         * @param x X position of the rectangle
         * @param y Y position of the rectangle
         * @param w Width of the rectangle
         * @param h Height of the rectangle
         * @return <ul><li>true if it is overlapping the player</li><li>false if it is not overlapping the player</li></ul>
         */
        public boolean overlapWithGlue(int x, int y, int w, int h) {
            return this.overlap(x, y, w, h, glueTiles);
        }
        /**
         * Check if a custom rectangle is overlapping the player
         *
         * @param x X position of the rectangle
         * @param y Y position of the rectangle
         * @param w Width of the rectangle
         * @param h Height of the rectangle
         *
         * @return <ul><li>true if it is overlapping the player</li><li>false if it is not overlapping the player</li></ul>
         */
        public boolean overlapWithPlayer(int x, int y, int w, int h) {
            return Stream.of(Level.this.player)
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
