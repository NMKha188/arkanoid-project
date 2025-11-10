package arkanoid.source.code.graphic;

import javafx.scene.media.AudioClip;
import java.io.File;

public class Sound {
    private static final String BRICK_HIT_PATH = "C:/arkanoid-project/src/main/resources/arkanoid/resources/sounds/hit.mp3";
    private static final String PADDLE_HIT_PATH = "C:/arkanoid-project/src/main/resources/arkanoid/resources/sounds/hit.mp3";
    private static final String LOSE_LIFE_PATH = "C:/arkanoid-project/src/main/resources/arkanoid/resources/sounds/hit.mp3";

    private static final AudioClip brickHitSound = loadSound(BRICK_HIT_PATH);
    private static final AudioClip paddleHitSound = loadSound(PADDLE_HIT_PATH);
    private static final AudioClip loseLifeSound = loadSound(LOSE_LIFE_PATH);

    private static AudioClip loadSound(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return new AudioClip(file.toURI().toString());
            } else {
                System.err.println("Không tìm thấy tệp âm thanh: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải âm thanh: " + path);
            e.printStackTrace();
            return null;
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