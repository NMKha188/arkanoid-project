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
    private double paddleSpeed = 3;

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
        shape.setX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        shape.setY(y);
    }

    public double getWidth() {
        return paddleWidth;
    }

    public void setWidth(double width) {
        this.paddleWidth = width;
        shape.setWidth(width);
    }

    public double getHeight() {
        return paddleHeight;
    }

    public void setHeight(double height) {
        this.paddleHeight = height;
        shape.setHeight(height);
    }

    public double getSpeed() {
        return paddleSpeed;
    }

    public void setPaddleSpeed(double paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }

    public Rectangle getShape() {
        return shape;
    }
    // getter setter END

    // update paddle position based on key press
    public void update() {
        if (InGameLogic.isMovingLeft() && !InGameLogic.isMovingRight() && x >= 0) {
            x -= paddleSpeed;
        } else if (InGameLogic.isMovingRight() && !InGameLogic.isMovingLeft() && x <= InGameLogic.getScreenWidth() - paddleWidth) {
            x += paddleSpeed;
        }
        shape.setX(x);
    }
}
