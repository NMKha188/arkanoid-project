package arkanoid.source.code.sound;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class Sound {

    private static final String PADDLE_HIT = "/arkanoid/resources/sounds/paddle_hit.wav";
    private static final String LOSE_LIFE = "/arkanoid/resources/sounds/gamedie.mp3";
    private static final String THEME_SOUND = "/arkanoid/resources/sounds/theme.mp3";
    private static final String EXPLOSION = "/arkanoid/resources/sounds/explode.mp3";
    private static final String BRICK_HIT = "/arkanoid/resources/sounds/brick_hit.mp3";
    private static final String LIGHTNING = "/arkanoid/resources/sounds/lightning.mp3";
    private static final String POWERUP = "/arkanoid/resources/sounds/powerup.mp3";
    private static final String WIN = "/arkanoid/resources/sounds/win.mp3";

    private static final AudioClip themeSound = loadSound(THEME_SOUND);
    private static final AudioClip paddleHitSound = loadSound(PADDLE_HIT);
    private static final AudioClip brickHitSound = loadSound(BRICK_HIT);
    private static final AudioClip loseLifeSound = loadSound(LOSE_LIFE);
    private static final AudioClip explosionSound = loadSound(EXPLOSION);
    private static final AudioClip powerUpSound = loadSound(POWERUP);
    private static final AudioClip winSound = loadSound(WIN);
    private static final AudioClip lightningSound = loadSound(LIGHTNING);

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
        if(brickHitSound != null) {
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

    public static void playExplosion() {
        if (explosionSound != null) {
            explosionSound.play();
        }
    }

    public static void playLightning() {
        if (lightningSound != null) {
            lightningSound.play();
        }
    }

    public static void playPowerUp() {
        if(powerUpSound != null) {
            powerUpSound.play();
        }
    }

    public static void playWin() {
        if(winSound != null) {
            winSound.play();
        }
    }
}