package arkanoid.source.code;

import javafx.scene.shape.Rectangle;

public class Paddle {
    // paddle position and size
    private double x;
    private double y;
    private static final double PADDLE_WIDTH = 80;
    private static final double PADDLE_HEIGHT = 15;
    private final Rectangle paddle;
    // paddle speed
    private static final double PADDLE_SPEED = 3;

    public Paddle(double SCREEN_WIDTH, double SCREEN_HEIGHT) {
        this.x = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
        this.y = SCREEN_HEIGHT - 30;
        this.paddle = new Rectangle(this.x, this.y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    // getter setter BEGIN
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return PADDLE_WIDTH;
    }

    public double getHeight() {
        return PADDLE_HEIGHT;
    }

    public double getSpeed() {
        return PADDLE_SPEED;
    }

    public Rectangle getPaddle() {
        return this.paddle;
    }
    // getter setter END

    // update paddle position based on key press
    public void updatePosition(boolean isMovingLeft, boolean isMovingRight, double SCREEN_WIDTH) {
        if (isMovingLeft && !isMovingRight && this.x >= 0) {
            this.x -= PADDLE_SPEED;
        } else if (isMovingRight && !isMovingLeft && this.x <= SCREEN_WIDTH - PADDLE_WIDTH) {
            this.x += PADDLE_SPEED;
        }
        this.paddle.setX(this.x);
    }
}
