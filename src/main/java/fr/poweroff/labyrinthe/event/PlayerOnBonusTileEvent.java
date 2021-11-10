package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;

public class PlayerOnBonusTileEvent implements Event<Tile> {
    private final Tile tile;

    public PlayerOnBonusTileEvent(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String getName() {
        return "PlayerOnBonusTile";
    }

    @Override
    public Tile getData() {
        return this.tile;
    }
}
