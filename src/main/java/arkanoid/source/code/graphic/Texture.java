package arkanoid.source.code.graphic;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
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

import java.io.InputStream;

public class Texture {

    private static Image backgroundImage;
    private static Image paddleImage;
    private static Image ballImage;
    private static Image expandPaddleImage;
    private static Image speedUpPaddleImage;
    private static Image explosiveBallImage;
    private static Image bongnoImage;
    private static Image slowBallImage;
    private static Image tripleBallImage;
    private static Image liveImage;
    private static Image topRecImage;
    private static Image downRecImage;
    private static Image leftRecImage;
    private static Image rightRecImage;
    public enum PowerUpType {
        EXPAND,
        SPEED_UP,
        EXPLOSIVEBALL,
        SLOWBALL,
        TRIPLEBALL,
        LIVE

    }
    private static Image[] brickImages = new Image[8];

    public static void loadTextures() {
        try {
            backgroundImage = loadImage("/arkanoid/resources/background.png");
            paddleImage = loadImage("/arkanoid/resources/paddle/paddle.png");
            ballImage = loadImage("/arkanoid/resources/ball/ball.png");

            brickImages[0] = null;
            brickImages[1] = loadImage("/arkanoid/resources/brick/brick1.png");
            brickImages[2] = loadImage("/arkanoid/resources/brick/brick2.png");
            brickImages[3] = loadImage("/arkanoid/resources/brick/brick3.png");
            brickImages[4] = loadImage("/arkanoid/resources/brick/brick4.png");
            brickImages[5] = loadImage("/arkanoid/resources/brick/brick5.png");
            brickImages[6] = loadImage("/arkanoid/resources/brick/brick6.png");
            brickImages[7] = loadImage("/arkanoid/resources/brick/brick7.png");
            expandPaddleImage = loadImage("/arkanoid/resources/powerup/powerup_expandpaddle.png");
            speedUpPaddleImage = loadImage("/arkanoid/resources/powerup/powerup_speedpaddle.png");
            explosiveBallImage = loadImage("/arkanoid/resources/powerup/powerup_explosiveball.png");
            bongnoImage = loadImage("/arkanoid/resources/ball/explosiveball.png");
            slowBallImage = loadImage("/arkanoid/resources/powerup/powerup_slowball.png");
            tripleBallImage = loadImage("/arkanoid/resources/powerup/powerup_tripleball.png");
            liveImage = loadImage("/arkanoid/resources/powerup/powerup_live.png");
            topRecImage = loadImage("/arkanoid/resources/topRec.png");
            downRecImage = loadImage("/arkanoid/resources/downRec.png");
            leftRecImage = loadImage("/arkanoid/resources/leftRec.png");
            rightRecImage = loadImage("/arkanoid/resources/rightRec.png");

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

    public static void applyTextureToBall(Circle ballShape) {
        if (ballImage != null) {
            ballShape.setFill(new ImagePattern(ballImage));
        }
    }

    public static void applyExplosiveTextureToBall(Circle ballShape) {
        if (bongnoImage != null) {
            ballShape.setFill(new ImagePattern(bongnoImage));
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
    public static void applyTextureToTopRec(Rectangle topRec) {
        if (topRecImage != null) {
            topRec.setFill(new ImagePattern(topRecImage));
        }
    }

    public static void applyTextureToDownRec(Rectangle downRec) {
        if (downRecImage != null) {
            downRec.setFill(new ImagePattern(downRecImage));
        }
    }

    public static void applyTextureToLeftRec(Rectangle leftRec) {
        if (leftRecImage != null) {
            leftRec.setFill(new ImagePattern(leftRecImage));
        }
    }

    public static void applyTextureToRightRec(Rectangle rightRec) {
        if (rightRecImage != null) {
            rightRec.setFill(new ImagePattern(rightRecImage));
        }
    }
    public static Image getExpandPaddleImage() {
        return expandPaddleImage;
    }

    public static Image getSpeedUpPaddleImage() {
        return speedUpPaddleImage;
    }

    public static Image getExplosiveBallImage() {
        return explosiveBallImage;
    }
    public static Image getSlowBallImage() {
        return slowBallImage;
    }

    public static Image getTripleBallImage() {
        return tripleBallImage;
    }

    public static Image getHealthImage() {
        return liveImage;
    }
    public static void applyTextureToPowerUp(Rectangle powerUpShape, PowerUpType type) {
        Image texture = null;

        switch (type) {
            case EXPAND:
                texture = expandPaddleImage;
                break;
            case SPEED_UP:
                texture = speedUpPaddleImage;
                break;
            case EXPLOSIVEBALL:
                texture = explosiveBallImage;
                break;
            case SLOWBALL:
                texture = slowBallImage;
                break;
            case TRIPLEBALL:
                texture = tripleBallImage;
                break;
            case LIVE:
                texture = liveImage;
                break;
        }
        if (texture != null) {
            powerUpShape.setFill(new ImagePattern(texture));
        }
    }
}