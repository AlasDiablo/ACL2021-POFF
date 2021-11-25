package fr.poweroff.labyrinthe.engine.menu;

import fr.poweroff.labyrinthe.utils.FilesUtils;

import java.awt.image.BufferedImage;

public class Menu {
    private static final BufferedImage sprites = FilesUtils.getImage("assets/textures/gui/menu.jpg");

    public static BufferedImage getSprites() {
        return sprites;
    }
}
