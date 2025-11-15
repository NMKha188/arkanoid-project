package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gamecontroller.PauseScreen;
import arkanoid.source.code.gamecontroller.SceneController;
import arkanoid.source.code.gamecontroller.ScreenFactory;
import arkanoid.source.code.gameplay.ball.BallList;
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

    public static AnimationTimer gameTimer;
    public static boolean pausing = false;

    private static final Paddle paddle = new Paddle();
    private static final BallList ballList = new BallList();
    private static BrickSet brickSet;
    private static final PowerUpList powerUpList = new PowerUpList();

    public static GameLogicThread gameLogicThread;

    static {
        Texture.applyBackground(gameRoot);

        paddle.addShapeToGameRoot();

        ballList.addShapeToGameRoot();
        ballList.addDirectionLineToGameRoot();

        InGameStatus.applyBorderTextures();
        InGameStatus.startDownRecAnimation();
        InGameStatus.addGroupToGameRoot();
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

    public static GameLogicThread getGameLogicThread() {
        return gameLogicThread;
    }

    public static void setGameLogicThread(GameLogicThread newGameLogicThread) {
        gameLogicThread = newGameLogicThread;
    }

    public static Paddle getPaddle() {
        return paddle;
    }

    public static BallList getBallList() {
        return ballList;
    }

    public static BrickSet getBrickSet() {
        return brickSet;
    }

    public static PowerUpList getPowerUpList() {
        return powerUpList;
    }

    public static void loadMap() {
        if (brickSet != null) {
            brickSet.removeShapeFromGameRoot();
        }

        brickSet = Map.getMap(InGameStatus.getLevel());
        brickSet.addShapeToGameRoot();
    }

    private static void handleInGameKeyInput() {
        // handle key press
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

        // Handle key release
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
                    ballList.hideDirectionLine();
                }
                case P -> {
                    stopGame();
                    ScreenFactory.createScreen(ScreenFactory.ScreenType.PAUSE,
                            (Stage) gameScene.getWindow());
                }
                default -> {
                }
            }
        });
    }

    public static Scene createGameScene(Stage primaryStage) {
        loadMap();
        reset();
        handleInGameKeyInput();

        if (gameTimer != null) {
            gameTimer.stop();
            gameTimer = null;
        }

        if (gameLogicThread != null) {
            gameLogicThread.stopThread();
            gameLogicThread = null;
        }

        // Create and start new game logic thread
        gameLogicThread = new GameLogicThread(paddle, ballList, brickSet, powerUpList);
        new Thread(gameLogicThread).start();

        // Create and start new game timer
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
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
        if (gameLogicThread != null) {
            gameLogicThread.stopThread();
            gameLogicThread = null;
        }
        InGameStatus.stopDownRecAnimation();
    }

    public static void reset() {
        movingLeft = false;
        movingRight = false;
        paddle.reset();
        ballList.reset();
        brickSet.reset();
        powerUpList.reset();
        InGameStatus.startDownRecAnimation();
    }
}