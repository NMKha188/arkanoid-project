package arkanoid.source.code.gameplay.paddle;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.RectangleGameObject;
import arkanoid.source.code.graphic.Texture;

public class Paddle extends RectangleGameObject {
    private double paddleSpeed = Config.PADDLE_SPEED;

    public Paddle() {
        super(
                Config.EXTRA / 2 + (InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH) / 2,
                Config.EXTRA / 4 + InGameLogic.getGameplayScreenHeight() - 30,
                Config.PADDLE_WIDTH,
                Config.PADDLE_HEIGHT
        );
        Texture.applyTextureToPaddle(shape);
    }

    public double getSpeed() {
        return paddleSpeed;
    }

    public void setPaddleSpeed(double paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }

    // update paddle position based on key press
    public void update() {
        if (InGameLogic.isMovingLeft() && !InGameLogic.isMovingRight() && x >= Config.EXTRA / 2) {
            x -= paddleSpeed;
        } else if (InGameLogic.isMovingRight() && !InGameLogic.isMovingLeft() && x <= Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth() - width) {
            x += paddleSpeed;
        }
        shape.setX(x);
    }

    public void reset() {
        x = Config.EXTRA / 2 + (InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH) / 2;
        y = Config.EXTRA / 4 + InGameLogic.getGameplayScreenHeight() - 30;
    }
}
