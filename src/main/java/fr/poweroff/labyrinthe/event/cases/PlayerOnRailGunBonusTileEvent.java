package fr.poweroff.labyrinthe.event.cases;

import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.level.tile.Tile;

/**
 * Event use when the player is on a bonus tile
 */
public class PlayerOnRailGunBonusTileEvent implements Event<Tile> {

    /**
     * The bonus tile in question
     */
    private final Tile tile;

    /**
     * Default constructor
     *
     * @param tile The tile how throw the event
     */
    public PlayerOnRailGunBonusTileEvent(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return Return the name of the event
     */
    @Override
    public String getName() {
        return "PlayerOnRailGunBonusTile";
    }

    /**
     * @return Return the bonus tile
     */
    @Override
    public Tile getData() {
        return this.tile;
    }
}
