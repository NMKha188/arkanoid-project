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

    public Paddle() {
        x = (InGameLogic.getScreenWidth() - paddleWidth) / 2;
        y = InGameLogic.getScreenHeight() - 30;
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
    public void updatePosition() {
        if (InGameLogic.isMovingLeft() && !InGameLogic.isMovingRight() && x >= 0) {
            x -= PADDLE_SPEED;
        } else if (InGameLogic.isMovingRight() && !InGameLogic.isMovingLeft() && x <= InGameLogic.getScreenWidth() - paddleWidth) {
            x += PADDLE_SPEED;
        }
        shape.setX(x);
    }
}
