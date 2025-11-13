package arkanoid.source.code.graphic;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class Sound {

    private static final String HIT_PATH = "/arkanoid/resources/sounds/hit.wav";
    private static final String LOSE_LIFE_PATH = "/arkanoid/resources/sounds/die.wav";
    private static final String THEME_SOUND = "/arkanoid/resources/sounds/theme_song.mp3";

    private static final AudioClip themeSound = loadSound(THEME_SOUND);
    private static final AudioClip HitSound = loadSound(HIT_PATH);
    private static final AudioClip loseLifeSound = loadSound(LOSE_LIFE_PATH);

    private static AudioClip loadSound(String path) {
        try {
            URL resourceUrl = Sound.class.getResource(path);

            if (resourceUrl != null) {
                return new AudioClip(resourceUrl.toExternalForm());
            } else {
                System.err.println("Không tìm thấy tệp âm thanh trong classpath: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải âm thanh: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static void playThemeSound() {
        if(themeSound != null) {
            themeSound.setCycleCount(AudioClip.INDEFINITE);
            themeSound.play();
        }
    }

    public static void playBrickHit() {
        if (HitSound != null) {
            HitSound.play();
        }
    }
    public static void playHit() {
        if (HitSound != null) {
            HitSound.play();
        }
    }
    public static void playLoseLife() {
        if (loseLifeSound != null) {
            loseLifeSound.play();
        }
    }
}