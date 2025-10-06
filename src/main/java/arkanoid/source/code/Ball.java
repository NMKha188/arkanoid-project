package arkanoid.source.code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Ball {
    private double x;
    private double y;
    private final double RADIUS = 10;
    private Circle ball;

    private double Vx;
    double changeVx = 1;
    final double MAX_VX = 9;
    private double Vy;

    private boolean released;

    private Line line;

    public Ball() {
        this.x = 0;
        this.y = 0;
        this.Vx = 0;
        this.Vy = -3;
        this.ball = new Circle(RADIUS);
        this.released = false;
        this.line = new Line();
        this.line.setStrokeWidth(5);
        this.line.setStroke(Color.NAVY);
    }

    // getter setter BEGIN
    public double getX() { return this.x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return this.y; }

    public void setY(double y) { this.y = y; }

    public double getRadius() { return this.RADIUS; }

    public double getVx() { return this.Vx; }

    public void setVx(double Vx) { this.Vx = Vx; }

    public double getVy() { return this.Vy; }

    public void setVy(double Vy) { this.Vy = Vy; }

    public Circle getBall() { return this.ball; }

    public boolean getReleasedState() { return this.released; }

    public void setReleasedState(boolean released) { this.released = released; }

    public Line getLine() { return this.line; }
    // getter setter END

    public void updatePosition(double SCREEN_WIDTH, double SCREEN_HEIGHT, Paddle paddle, boolean isMovingLeft, boolean isMovingRight) {
        if (!this.released) {
            // stick to the paddle
            this.x = paddle.getX() + paddle.getWidth() / 2;
            this.y = paddle.getY() - this.RADIUS;
            // initialize Vx
            if (this.Vx > MAX_VX || this.Vx < -MAX_VX) {
                changeVx = -changeVx;
            }
            this.Vx += changeVx;
            // initial Vx representative line
            this.line.setVisible(true);
            this.line.setStartX(this.x);
            this.line.setStartY(this.y + this.RADIUS + paddle.getHeight() + 5);
            this.line.setEndX(this.x + this.Vx * 13.3);
            this.line.setEndY(this.y + this.RADIUS + paddle.getHeight() + 5);
        } else {
            this.line.setVisible(false);
            // collide with the side walls
            if (this.x < RADIUS || this.x > SCREEN_WIDTH - RADIUS) {
                this.Vx = -this.Vx;
            }
            // collide with the top
            if (this.y < RADIUS) {
                this.Vy = -this.Vy;
            }
            // ball falls to the bottom -> reset
            if (this.y > SCREEN_HEIGHT - RADIUS) {
                this.released = false;
                this.Vy = -this.Vy;
            }
            // Collide with the paddle
            if (Shape.intersect(this.ball, paddle.getPaddle()).getBoundsInLocal().getHeight() > 0) {
                // collide with the top surface of the paddle
                if (paddle.getX() < this.x && this.x < paddle.getX() + paddle.getWidth()) {
                    // effect from the distance between the position of collision and the center of the paddle
                    double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                    double dx = Math.abs(paddleCenter - this.x);
                    this.Vx = MAX_VX * (dx / (paddle.getWidth() / 2));
                    if ((this.Vx > 0 && this.x < paddleCenter) || (this.Vx < 0 && this.x > paddleCenter)) {
                        this.Vx = -this.Vx;
                    }
                    // effect from paddle speed
                    if (isMovingLeft) {
                        this.Vx = this.Vx * 0.7 - 0.3 * paddle.getSpeed();
                    } else if (isMovingRight) {
                        this.Vx = this.Vx * 0.7 + 0.3 * paddle.getSpeed();
                    }
                    this.Vy = -this.Vy;
                } else if (paddle.getY() < this.y) { // collide with the sides of the paddle
                    this.Vx = -this.Vx;
                } else { // collide with the corners of the paddle
                    if (this.x < paddle.getX() + paddle.getWidth()) {
                        this.Vx = -MAX_VX;
                    } else {
                        this.Vx = MAX_VX;
                    }
                    this.Vy = -this.Vy;
                }
            }
            this.x += this.Vx;
            this.y += this.Vy;
        }
        this.ball.setCenterX(this.x);
        this.ball.setCenterY(this.y);
    }
}
