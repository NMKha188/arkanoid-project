package arkanoid.source.code;

import javafx.scene.shape.Rectangle;

public class Paddle {
    // paddle position and size
    private double x;
    private double y;
    private double paddleWidth = 80;
    private double paddleHeight = 15;
    private final Rectangle shape;
    // paddle speed
    private final double PADDLE_SPEED = 3;

    public Paddle(double SCREEN_WIDTH, double SCREEN_HEIGHT) {
        x = (SCREEN_WIDTH - paddleWidth) / 2;
        y = SCREEN_HEIGHT - 30;
        this.shape = new Rectangle(this.x, this.y, paddleWidth, paddleHeight);
    }

    // getter setter BEGIN
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return paddleWidth;
    }

    public double getHeight() {
        return paddleHeight;
    }

    public double getSpeed() {
        return PADDLE_SPEED;
    }

    public Rectangle getShape() {
        return shape;
    }
    // getter setter END

    // update paddle position based on key press
    public void updatePosition(boolean isMovingLeft, boolean isMovingRight, double SCREEN_WIDTH) {
        if (isMovingLeft && !isMovingRight && x >= 0) {
            x -= PADDLE_SPEED;
        } else if (isMovingRight && !isMovingLeft && x <= SCREEN_WIDTH - paddleWidth) {
            x += PADDLE_SPEED;
        }
        shape.setX(x);
    }
}
