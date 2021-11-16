package fr.poweroff.labyrinthe.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utils class used to get file ressource
 */
public class FilesUtils {

    /**
     * Map of sprite use to avoid reloading a loaded sprite
     */
    private static final Map<String, BufferedImage> BUFFERED_IMAGE_MAP;

    /**
     * Class loeader use to get the ressource
     */
    private static ClassLoader classLoader = null;

    static {
        BUFFERED_IMAGE_MAP = new HashMap<>();
    }

    /**
     * Function use by the game to initialize the class loader
     *
     * @param classLoaderIn Class loader use by the game
     */
    public static void setClassLoader(ClassLoader classLoaderIn) {
        if (classLoader == null) classLoader = classLoaderIn;
    }

    /**
     * Function used to load an image from the ressource or to get the loaded version
     *
     * @param url The path to the sprite
     *
     * @return Return a buffered image containing the sprite
     */
    public static BufferedImage getImage(String url) {
        if (BUFFERED_IMAGE_MAP.containsKey(url))
            return BUFFERED_IMAGE_MAP.get(url);
        try {
            var image = ImageIO.read(new File(Objects.requireNonNull(classLoader.getResource(url)).getFile()));
            BUFFERED_IMAGE_MAP.put(url, image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function used to get a json element from the ressource
     *
     * @param url The path to the json file
     *
     * @return Return a parsed json element
     */
    public static JsonElement getJson(String url) {
        JsonElement json;
        var         inputStream = classLoader.getResourceAsStream(url);
        if (inputStream == null)
            json = new JsonObject();
        else {
            var reader = new InputStreamReader(inputStream);
            json = JsonParser.parseReader(reader);
        }
        return json;
    }
}
