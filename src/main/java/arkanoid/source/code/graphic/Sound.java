package arkanoid.source.code.graphic;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class Sound {

    private static final String BRICK_HIT_PATH = "/arkanoid/resources/sounds/hit.mp3";
    private static final String PADDLE_HIT_PATH = "/arkanoid/resources/sounds/hit.mp3";
    private static final String LOSE_LIFE_PATH = "/arkanoid/resources/sounds/die.mp3";
    private static final String THEME_SOUND = "/arkanoid/resources/sounds/theme_song.mp3";

    private static final AudioClip themeSound = loadSound(THEME_SOUND);
    private static final AudioClip brickHitSound = loadSound(BRICK_HIT_PATH);
    private static final AudioClip paddleHitSound = loadSound(PADDLE_HIT_PATH);
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
            themeSound.play();
        }
    }

    public static void playBrickHit() {
        if (brickHitSound != null) {
            brickHitSound.play();
        }
    }
    public static void playPaddleHit() {
        if (paddleHitSound != null) {
            paddleHitSound.play();
        }
    }
    public static void playLoseLife() {
        if (loseLifeSound != null) {
            loseLifeSound.play();
        }
    }
}