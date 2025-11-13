package arkanoid.source.code.gameplay;

import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.application.Platform;

public class GameLogicThread implements Runnable {
    private volatile boolean running = true;
    private final long FPS = 60;
    private final long UPDATE_INTERVAL = 1000 / FPS;

    private Paddle paddle;
    private BallList ballList;
    private BrickSet brickSet;
    private PowerUpList powerUpList;

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
            /*ballList.update(paddle, brickSet, powerUpList);
            brickSet.update(powerUpList);
            powerUpList.update(paddle, ballList, brickSet);*/

            Platform.runLater(() -> {
                paddle.updateVisual();
            });

            long timeSpent = System.currentTimeMillis() - startTime;
            long sleepTime = UPDATE_INTERVAL - timeSpent;
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
