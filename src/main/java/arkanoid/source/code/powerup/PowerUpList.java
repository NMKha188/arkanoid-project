package arkanoid.source.code.powerup;

import arkanoid.source.code.Ball;
import arkanoid.source.code.Paddle;
import arkanoid.source.code.brick.Brick;
import arkanoid.source.code.brick.BrickSet;

import java.util.ArrayList;

public class PowerUpList {
    private final ArrayList<PowerUp> powerUpList = new ArrayList<>();

    private void addPowerUpToList(PowerUp powerUp) {
        powerUpList.add(powerUp);
        powerUp.addShapeToRoot();
    }

    private void removePowerUpFromList(int index) {
        powerUpList.get(index).removeShapeFromRoot();
        powerUpList.remove(index);
    }

    // create power up based on probability when a brick is destroyed : Associated with brick, this method is called in getHit() method of Brick
    public void createPowerUp(Brick brick) {
        double x = brick.getX() + Brick.getBrickWidth() / 4;
        double y = brick.getY() + Brick.getBrickHeight() / 4;

        switch ((int) (Math.random() * 3)) {
            case 0:
                // expand paddle
                if ((int) (Math.random() * 100) <= ExpandPaddle.getProbability()) {
                    PowerUp expandPaddle = new ExpandPaddle(x, y);
                    this.addPowerUpToList(expandPaddle);
                }
                break;
            case 1:
                // speed up paddle
                if ((int) (Math.random() * 100) <= SpeedUpPaddle.getProbability()) {
                    PowerUp speedUpPaddle = new SpeedUpPaddle(x, y);
                    this.addPowerUpToList(speedUpPaddle);
                }
                break;
            case 2:
                // explosive ball
                if ((int) (Math.random() * 100) <= ExplosiveBall.getProbability()) {
                    PowerUp explosiveBall = new ExplosiveBall(x, y);
                    this.addPowerUpToList(explosiveBall);
                }
            default:
        }
    }

    public void update(Paddle paddle, Ball ball, BrickSet brickSet) {
        for (int i = 0; i < powerUpList.size(); i++) {
            PowerUp powerUp = powerUpList.get(i);
            // fall down
            powerUp.updatePosition();
            // paddle catch power up
            powerUp.caughtByPaddle(paddle);
            // fall to bottom without being caught by paddle
            if (powerUp.isFallenToBottom()) {
                this.removePowerUpFromList(i--);
            }

            if (powerUp.onDuration()) {
                if (powerUp instanceof ExpandPaddle || powerUp instanceof SpeedUpPaddle) {
                    powerUp.applyEffect(paddle);
                }
                if (powerUp instanceof ExplosiveBall) {
                    powerUp.applyEffect(ball);
                }
            }

            if (powerUp.runOutOfDuration()) {
                if (powerUp instanceof ExpandPaddle || powerUp instanceof SpeedUpPaddle) {
                    powerUp.removeEffect(paddle);
                }
                if (powerUp instanceof ExplosiveBall) {
                    powerUp.removeEffect(ball);
                }
                this.removePowerUpFromList(i--);
            }
        }
    }
}
