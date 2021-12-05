package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Monster extends Entity {

    /**
     * The player sprite path
     */
    private static final String    SPRITE_PATH = "assets/textures/ghost/";
    private final        int       spriteIndex;
    private final        List<Cmd> directions  = new ArrayList<>(List.of(Cmd.RIGHT, Cmd.LEFT, Cmd.DOWN, Cmd.UP));
    protected            boolean   vertical;
    protected            Cmd       direction;

    public Monster(Coordinate coordinate) {
        super(
                coordinate,
                Monster.getSprite("monster_blinky.png"),
                Monster.getSprite("monster_clyde.png"),
                Monster.getSprite("monster_inky.png"),
                Monster.getSprite("monster_pinky.png"),
                Monster.getSprite("monster_blue.png")
        );
        this.spriteIndex = PacmanGame.RANDOM.ints(0, this.sprite.length - 1).findAny().orElse(0);
        this.vertical    = PacmanGame.RANDOM.nextBoolean();
        var bool = PacmanGame.RANDOM.nextBoolean();
        if (this.vertical) {
            this.direction = bool ? Cmd.DOWN : Cmd.UP;
        } else {
            this.direction = bool ? Cmd.RIGHT : Cmd.LEFT;
        }
    }

    public Monster(Coordinate coordinate, int spriteIndex) {
        super(
                coordinate,
                Monster.getSprite("monster_blinky.png"),
                Monster.getSprite("monster_clyde.png"),
                Monster.getSprite("monster_inky.png"),
                Monster.getSprite("monster_pinky.png"),
                Monster.getSprite("monster_blue.png")
        );
        this.spriteIndex = spriteIndex;
        this.vertical    = PacmanGame.RANDOM.nextBoolean();
        var bool = PacmanGame.RANDOM.nextBoolean();
        if (this.vertical) {
            this.direction = bool ? Cmd.DOWN : Cmd.UP;
        } else {
            this.direction = bool ? Cmd.RIGHT : Cmd.LEFT;
        }
    }

    /**
     * Funcion use to get the player sprite
     *
     * @param name the name of the sprite
     *
     * @return the sprite as a buffered image
     */
    private static BufferedImage getSprite(String name) {
        return FilesUtils.getImage(SPRITE_PATH + name);
    }

    @Override
    public Cmd getDirection() {
        return null;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.getSprite()[this.spriteIndex], this.getCoordinate().getX(), this.getCoordinate().getY(), ENTITY_SIZE, ENTITY_SIZE, null);
    }

    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        this.changeDirectionRandomly();
        this.move(levelEvolve);
    }

    protected void changeDirectionRandomly() {
        if (PacmanGame.RANDOM.nextInt(1024) > 1000) {
            this.changeDirection();
        }
    }

    protected void move(LevelEvolve levelEvolve) {
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();
        int newX, newY;

        if (this.direction == Cmd.UP || this.direction == Cmd.LEFT) {
            newX = x - (this.vertical ? 0 : MOVE_SPEED);
            newY = y - (this.vertical ? MOVE_SPEED : 0);

        } else {
            newX = x + (this.vertical ? 0 : MOVE_SPEED);
            newY = y + (this.vertical ? MOVE_SPEED : 0);
        }

        if (levelEvolve.notOverlap(newX, newY, ENTITY_SIZE, ENTITY_SIZE)) {
            this.coordinate.setX(newX);
            this.coordinate.setY(newY);
        } else {
            this.changeDirection();
        }
    }

    private void changeDirection() {
        var dir = PacmanGame.RANDOM.nextInt(4);
        this.direction = this.directions.get(dir);
        this.vertical  = this.direction == Cmd.UP || this.direction == Cmd.DOWN;
    }
}
