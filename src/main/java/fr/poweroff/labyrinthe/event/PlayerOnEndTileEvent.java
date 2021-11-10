package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;

public class PlayerOnEndTileEvent implements Event<Tile> {
    private final Tile tile;

    public PlayerOnEndTileEvent(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String getName() {
        return "PlayerOnEndTile";
    }

    @Override
    public Tile getData() {
        return this.tile;
    }
}
