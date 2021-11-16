package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;

/**
 * Event use when the player is on a bonus tile
 */
public class PlayerOnBonusTileEvent implements Event<Tile> {

    /**
     * The bonus tile in question
     */
    private final Tile tile;

    /**
     * Default constructor
     *
     * @param tile The tile how throw the event
     */
    public PlayerOnBonusTileEvent(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return Return the name of the event
     */
    @Override
    public String getName() {
        return "PlayerOnBonusTile";
    }

    /**
     * @return Return the bonus tile
     */
    @Override
    public Tile getData() {
        return this.tile;
    }
}
