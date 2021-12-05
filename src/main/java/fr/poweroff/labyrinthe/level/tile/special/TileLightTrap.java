package fr.poweroff.labyrinthe.level.tile.special;

import com.jcraft.jsch.Random;
import fr.poweroff.labyrinthe.level.Level;
import fr.poweroff.labyrinthe.level.entity.LightTrap;
import fr.poweroff.labyrinthe.level.tile.Tile;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;

public class TileLightTrap extends Tile {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    /**
     * Trap with launcher of raylight
     */
    private LightTrap rayLight;
    private int direction;
    /**
     * Constructor how take coordinate
     *  @param x X coordinate
     * @param y Y coordinate
     */
    public TileLightTrap(int x, int y) {
        super(x, y, FilesUtils.getImage("assets/textures/tile/lightTrapUp.png"),
                    FilesUtils.getImage("assets/textures/tile/lightTrapDown.png"),
                    FilesUtils.getImage("assets/textures/tile/lightTrapLeft.png"),
                    FilesUtils.getImage("assets/textures/tile/lightTrapRight.png"));
        direction = PacmanGame.RANDOM.nextInt(4);
        rayLight = null;
    }

    /**
     * Constructor how take coordinate
     *
     * @param coordinate Coordinate object
     */
    public TileLightTrap(Coordinate coordinate) {
        super(coordinate, FilesUtils.getImage("assets/textures/tile/lightTrapUp.png"),
                FilesUtils.getImage("assets/textures/tile/lightTrapDown.png"),
                FilesUtils.getImage("assets/textures/tile/lightTrapLeft.png"),
                FilesUtils.getImage("assets/textures/tile/lightTrapRight.png"));
        direction = PacmanGame.RANDOM.nextInt(4);
    }

    /**
     * Function how draw the tile
     *
     * @param graphics2D drawing object
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();
        graphics2D.drawImage(this.getSprites()[direction], x, y, Level.TITLE_SIZE, Level.TITLE_SIZE, null);
    }

    /**
     * Function used to return the type of the tile
     *
     * @return Type on the tile
     */
    @Override
    public Type getType() {
        return Type.LIGHTTRAP;
    }

    public void construct_rayLight(LightTrap ltAssocied){

    }

    public int getDirection() {
        return direction;
    }
}
