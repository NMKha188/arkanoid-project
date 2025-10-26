package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class Texture {

    private static Image backgroundImage;
    private static Image paddleImage;
    private static Image ballImage;

    private static Image[] brickImages = new Image[8];

    public static void loadTextures() {
        try {
            backgroundImage = loadImage("/arkanoid/resources/background.png");
            paddleImage = loadImage("/arkanoid/resources/paddle.png");
            ballImage = loadImage("/arkanoid/resources/ball.png");

            brickImages[0] = null;
            brickImages[1] = loadImage("/arkanoid/resources/brick1.png");
            brickImages[2] = loadImage("/arkanoid/resources/brick2.png");
            brickImages[3] = loadImage("/arkanoid/resources/brick3.png");
            brickImages[4] = loadImage("/arkanoid/resources/brick4.png");
            brickImages[5] = loadImage("/arkanoid/resources/brick5.png");
            brickImages[6] = loadImage("/arkanoid/resources/brick6.png");
            brickImages[7] = loadImage("/arkanoid/resources/brick7.png");

        } catch (Exception e) {
            System.err.println("Lỗi nghiêm trọng khi tải textures:");
            e.printStackTrace();
        }
    }

    private static Image loadImage(String path) {
        InputStream stream = InGameLogic.class.getResourceAsStream(path);
        if (stream != null) {
            return new Image(stream);
        } else {
            System.err.println("Không thể tải resource: " + path);
            return null;
        }
    }

    public static void applyBackground(Pane rootPane) {
        if (backgroundImage != null) {
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(InGameLogic.getGameplayScreenWidth() + Config.EXTRA, InGameLogic.getGameplayScreenHeight() + Config.EXTRA / 2, false, false, false, false)
            );
            rootPane.setBackground(new Background(bgImage));
        }
    }

    public static void applyTextureToBall(Circle ballShape) {
        if (ballImage != null) {
            ballShape.setFill(new ImagePattern(ballImage));
        }
    }

    public static void applyTextureToPaddle(Rectangle paddleShape) {
        if (paddleImage != null) {
            paddleShape.setFill(new ImagePattern(paddleImage));
        }
    }

    public static void applyTextureToBrick(Rectangle brickShape, int index) {
        if (index > 0 && index < brickImages.length && brickImages[index] != null) {
            brickShape.setFill(new ImagePattern(brickImages[index]));
        }
    }
}