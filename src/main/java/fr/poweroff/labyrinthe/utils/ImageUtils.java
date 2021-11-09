package fr.poweroff.labyrinthe.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageUtils {

    private static ClassLoader classLoader = null;

    public static void setClassLoader(ClassLoader classLoaderIn) {
        if (classLoader == null) classLoader = classLoaderIn;
    }

    public static BufferedImage getImage(String url) {
        try {
            return ImageIO.read(new File(Objects.requireNonNull(classLoader.getResource(url)).getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
