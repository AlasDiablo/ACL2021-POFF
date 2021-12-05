package fr.poweroff.labyrinthe.utils;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Score {

    private static final File             SCORE_DIR  = new File(System.getProperty("user.home"), ".poweroff-games");
    private static final File             SCORE_FILE = new File(SCORE_DIR, "pac-pac-score.json");
    private static final List<JsonObject> SCORES     = Lists.newArrayList();
    private static       boolean          CAN_WRITE  = true;

    public static void init() {
        try {
            var scoreDirResult  = SCORE_DIR.mkdir();
            var scoreFileResult = SCORE_FILE.createNewFile();

            if (scoreDirResult) {
                System.out.println("[USER/DEBUG] Score dir created");
            }

            if (scoreFileResult) {
                System.out.println("[USER/DEBUG] Score file created");
            }
        } catch (IOException e) {
            CAN_WRITE = false;
        }

        try {
            var parsedJson = JsonParser.parseReader(new FileReader(SCORE_FILE));
            var json       = parsedJson.getAsJsonObject();
            SCORES.add(json.get("0").getAsJsonObject());
            SCORES.add(json.get("1").getAsJsonObject());
            SCORES.add(json.get("2").getAsJsonObject());
            SCORES.add(json.get("3").getAsJsonObject());
        } catch (JsonParseException | IllegalStateException e) {
            SCORES.add(createPlayerScore("Pinky", 100));
            SCORES.add(createPlayerScore("Inky", 75));
            SCORES.add(createPlayerScore("Blinky", 50));
            SCORES.add(createPlayerScore("Clyde", 25));
            write();
        } catch (FileNotFoundException e) {
            CAN_WRITE = false;
        }
    }

    public static JsonObject createPlayerScore(String name, int score) {
        var json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("score", score);
        return json;
    }

    private static void write() {
        if (!CAN_WRITE) return;
        try {
            var json = new JsonObject();
            var i    = new AtomicInteger(0);
            SCORES.forEach(jsonObject -> json.add(String.valueOf(i.getAndIncrement()), jsonObject));
            var writer = new FileWriter(SCORE_FILE);
            writer.write(json.toString());
            writer.flush();
            writer.close();
        } catch (Exception ignored) {
        }
    }

    public static void addScore(String name, int score) {
        var index = new AtomicInteger(-1);
        var i     = new AtomicInteger(0);
        SCORES.forEach(jsonObject -> {
            if (score >= jsonObject.get("score").getAsInt()) {
                index.set(i.get());
            }
            i.getAndIncrement();
        });
        if (index.get() >= 0) {
            SCORES.set(index.get(), createPlayerScore(name, score));
            write();
        }
    }

    public static List<JsonObject> getScores() {
        return SCORES;
    }
}
