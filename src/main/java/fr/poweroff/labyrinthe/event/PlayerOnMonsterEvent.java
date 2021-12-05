package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.level.tile.TileGround;

public class PlayerOnMonsterEvent implements Event<Boolean>{

    @Override
    public String getName() {
        return "PlayerOnMonster";
    }

    @Override
    public Boolean getData() {
        return true;
    }
}
