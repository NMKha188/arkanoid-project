package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gamecontroller.SceneController;
import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.gameplay.ball.LineBall;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.brick.Map;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import arkanoid.source.code.graphic.Texture;

public class InGameLogic {
    private static final double GAMEPLAY_SCREEN_WIDTH = Config.GAMEPLAY_SCREEN_WIDTH;
    private static final double GAMEPLAY_SCREEN_HEIGHT = Config.GAMEPLAY_SCREEN_HEIGHT;

    private static final Pane gameRoot = new Pane();
    private static final Scene gameScene = new Scene(gameRoot, GAMEPLAY_SCREEN_WIDTH + Config.EXTRA, GAMEPLAY_SCREEN_HEIGHT + Config.EXTRA / 2);

    private static boolean movingLeft = false;
    private static boolean movingRight = false;

    private static AnimationTimer gameTimer;

    private static final Paddle paddle = new Paddle();

    private static final BallList ballList = new BallList();

    private static BrickSet brickSet = Map.getMap(5);

    private static final PowerUpList powerUpList = new PowerUpList();

    static {
        Texture.applyBackground(gameRoot);

        paddle.addShapeToGameRoot();

        brickSet.addShapeToGameRoot();

        InGameStatus.applyBorderTextures();
        InGameStatus.startDownRecAnimation();
        InGameStatus.addGroupToGameRoot();

        ballList.addShapeToGameRoot();
        ballList.addVelocityRepresentativeLineToGameRoot();
    }

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

    private static void handleKeyInput() {
        gameScene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT -> {
                    movingLeft = true;
                }
                case RIGHT -> {
                    movingRight = true;
                }
                default -> {
                }
            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case LEFT -> {
                    movingLeft = false;
                }
                case RIGHT -> {
                    movingRight = false;
                }
                case SPACE -> {
                    ballList.setReleased(true);
                    ballList.hideVelocityRepresentativeLine();
                    LineBall.setVisibility(false);
                }
                default -> {
                }
            }
        });
    }

    public static Scene createGameScene(Stage primaryStage) {


        brickSet = Map.getMap(InGameStatus.getLevel());




        handleKeyInput();

        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.update();
                ballList.update(paddle, brickSet, powerUpList);
                brickSet.update(powerUpList);
                powerUpList.update(paddle, ballList, brickSet);
                if(brickSet.isClear()) {
                    InGameStatus.setNextMap();
                    SaveGame.saveGame();
                    System.out.println("Level Passed");
                    SceneController.completeLevel();
                }

            }
        };
        gameTimer.start();

        return gameScene;
    }

    public static void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
            gameTimer = null;
        }
        InGameStatus.stopDownRecAnimation();
    }

    public static void reset() {
        movingLeft = false;
        movingRight = false;
        paddle.reset();
        ballList.reset();
        brickSet.reset();
        powerUpList.reset(paddle, ballList);
        InGameStatus.startDownRecAnimation();
    }
}