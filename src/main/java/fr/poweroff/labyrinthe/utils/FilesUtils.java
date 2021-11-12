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

public class FilesUtils {

    private static final Map<String, BufferedImage> BUFFERED_IMAGE_MAP;

    static {
        BUFFERED_IMAGE_MAP = new HashMap<>();
    }

    private static ClassLoader classLoader = null;

    public static void setClassLoader(ClassLoader classLoaderIn) {
        if (classLoader == null) classLoader = classLoaderIn;
    }

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
