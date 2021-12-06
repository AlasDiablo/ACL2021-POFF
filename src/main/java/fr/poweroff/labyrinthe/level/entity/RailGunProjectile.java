package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.engine.Cmd;
import fr.poweroff.labyrinthe.event.ProjectileOnSomethingEvent;
import fr.poweroff.labyrinthe.level.Level.LevelEvolve;
import fr.poweroff.labyrinthe.model.PacmanGame;
import fr.poweroff.labyrinthe.utils.Coordinate;
import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class how define the player entity
 */
public class RailGunProjectile extends Entity {
    /**
     * Speed of the projectile
     */
    private static final int PROJECTILE_SPEED = 20;
    /**
     * The Projectile sprite path
     */
    private static final String SPRITE_PATH = "assets/textures/projectiles/";
    /**
     * Direction of the projectile
     */
    protected Cmd direction;
    /**
     * The current player sprite
     */
    private int spriteIndex;

    /**
     * Default constructor of the player
     */
    public RailGunProjectile(Coordinate coordinate, Cmd direction) {
        super(
                coordinate,
                RailGunProjectile.getSprite("projectile_right.png"),
                RailGunProjectile.getSprite("projectile_down.png"),
                RailGunProjectile.getSprite("projectile_left.png"),
                RailGunProjectile.getSprite("projectile_up.png")
        );
        this.spriteIndex = 0;
        this.direction = direction;
    }

    /**
     * Funcion use to get the player sprite
     *
     * @param name the name of the sprite
     * @return the sprite as a buffered image
     */
    private static BufferedImage getSprite(String name) {
        return FilesUtils.getImage(SPRITE_PATH + name);
    }

    /**
     * Fonction used to draw the player
     *
     * @param graphics Drawing object
     */
    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                this.getSprite()[this.spriteIndex],
                this.getCoordinate().getX(),
                this.getCoordinate().getY(),
                ENTITY_SIZE, ENTITY_SIZE, null
        );
    }

    /**
     * Function used to tick the player
     *
     * @param cmd         the current command
     * @param levelEvolve the level evolve instance use for overlapping check
     */
    @Override
    public void evolve(Cmd cmd, LevelEvolve levelEvolve) {
        //System.out.println("Player direction: " + this.direction.toString());
        var x = this.coordinate.getX();
        var y = this.coordinate.getY();
        var newx = x;
        var newy = y;

        switch (this.direction) {
            case DOWN:
                newy += PROJECTILE_SPEED;
                this.spriteIndex = 1;
                break;
            case LEFT:
                newx += -PROJECTILE_SPEED;
                this.spriteIndex = 2;

                break;
            case UP:
                newy += -PROJECTILE_SPEED;
                this.spriteIndex = 3;

                break;
            case RIGHT:
            default:
                newx += PROJECTILE_SPEED;
                this.spriteIndex = 0;

                break;
            case LEFT_DOWN:
                newx += -PROJECTILE_SPEED;
                newy += PROJECTILE_SPEED;
                this.spriteIndex = 1;

                break;
            case LEFT_UP:
                newx += -PROJECTILE_SPEED;
                newy += -PROJECTILE_SPEED;
                this.spriteIndex = 1;

                break;
            case RIGHT_DOWN:
                newx += PROJECTILE_SPEED;
                newy += PROJECTILE_SPEED;
                this.spriteIndex = 1;

                break;
            case RIGHT_UP:
                newx += PROJECTILE_SPEED;
                newy += -PROJECTILE_SPEED;
                this.spriteIndex = 1;

                break;
        }
        if (levelEvolve.notOverlap(x, y, ENTITY_SIZE, ENTITY_SIZE)) {
            this.coordinate.setX(newx);
            this.coordinate.setY(newy);
        } else {
            PacmanGame.onEvent(new ProjectileOnSomethingEvent(this));
        }
    }

    public void move(LevelEvolve levelEvolve) {

    }

    @Override
    public Cmd getDirection() {
        return null;
    }
}
