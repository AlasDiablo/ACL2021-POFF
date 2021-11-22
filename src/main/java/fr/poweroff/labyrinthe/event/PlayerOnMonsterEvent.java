package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;

public class PlayerOnMonsterEvent  implements Event<Tile> {

    /**
     * The end tile in question
     */
    private final Tile tile;

    /**
     * Default constructor
     *
     * @param tile The tile how throw the event
     */
    public PlayerOnMonsterEvent(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return Return the name of the event
     */
    @Override
    public String getName() {
        return "PlayerOnMonster";
    }

    /**
     * @return Return the end tile
     */
    @Override
    public Tile getData() {
        return this.tile;
    }
}