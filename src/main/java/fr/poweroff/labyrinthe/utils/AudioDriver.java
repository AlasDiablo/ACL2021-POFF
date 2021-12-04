package fr.poweroff.labyrinthe.utils;

import fr.poweroff.labyrinthe.model.PacmanGame;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioDriver {
    private static final String PATH = "assets/sounds/";

    public static void init() {
        playPowerup();
    }

    private static void playSounds(Sounds sounds) {
        sounds.play();
    }

    public static void playCoin() {
        var    random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.COIN_1;
        else sounds = Sounds.COIN_2;
        playSounds(sounds);
    }

    public static void playExplosion() {
        var    random_int = PacmanGame.RANDOM.nextInt(5);
        Sounds sounds;
        switch (random_int) {
            default:
                sounds = Sounds.EXPLOSION_1;
                break;
            case 1:
                sounds = Sounds.EXPLOSION_2;
                break;
            case 2:
                sounds = Sounds.EXPLOSION_3;
                break;
            case 3:
                sounds = Sounds.EXPLOSION_4;
                break;
            case 4:
                sounds = Sounds.EXPLOSION_5;
                break;
        }
        playSounds(sounds);
    }

    public static void playHit() {
        var    random_int = PacmanGame.RANDOM.nextInt(3);
        Sounds sounds;
        switch (random_int) {
            default:
                sounds = Sounds.HIT_1;
                break;
            case 1:
                sounds = Sounds.HIT_2;
                break;
            case 2:
                sounds = Sounds.HIT_3;
                break;
        }
        playSounds(sounds);
    }

    public static void playPowerup() {
        var    random_int = PacmanGame.RANDOM.nextInt(3);
        Sounds sounds;
        switch (random_int) {
            default:
                sounds = Sounds.POWERUP_1;
                break;
            case 1:
                sounds = Sounds.POWERUP_2;
                break;
            case 2:
                sounds = Sounds.POWERUP_3;
                break;
        }
        playSounds(sounds);
    }

    public static void playSelect() {
        var    random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.SELECT_1;
        else sounds = Sounds.SELECT_2;
        playSounds(sounds);
    }

    public static void playShoot() {
        var    random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.SHOOT_1;
        else sounds = Sounds.SHOOT_2;
        playSounds(sounds);
    }

    public enum Sounds implements LineListener {
        COIN_1("coin_1"),
        COIN_2("coin_2"),
        EXPLOSION_1("explosion_1"),
        EXPLOSION_2("explosion_2"),
        EXPLOSION_3("explosion_3"),
        EXPLOSION_4("explosion_4"),
        EXPLOSION_5("explosion_5"),
        HIT_1("hit_1"),
        HIT_2("hit_2"),
        HIT_3("hit_3"),
        POWERUP_1("powerup_1"),
        POWERUP_2("powerup_2"),
        POWERUP_3("powerup_3"),
        SELECT_1("select_1"),
        SELECT_2("select_2"),
        SHOOT_1("shoot_1"),
        SHOOT_2("shoot_2");

        private final String audioFile;

        private Clip clip;

        Sounds(String name) {
            this.audioFile = PATH + name + ".wav";
        }

        public AudioInputStream getAudioStream() {
            return FilesUtils.getAudioStream(this.audioFile);
        }

        public void play() {
            if (this.clip == null) {
                var audioStream = this.getAudioStream();
                try {
                    AudioFormat format = audioStream.getFormat();

                    DataLine.Info info = new DataLine.Info(Clip.class, format);

                    Clip audioClip = (Clip) AudioSystem.getLine(info);

                    audioClip.addLineListener(this);

                    audioClip.open(audioStream);

                    audioClip.start();
                } catch (LineUnavailableException | IOException ignored) {
                }
            }
        }

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                this.clip.close();
                this.clip = null;
            }
        }
    }
}
