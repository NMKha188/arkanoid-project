package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import arkanoid.source.code.graphic.Texture;

public class InGameLogic {
    // Screen size
    private static final double GAMEPLAY_SCREEN_WIDTH = Config.GAMEPLAY_SCREEN_WIDTH;
    private static final double GAMEPLAY_SCREEN_HEIGHT = Config.GAMEPLAY_SCREEN_HEIGHT;
    // game pane and scene
    private static final Pane gameRoot;
    private static final Scene gameScene;
    // Check key press
    private static boolean movingLeft = false;
    private static boolean movingRight = false;
    // paddle
    private static final Paddle paddle;
    // ball list
    private static final BallList ballList;
    // brickSet
    private static final BrickSet brickSet;
    // power ups
    private static final PowerUpList powerUpList;

    static {
        gameRoot = new Pane();
        gameScene = new Scene(gameRoot, GAMEPLAY_SCREEN_WIDTH + Config.EXTRA, GAMEPLAY_SCREEN_HEIGHT + Config.EXTRA / 2);

        Texture.loadTextures();

        Texture.applyBackground(gameRoot);

        paddle = new Paddle();
        paddle.addShapeToGameRoot();

        ballList = new BallList();
        ballList.addShapeToGameRoot();

        brickSet = new BrickSet();
        brickSet.readData(Config.MAP1_DATA_PATH);
        brickSet.addShapeToGameRoot();

        powerUpList = new PowerUpList();

        gameRoot.getChildren().add(InGameStatus.getGroup());
    }

    // getter setter BEGIN
    public static double getGameplayScreenWidth() {
        return GAMEPLAY_SCREEN_WIDTH;
    }

    public static double getGameplayScreenHeight() {
        return GAMEPLAY_SCREEN_HEIGHT;
    }

    public static boolean isMovingLeft() {
        return movingLeft;
    }

    public static boolean isMovingRight() {
        return movingRight;
    }

    public static Pane getRoot() {
        return gameRoot;
    }
    // getter setter END

    private void handleKeyInput() {
        // handle key press
        gameScene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:
                    movingLeft = true;
                    break;
                case RIGHT:
                    movingRight = true;
                    break;
                default:
                    // blank
            }
        });

        // Handle key release
        gameScene.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case LEFT:
                    movingLeft = false;
                    break;
                case RIGHT:
                    movingRight = false;
                    break;
                case SPACE:
                    ballList.setReleased(true);
                default:
                    // blank
            }
        });
    }

    public Scene createGameScene(Stage primaryStage) {
        this.handleKeyInput();

        // animation control
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.update();
                ballList.update(paddle, brickSet, powerUpList);
                brickSet.update();
                powerUpList.update(paddle, ballList, brickSet);
            }
        }.start();

        return gameScene;
    }
}