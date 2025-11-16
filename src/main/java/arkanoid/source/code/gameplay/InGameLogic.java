package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gamecontroller.SceneController;
import arkanoid.source.code.gameplay.gameobject.ball.BallList;
import arkanoid.source.code.gameplay.gameobject.brick.BrickSet;
import arkanoid.source.code.gameplay.gameobject.brick.Map;
import arkanoid.source.code.gameplay.gamecommand.*;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;
import arkanoid.source.code.gameplay.gameobject.powerup.PowerUpList;
import arkanoid.source.code.gameplay.gamestatus.InGameStatus;
import arkanoid.source.code.gameplay.gamestatus.SaveGame;
import arkanoid.source.code.sound.Sound;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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

    private static final Paddle paddle = new Paddle();
    private static final BallList ballList = new BallList();
    private static BrickSet brickSet;
    private static final PowerUpList powerUpList = new PowerUpList();

    private static GameLogicThread gameLogicThread;

    static {
        Texture.applyBackground(gameRoot);

        Sound.playThemeSound();

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

    public static void setMovingLeft(boolean moving) {
        movingLeft = moving;
    }

    public static void setMovingRight(boolean moving) {
        movingRight = moving;
    }

    public static boolean isMovingRight() {
        return movingRight;
    }

    public static Pane getRoot() {
        return gameRoot;
    }

    public static Scene getGameScene() {
        return gameScene;
    }

    public static GameLogicThread getGameLogicThread() {
        return gameLogicThread;
    }

    public static void setGameLogicThread(GameLogicThread newGameLogicThread) {
        gameLogicThread = newGameLogicThread;
    }

    public synchronized static Paddle getPaddle() {
        return paddle;
    }

    public synchronized static BallList getBallList() {
        return ballList;
    }

    public synchronized static BrickSet getBrickSet() {
        return brickSet;
    }

    public synchronized static PowerUpList getPowerUpList() {
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
        GameCommand releasedBall = new ReleasedBallCommand(ballList);
        GameCommand moveLeft = new MoveLeftCommand();
        GameCommand stopMoveLeft = new StopMoveLeftCommand();
        GameCommand moveRight = new MoveRightCommand();
        GameCommand stopMoveRight = new StopMoveRightCommand();
        GameCommand pauseGame = new PauseGameCommand();

        InputHandler inputHandler = new InputHandler();

        inputHandler.bindKey(KeyCode.SPACE, releasedBall, null);
        inputHandler.bindKey(KeyCode.LEFT, moveLeft, stopMoveLeft);
        inputHandler.bindKey(KeyCode.RIGHT, moveRight, stopMoveRight);
        inputHandler.bindKey(KeyCode.P, pauseGame, null);

        inputHandler.setupInput(gameScene);
    }

    public static Scene createGameScene(Stage primaryStage) {
        loadMap();

        reset();

        handleInGameKeyInput();

        if (gameTimer != null) {
            gameTimer.stop();
        }

        if (gameLogicThread != null) {
            gameLogicThread.stopThread();
            gameLogicThread = null;
        }

        gameLogicThread = new GameLogicThread(paddle, ballList, brickSet, powerUpList);
        new Thread(gameLogicThread).start();

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(brickSet.isClear()) {
                    reset();
                    Sound.playWin();
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