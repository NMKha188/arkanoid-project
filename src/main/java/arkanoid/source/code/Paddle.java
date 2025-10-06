package arkanoid.source.code;

import javafx.scene.shape.Rectangle;

public class Paddle {
    private double x;
    private double y;
    private final double WIDTH = 80;
    private final double HEIGHT = 15;
    private final double PADDLE_SPEED = 2;
    private Rectangle paddle;

    public Paddle(double SCREEN_WIDTH, double SCREEN_HEIGHT) {
        this.x = (SCREEN_WIDTH - this.WIDTH) / 2;
        this.y = SCREEN_HEIGHT - 30;
        this.paddle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    // getter setter BEGIN
    public double getX() { return this.x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return this.y; }

    public void setY(double y) { this.y = y; }

    public double getWidth() { return this.WIDTH; }

    public double getHeight() { return this.HEIGHT; }

    public double getSpeed() { return this.PADDLE_SPEED; }

    public Rectangle getPaddle() { return this.paddle; }
    // getter setter END

    public void updatePosition(boolean isMovingLeft, boolean isMovingRight, double SCREEN_WIDTH) {
        if (isMovingLeft && !isMovingRight && this.x >= 0) {
            this.x -= PADDLE_SPEED;
        } else if (isMovingRight && !isMovingLeft && this.x <= SCREEN_WIDTH - this.WIDTH) {
            this.x += PADDLE_SPEED;
        }
        this.paddle.setX(this.x);
    }
}
