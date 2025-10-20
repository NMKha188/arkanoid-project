package arkanoid.source.code.powerup;

import arkanoid.source.code.Paddle;
import arkanoid.source.code.brick.Brick;

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

        switch ((int) (Math.random() * 2)) {
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
            default:
        }
    }

    public void update(Paddle paddle) {
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
                powerUp.applyEffect(paddle);
            }

            if (powerUp.runOutOfDuration()) {
                powerUp.removeEffect(paddle);
                this.removePowerUpFromList(i--);
            }
        }
    }
}
