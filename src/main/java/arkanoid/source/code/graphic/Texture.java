package arkanoid.source.code.graphic;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.InputStream;

public class Texture {
    private static Image backgroundImage;

    private static Image topBorderImage;
    private static Image[] downBorderImages = new Image[14];
    private static Timeline borderAnimation;
    private static Image leftBorderImage;
    private static Image rightBorderImage;

    private static Image paddleImage;
    private static Image normalBallImage;
    private static Image explosiveBallImage;
    private static Image[] brickImages = new Image[10];
    private static Image[] explosionImages = new Image[8];
    private static Image[] lightingImages = new Image[12];

    private static Image expandPaddlePowerUPImage;
    private static Image speedUpPaddlePowerUpImage;
    private static Image slowBallPowerUpImage;
    private static Image explosiveBallPowerUpImage;
    private static Image tripleBallPowerUpImage;
    private static Image livePowerUpImage;
    public enum PowerUpType {
        EXPAND,
        SPEED_UP,
        EXPLOSIVEBALL,
        SLOWBALL,
        TRIPLEBALL,
        LIVE
    }

    static {
        try {
            backgroundImage = loadImage("/arkanoid/resources/background.png");

            paddleImage = loadImage("/arkanoid/resources/paddle/paddle.png");

            normalBallImage = loadImage("/arkanoid/resources/ball/ball.png");
            explosiveBallImage = loadImage("/arkanoid/resources/ball/explosiveball.png");

            brickImages[0] = null;
            brickImages[1] = loadImage("/arkanoid/resources/brick/brick1.png");
            brickImages[2] = loadImage("/arkanoid/resources/brick/brick2.png");
            brickImages[3] = loadImage("/arkanoid/resources/brick/brick3.png");
            brickImages[4] = loadImage("/arkanoid/resources/brick/brick4.png");
            brickImages[5] = loadImage("/arkanoid/resources/brick/brick5.png");
            brickImages[6] = loadImage("/arkanoid/resources/brick/brick6.png");
            brickImages[7] = loadImage("/arkanoid/resources/brick/brick7.png");
            brickImages[8] = loadImage("/arkanoid/resources/brick/brick8.png");
            brickImages[9] = loadImage("/arkanoid/resources/brick/brick9.png");

            expandPaddlePowerUPImage = loadImage("/arkanoid/resources/powerup/powerup_expandpaddle.png");
            speedUpPaddlePowerUpImage = loadImage("/arkanoid/resources/powerup/powerup_speedpaddle.png");
            explosiveBallPowerUpImage = loadImage("/arkanoid/resources/powerup/powerup_explosiveball.png");
            slowBallPowerUpImage = loadImage("/arkanoid/resources/powerup/powerup_slowball.png");
            tripleBallPowerUpImage = loadImage("/arkanoid/resources/powerup/powerup_tripleball.png");
            livePowerUpImage = loadImage("/arkanoid/resources/powerup/powerup_live.png");

            topBorderImage = loadImage("/arkanoid/resources/border/topRec.png");
            leftBorderImage = loadImage("/arkanoid/resources/border/leftRec.png");
            rightBorderImage = loadImage("/arkanoid/resources/border/rightRec.png");
            for (int i = 0; i < downBorderImages.length; i++) {
                String path = "/arkanoid/resources/border/downRec" + (i + 1) + ".png";
                downBorderImages[i] = loadImage(path);
            }
            for (int i = 0; i < explosionImages.length; i++) {
                String path = "/arkanoid/resources/explosionsprites/explosion" + (i + 1) + ".png";
                explosionImages[i] = loadImage(path);
            }
            for (int i = 0; i < lightingImages.length; i++) {
                String path = "/arkanoid/resources/lightingsprites/lighting" + (i + 1) + ".png";
                lightingImages[i] = loadImage(path);
            }
        } catch (Exception e) {
            System.err.println("Error loading textures resources");
            e.printStackTrace();
        }
    }

