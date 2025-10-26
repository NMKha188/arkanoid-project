package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;

public class Paddle extends RectangleGameObject {
    // paddle speed
    private double paddleSpeed = Config.PADDLE_SPEED;

    // constructor
    public Paddle() {
        super(
                Config.EXTRA / 2 + (InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH) / 2,
                Config.EXTRA / 4 + InGameLogic.getGameplayScreenHeight() - 30,
                Config.PADDLE_WIDTH,
                Config.PADDLE_HEIGHT
        );
    }

    // getter setter BEGIN
    public double getSpeed() {
        return paddleSpeed;
    }

    public void setPaddleSpeed(double paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }
    // getter setter END

    // update paddle position based on key press
    public void update() {
        if (InGameLogic.isMovingLeft() && !InGameLogic.isMovingRight() && x >= Config.EXTRA / 2) {
            x -= paddleSpeed;
        } else if (InGameLogic.isMovingRight() && !InGameLogic.isMovingLeft() && x <= Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth() - width) {
            x += paddleSpeed;
        }
        shape.setX(x);
    }
}
