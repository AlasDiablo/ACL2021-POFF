package fr.poweroff.labyrinthe.level.tile;

import fr.poweroff.labyrinthe.event.Event;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.ImageUtils;

import java.awt.*;

public class TileBonus extends Tile{

    public TileBonus(int x, int y){
        super(ImageUtils.getImage("tile_bonus.png"), x, y);
    }

    public TileBonus(Coordinate coordinate) {
        super(ImageUtils.getImage("tile_bonus.png"), coordinate);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprite(), x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Event onEvent(Event event) {
        return event;
    }
}
