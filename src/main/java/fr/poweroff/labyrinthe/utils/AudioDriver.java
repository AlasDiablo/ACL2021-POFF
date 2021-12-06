package fr.poweroff.labyrinthe.utils;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import fr.poweroff.labyrinthe.model.PacmanGame;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioDriver {
    private static final String SOUNDS_PATH = "assets/sounds/";
    private static final String MUSIC_PATH = "assets/music/";

    private static final Minim MINIM = new Minim(FilesUtils.getMinimFileSystem());
    private static Music currentMusic;

    public static void init() {
        playPowerup();
    }

    private static void playSounds(Sounds sounds) {
        sounds.play();
    }

    public static void playCoin() {
        var random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.COIN_1;
        else sounds = Sounds.COIN_2;
        playSounds(sounds);
    }

    public static void playExplosion() {
        var random_int = PacmanGame.RANDOM.nextInt(5);
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
        var random_int = PacmanGame.RANDOM.nextInt(3);
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
        var random_int = PacmanGame.RANDOM.nextInt(3);
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
        var random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.SELECT_1;
        else sounds = Sounds.SELECT_2;
        playSounds(sounds);
    }

    public static void playShoot() {
        var random_int = PacmanGame.RANDOM.nextInt(2);
        Sounds sounds;
        if (random_int == 0) sounds = Sounds.SHOOT_1;
        else sounds = Sounds.SHOOT_2;
        playSounds(sounds);
    }

    public static void playMusic(Music music) {
        currentMusic = music;
        music.play();
    }

    public static void stopMusic() {
        if (currentMusic != null) currentMusic.stop();
    }

    public static Music getCurrentMusic() {
        return currentMusic;
    }

    public enum Music {
        AMIGA("amiga"),
        IN_MOTION("in_motion");

        private final String audioFile;

        private AudioPlayer player;

        Music(String name) {
            this.audioFile = MUSIC_PATH + name + ".mp3";
        }

        public void play() {
            if (this.player == null) {
                this.player = MINIM.loadFile(audioFile);
            }
            this.player.play();
            this.player.loop();
        }

        public void stop() {
            this.player.rewind();
            this.player.pause();
        }
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
            this.audioFile = SOUNDS_PATH + name + ".wav";
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
                    this.clip = (Clip) AudioSystem.getLine(info);
                    this.clip.addLineListener(this);
                    this.clip.open(audioStream);
                    this.clip.start();
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
