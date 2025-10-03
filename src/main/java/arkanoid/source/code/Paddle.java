package arkanoid.source.code;

import javafx.scene.shape.Rectangle;

public class Paddle {
    private double x;
    private double y;
    private final double WIDTH = 80;
    private final double HEIGHT = 15;
    private final double PADDLE_SPEED = 2;
    private Rectangle paddle;

    public Paddle() {
        this.x = 275;
        this.y = 680;
        this.paddle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    public double getX() { return this.x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return this.y; }
    public void setY(double y) { this.y = y; }
    public Rectangle getPaddle() { return this.paddle; }

    public void updatePosition(boolean isMovingLeft, boolean isMovingRight, double SCREEN_WIDTH) {
        if (isMovingLeft && !isMovingRight && this.x >= 0) {
            this.x -= PADDLE_SPEED;
        } else if (isMovingRight && !isMovingLeft && this.x <= SCREEN_WIDTH - this.WIDTH) {
            this.x += PADDLE_SPEED;
        }
        this.paddle.setX(this.x);
    }
}
