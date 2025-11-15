package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.brick.Brick;
import javafx.application.Platform;

import java.time.Instant;
import java.util.ArrayList;

public class PowerUpList implements GameObject {
    private final ArrayList<PowerUp> powerUpList = new ArrayList<>();

    public void addShapeToGameRoot() {
    }

    public void removeShapeFromGameRoot() {
        for (PowerUp powerUp : powerUpList) {
            powerUp.removeShapeFromRoot();
        }
    }

    private void addPowerUpToList(PowerUp powerUp) {
        powerUpList.add(powerUp);
        Platform.runLater(() -> {
            powerUp.addShapeToRoot();
        });
    }

    private void removePowerUpFromList(int index) {
        PowerUp powerUp = powerUpList.get(index);
        if (powerUp == null) {
            return;
        }
        Platform.runLater(() -> {
            powerUp.removeShapeFromRoot();
        });
        powerUpList.remove(index);
    }

    // create power up based on probability when a brick is destroyed : Associated with brick, this method is called in getHit() method of Brick
    public void createPowerUp(Brick brick) {
        double x = brick.getX() + (brick.getWidth() - Config.POWER_UP_WIDTH) / 2;
        double y = brick.getY() + (brick.getHeight() - Config.POWER_UP_HEIGHT) / 2;

        switch ((int) (Math.random() * 6)) {
            case 0 -> {
                // expand paddle
                if ((int) (Math.random() * 100) <= Config.EXPAND_PADDLE_PROBABILITY) {
                    PowerUp expandPaddle = new ExpandPaddle(x, y);
                    this.addPowerUpToList(expandPaddle);
                }
            }
            case 1 -> {
                // speed up paddle
                if ((int) (Math.random() * 100) <= Config.SPEED_UP_PADDLE_PROBABILITY) {
                    PowerUp speedUpPaddle = new SpeedUpPaddle(x, y);
                    this.addPowerUpToList(speedUpPaddle);
                }
            }
            case 2 -> {
                // slow ball
                if ((int) (Math.random() * 100) <= Config.SLOW_BALL_PROBABILITY) {
                    PowerUp slowBall = new SlowBall(x, y);
                    this.addPowerUpToList(slowBall);
                }
            }
            case 3 -> {
                // explosive ball
                if ((int) (Math.random() * 100) <= Config.EXPLOSIVE_BALL_PROBABILITY) {
                    PowerUp explosiveBall = new ExplosiveBall(x, y);
                    this.addPowerUpToList(explosiveBall);
                }
            }
            case 4 -> {
                // triple ball
                if ((int) (Math.random() * 100) <= Config.TRIPLE_BALL_PROBABILITY) {
                    PowerUp tripleBall = new TripleBall(x, y);
                    this.addPowerUpToList(tripleBall);
                }
            }
            case 5 -> {
                // life
                if ((int) (Math.random() * 100) <= Config.LIVE_PROBABILITY) {
                    PowerUp live = new Live(x, y);
                    this.addPowerUpToList(live);
                }
            }
            default -> {
            }
        }
    }

    public void updateLogic() {
        Paddle paddle = InGameLogic.getPaddle();
        BallList ballList = InGameLogic.getBallList();

        for (int i = 0; i < powerUpList.size(); i++) {
            PowerUp powerUp = powerUpList.get(i);

            powerUp.updateLogic();

            if (powerUp.caughtByPaddle(paddle)) {
                powerUp.setEffectStartTime(Instant.now());
                Platform.runLater(() -> {
                    powerUp.removeShapeFromGameRoot();
                });
            }

            if (powerUp.isFallenToBottom()) {
                this.removePowerUpFromList(i--);
            }

            if (powerUp.getEffectStartTime() != null && (powerUp instanceof Live || powerUp instanceof TripleBall)) {
                powerUp.applyEffect(ballList);
                this.removePowerUpFromList(i--);
            } else {
                if (powerUp.onDuration()) {
                    if (powerUp instanceof ExpandPaddle || powerUp instanceof SpeedUpPaddle) {
                        powerUp.applyEffect(paddle);
                    }
                    if (powerUp instanceof SlowBall || powerUp instanceof ExplosiveBall) {
                        powerUp.applyEffect(ballList);
                    }
                }

                if (powerUp.runOutOfDuration()) {
                    if (powerUp instanceof ExpandPaddle || powerUp instanceof SpeedUpPaddle) {
                        powerUp.removeEffect(paddle);
                    }
                    if (powerUp instanceof SlowBall || powerUp instanceof ExplosiveBall) {
                        powerUp.removeEffect(ballList);
                    }
                    this.removePowerUpFromList(i--);
                }
            }
        }
    }

    public void updateVisual() {
        for (PowerUp powerUp : powerUpList) {
            powerUp.updateVisual();
        }
    }

    public void reset() {
        Paddle paddle = InGameLogic.getPaddle();
        BallList ballList = InGameLogic.getBallList();

        for (PowerUp powerUp : powerUpList) {
            if (powerUp.onDuration()) {
                if (powerUp instanceof ExpandPaddle || powerUp instanceof SpeedUpPaddle) {
                    powerUp.removeEffect(paddle);
                }
                if (powerUp instanceof SlowBall || powerUp instanceof ExplosiveBall) {
                    powerUp.removeEffect(ballList);
                }
            }
        }

        Platform.runLater(() -> {
            this.removeShapeFromGameRoot();
            powerUpList.clear();
        });
    }
}