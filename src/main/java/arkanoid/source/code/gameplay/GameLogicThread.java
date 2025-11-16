package arkanoid.source.code.gameplay;

import arkanoid.source.code.gameplay.gameobject.ball.BallList;
import arkanoid.source.code.gameplay.gameobject.brick.BrickSet;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;
import arkanoid.source.code.gameplay.gameobject.powerup.PowerUpList;
import javafx.application.Platform;

public class GameLogicThread implements Runnable {
    private volatile boolean running = true;
    private final long FPS = 60;
    private final long INTERVAL = 1000 / FPS;

    private final Paddle paddle;
    private final BallList ballList;
    private final BrickSet brickSet;
    private final PowerUpList powerUpList;

    public GameLogicThread(Paddle paddle, BallList ballList, BrickSet brickSet, PowerUpList powerUpList) {
        this.paddle = paddle;
        this.ballList = ballList;
        this.brickSet = brickSet;
        this.powerUpList = powerUpList;
    }

    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();

            paddle.updateLogic();
            ballList.updateLogic();
            brickSet.updateLogic();
            powerUpList.updateLogic();

            Platform.runLater(() -> {
                paddle.updateVisual();
                ballList.updateVisual();
                brickSet.updateVisual();
                powerUpList.updateVisual();
            });

            long timeSpent = System.currentTimeMillis() - startTime;
            long sleepTime = INTERVAL - timeSpent;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
