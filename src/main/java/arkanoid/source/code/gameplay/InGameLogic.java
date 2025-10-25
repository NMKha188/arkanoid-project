package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InGameLogic {
    // Screen size
    private static final double GAMEPLAY_SCREEN_WIDTH = Config.GAMEPLAY_SCREEN_WIDTH;
    private static final double GAMEPLAY_SCREEN_HEIGHT = Config.GAMEPLAY_SCREEN_HEIGHT;
    // Check key press
    private static boolean movingLeft = false;
    private static boolean movingRight = false;
    // paddle
    private static final Paddle paddle = new Paddle();
    // ball
    private static final Ball ball = new Ball();
    // brickSet
    private static final BrickSet brickSet = new BrickSet();
    String dataPath = "/arkanoid/resources/map1.txt";
    // power ups
    private static final PowerUpList powerUpList = new PowerUpList();
    // game pane and scene
    private static final Pane gameRoot = new Pane();
    private static final Scene gameScene = new Scene(gameRoot, GAMEPLAY_SCREEN_WIDTH + Config.EXTRA, GAMEPLAY_SCREEN_HEIGHT + Config.EXTRA / 2);

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
                    ball.setReleasedState(true);
                default:
                    // blank
            }
        });
    }

    public Scene createGameScene(Stage primaryStage) {
        brickSet.readData(dataPath);

        gameRoot.getChildren().add(paddle.getShape());
        gameRoot.getChildren().add(ball.getShape());
        gameRoot.getChildren().add(ball.getvelocityRepresentativeLine());
        for (int i = 0; i < brickSet.getBricksRow(); i++) {
            for (int j = 0; j < brickSet.getBricksPerRow(); j++) {
                if (brickSet.getOneBrickAt(i, j) != null) {
                    gameRoot.getChildren().add(brickSet.getOneBrickAt(i, j).getShape());
                }
            }
        }
        gameRoot.getChildren().add(InGameStatus.getGroup());

        this.handleKeyInput();

        // animation control
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.update();
                ball.update( paddle, brickSet, powerUpList);
                powerUpList.update(paddle, ball, brickSet);
            }
        }.start();

        return gameScene;
    }
}