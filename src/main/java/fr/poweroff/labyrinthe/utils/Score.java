package fr.poweroff.labyrinthe.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Score {

    private File file;

    public Score() {
        try {
            file = new File("src/main/resources/meilleur_score.txt");

            if (file.createNewFile()) {
                System.out.println();
            } else {
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajout le nouveau score s'il est plus élevé
     * @param score le nouveau score
     * @throws FileNotFoundException exception pour la l'ecriture
     * @throws UnsupportedEncodingException exception pour la l'ecriture
     */
    public void addFile(int score) throws IOException {
        int s = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                s = Integer.parseInt(line);
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //Si le score était plus petit que le nouveau, on le change
        if(s < score) {
            PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
            writer.println(score);
            writer.close();
        }
    }

    public int getBestScore(){
        int s = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                s = Integer.parseInt(line);
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //Si le score était plus petit que le nouveau, on le change
        return s;
    }
}