    private static Image loadImage(String path) {
        InputStream stream = InGameLogic.class.getResourceAsStream(path);
        if (stream != null) {
            return new Image(stream);
        } else {
            System.err.println("Error while loading " + path);
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

    public static void applyTextureToTopRec(Rectangle topRec) {
        if (topBorderImage != null) {
            topRec.setFill(new ImagePattern(topBorderImage));
        }
    }

    public static void applyTextureToLeftRec(Rectangle leftRec) {
        if (leftBorderImage != null) {
            leftRec.setFill(new ImagePattern(leftBorderImage));
        }
    }

    public static void applyTextureToRightRec(Rectangle rightRec) {
        if (rightBorderImage != null) {
            rightRec.setFill(new ImagePattern(rightBorderImage));
        }
    }

    public static void playLightingAnimation(double x, double y) {
        if (lightingImages == null || lightingImages[0] == null) {
            System.err.println("Lighting animation frames not loaded!");
            return;
        }

        double frameWidth = 150;
        double frameHeight = 150;

        Rectangle lightingRect = new Rectangle(frameWidth, frameHeight);

        lightingRect.setX(x - frameWidth / 2);
        lightingRect.setY(y - frameHeight / 2);

        lightingRect.setFill(new ImagePattern(lightingImages[0]));

        InGameLogic.getRoot().getChildren().add(lightingRect);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        double frameDuration = 80;

        for (int i = 0; i < lightingImages.length; i++) {
            final int frameIndex = i;
            KeyFrame kf = new KeyFrame(
                    Duration.millis(frameDuration * (i + 1)),
                    e -> {
                        if (lightingRect != null && lightingRect.getScene() != null) {
                            lightingRect.setFill(new ImagePattern(lightingImages[frameIndex]));
                        }
                    }
            );
            timeline.getKeyFrames().add(kf);
        }

        timeline.setOnFinished(e -> InGameLogic.getRoot().getChildren().remove(lightingRect));

        timeline.play();
    }

    public static void playExplosionAnimation(double x, double y) {
        if (explosionImages == null || explosionImages[0] == null) {
            System.err.println("Explosion animation frames not loaded!");
            return;
        }

        double frameWidth = 150;
        double frameHeight = 150;

        Rectangle explosionRect = new Rectangle(frameWidth, frameHeight);

        explosionRect.setX(x - frameWidth / 2);
        explosionRect.setY(y - frameHeight / 2);

        explosionRect.setFill(new ImagePattern(explosionImages[0]));

        InGameLogic.getRoot().getChildren().add(explosionRect);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        double frameDuration = 80;

        for (int i = 0; i < explosionImages.length; i++) {
            final int frameIndex = i;
            KeyFrame kf = new KeyFrame(
                    Duration.millis(frameDuration * (i + 1)),
                    e -> {
                        if (explosionRect != null && explosionRect.getScene() != null) {
                            explosionRect.setFill(new ImagePattern(explosionImages[frameIndex]));
                        }
                    }
            );
            timeline.getKeyFrames().add(kf);
        }

        timeline.setOnFinished(e -> InGameLogic.getRoot().getChildren().remove(explosionRect));

        timeline.play();
    }

    // downRec animation
    public static void applyAndPlayAnimation(Rectangle borderShape) {
        if (downBorderImages == null || downBorderImages[0] == null) {
            System.err.println("DownRec animation frames not loaded!");
            return;
        }

        if (borderAnimation != null) {
            borderAnimation.stop();
        }

        borderShape.setFill(new ImagePattern(downBorderImages[0]));

        borderAnimation = new Timeline();
        borderAnimation.setCycleCount(Animation.INDEFINITE);
        double frameDuration = 150;

        for (int i = 0; i < downBorderImages.length; i++) {
            final int frameIndex = i;
            KeyFrame kf = new KeyFrame(
                    Duration.millis(frameDuration * (i + 1)),
                    e -> {
                        if (borderShape != null && borderShape.getScene() != null) {
                            borderShape.setFill(new ImagePattern(downBorderImages[frameIndex]));
                        }
                    }
            );
            borderAnimation.getKeyFrames().add(kf);
        }

        borderAnimation.play();
    }

    // stop downRec animation
    public static void stopAnimation() {
        if (borderAnimation != null) {
            borderAnimation.stop();
            borderAnimation = null;
        }
    }

    public static void applyTextureToPaddle(Rectangle paddleShape) {
        if (paddleImage != null) {
            paddleShape.setFill(new ImagePattern(paddleImage));
        }
    }

    public static void applyTextureToBall(Circle ballShape) {
        if (normalBallImage != null) {
            ballShape.setFill(new ImagePattern(normalBallImage));
        }
    }

    public static void applyExplosiveTextureToBall(Circle ballShape) {
        if (explosiveBallImage != null) {
            ballShape.setFill(new ImagePattern(explosiveBallImage));
        }
    }

    public static void applyTextureToBrick(Rectangle brickShape, int index) {
        if (index > 0 && index < brickImages.length && brickImages[index] != null) {
            brickShape.setFill(new ImagePattern(brickImages[index]));
        }
    }

    public static void applyTextureToPowerUp(Rectangle powerUpShape, PowerUpType type) {
        Image texture = null;

        switch (type) {
            case EXPAND:
                texture = expandPaddlePowerUPImage;
                break;
            case SPEED_UP:
                texture = speedUpPaddlePowerUpImage;
                break;
            case EXPLOSIVEBALL:
                texture = explosiveBallPowerUpImage;
                break;
            case SLOWBALL:
                texture = slowBallPowerUpImage;
                break;
            case TRIPLEBALL:
                texture = tripleBallPowerUpImage;
                break;
            case LIVE:
                texture = livePowerUpImage;
                break;
        }
        if (texture != null) {
            powerUpShape.setFill(new ImagePattern(texture));
        }
    }
}