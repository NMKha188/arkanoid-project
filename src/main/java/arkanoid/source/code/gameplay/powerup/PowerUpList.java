package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.Paddle;
import arkanoid.source.code.gameplay.brick.Brick;
import arkanoid.source.code.gameplay.brick.BrickSet;

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
        double x = brick.getX() + (brick.getWidth() - Config.POWER_UP_WIDTH) / 2;
        double y = brick.getY() + (brick.getHeight() - Config.POWER_UP_HEIGHT) / 2;

        switch ((int) (Math.random() * 6)) {
            case 0:
                // expand paddle
                if ((int) (Math.random() * 100) <= Config.EXPAND_PADDLE_PROBABILITY) {
                    PowerUp expandPaddle = new ExpandPaddle(x, y);
                    this.addPowerUpToList(expandPaddle);
                }
                break;
            case 1:
                // speed up paddle
                if ((int) (Math.random() * 100) <= Config.SPEED_UP_PADDLE_PROBABILITY) {
                    PowerUp speedUpPaddle = new SpeedUpPaddle(x, y);
                    this.addPowerUpToList(speedUpPaddle);
                }
                break;
            case 2:
                // slow ball
                if ((int) (Math.random() * 100) <= Config.SLOW_BALL_PROBABILITY) {
                    PowerUp slowBall = new SlowBall(x, y);
                    this.addPowerUpToList(slowBall);
                }
                break;
            case 3:
                // explosive ball
                if ((int) (Math.random() * 100) <= Config.EXPLOSIVE_BALL_PROBABILITY) {
                    PowerUp explosiveBall = new ExplosiveBall(x, y);
                    this.addPowerUpToList(explosiveBall);
                }
                break;
            case 4:
                // triple ball
                if ((int) (Math.random() * 100) <= Config.TRIPLE_BALL_PROBABILITY) {
                    PowerUp tripleBall = new TripleBall(x, y);
                    this.addPowerUpToList(tripleBall);
                }
                break;
            case 5:
                // life
                if ((int) (Math.random() * 100) <= Config.LIFE_PROBABILITY) {
                    PowerUp life = new Life(x, y);
                    this.addPowerUpToList(life);
                }
                break;
            default:
        }
    }

    public void update(Paddle paddle, BallList ballList, BrickSet brickSet) {
        for (int i = 0; i < powerUpList.size(); i++) {
            PowerUp powerUp = powerUpList.get(i);
            // fall down
            powerUp.update();
            // paddle catch power up
            powerUp.caughtByPaddle(paddle);
            // fall to bottom without being caught by paddle
            if (powerUp.isFallenToBottom()) {
                this.removePowerUpFromList(i--);
            }

            if (powerUp.getEffectStartTime() != null && (powerUp instanceof Life || powerUp instanceof TripleBall)) {
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
}
