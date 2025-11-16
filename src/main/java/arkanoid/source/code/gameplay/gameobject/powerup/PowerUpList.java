package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.gameplay.gameobject.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.gameobject.ball.BallList;
import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;
import arkanoid.source.code.gameplay.gameobject.brick.Brick;
import arkanoid.source.code.sound.Sound;
import javafx.application.Platform;

import java.time.Instant;
import java.util.ArrayList;

public class PowerUpList implements GameObject {
    private final ArrayList<PowerUp> powerUpList = new ArrayList<>();

    public void addShapeToGameRoot() {
    }

    public void removeShapeFromGameRoot() {
        for (PowerUp powerUp : powerUpList) {
            powerUp.removeShapeFromGameRoot();
        }
    }

    private void addPowerUpToList(PowerUp powerUp) {
        powerUpList.add(powerUp);
        Platform.runLater(() -> {
            powerUp.addShapeToGameRoot();
        });
    }

    private void removePowerUpFromList(int index) {
        PowerUp powerUp = powerUpList.get(index);
        if (powerUp == null) {
            return;
        }
        Platform.runLater(() -> {
            powerUp.removeShapeFromGameRoot();
        });
        powerUpList.remove(index);
    }

    // create power up based on probability when a brick is destroyed : Associated with brick, this method is called in getHit() method of Brick
    public void createPowerUp(Brick brick) {
        double x = brick.getX() + (brick.getWidth() - Config.POWER_UP_WIDTH) / 2;
        double y = brick.getY() + (brick.getHeight() - Config.POWER_UP_HEIGHT) / 2;

        PowerUp powerUp = PowerUpFactory.createRandomPowerUp(x, y);

        if (powerUp != null) {
            this.addPowerUpToList(powerUp);
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
                    Sound.playPowerUp();
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
