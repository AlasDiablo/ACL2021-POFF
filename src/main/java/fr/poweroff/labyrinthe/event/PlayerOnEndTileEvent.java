package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;

/**
 * Event use when the player is on a end tile
 */
public class PlayerOnEndTileEvent implements Event<Tile> {

    /**
     * The end tile in question
     */
    private final Tile tile;

    /**
     * Default constructor
     *
     * @param tile The tile how throw the event
     */
    public PlayerOnEndTileEvent(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return Return the name of the event
     */
    @Override
    public String getName() {
        return "PlayerOnEndTile";
    }

    /**
     * @return Return the end tile
     */
    @Override
    public Tile getData() {
        return this.tile;
    }
}
