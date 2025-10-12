package arkanoid.source.code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Ball {
    // ball center and radius
    private double x;
    private double y;
    private static final double BALL_RADIUS = 10;
    private final Circle ball;

    // x and y velocity
    private double Vx;
    private double Vy;
    // initialize Vx while sticking to the paddle
    private double changeVx = 0.05;
    private static final double MAX_VX = 3;
    // keep overall speed stable
    private static final double BALL_SPEED = 4.5;

    // check if ball is released or sticks to the paddle
    private boolean released;

    // Vx representative line while sticking to the paddle
    private final Line line;

    public Ball() {
        this.ball = new Circle(BALL_RADIUS);
        this.released = false;
        this.line = new Line();
        this.line.setStrokeWidth(5);
        this.line.setStroke(Color.NAVY);
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

    public double getRadius() {
        return BALL_RADIUS;
    }

    public double getVx() {
        return this.Vx;
    }

    public void setVx(double Vx) {
        this.Vx = Vx;
    }

    public double getVy() {
        return this.Vy;
    }

    public void setVy(double Vy) {
        this.Vy = Vy;
    }

    public Circle getBall() {
        return this.ball;
    }

    public boolean getReleasedState() {
        return this.released;
    }

    public void setReleasedState(boolean released) {
        this.released = released;
    }

    public Line getLine() {
        return this.line;
    }
    // getter setter END

    // collide with paddle logic
    private void collideWithPaddle(Paddle paddle, boolean isMovingLeft, boolean isMovingRight) {
        if (Shape.intersect(this.ball, paddle.getPaddle()).getBoundsInLocal().getHeight() > 0) {
            // collide with the top surface of the paddle
            if (paddle.getX() <= this.x && this.x <= paddle.getX() + paddle.getWidth()) {
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
                this.Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(this.Vx, 2));
            } else if (paddle.getY() <= this.y) { // collide with the sides of the paddle
                this.Vx = -this.Vx;
            } else { // collide with the corners of the paddle
                if (this.x < paddle.getX() + paddle.getWidth()) {
                    this.Vx = -MAX_VX;
                } else {
                    this.Vx = MAX_VX;
                }
                this.Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(this.Vx, 2));
            }
        }
    }

    // collide with 1 brick logic
    private void collideWithBrick(Brick brick) {
        if (brick.getHitPoints() > 0 && Shape.intersect(this.ball, brick.getBrick()).getBoundsInLocal().getHeight() > 0) {
            // collision logic
            if (this.x >= brick.getX() && this.x <= brick.getX() + Brick.getBrickWidth()) {
                this.Vy = -this.Vy;
            } else if (this.y >= brick.getY() && this.y <= brick.getY() + Brick.getBrickHeight()) {
                this.Vx = -this.Vx;
            } else {
                this.Vx = -this.Vx;
                this.Vy = -this.Vy;
            }
            // subtract brick HP
            brick.setHitPoints(brick.getHitPoints() - 1);
            if (brick.getHitPoints() == 0) {
                brick.setVisibility(false);
            }
        }
    }

    // collide with brick set
    private void collideWithBrickSet(BrickSet brickSet) {
        for (Brick brick : brickSet.getBrickSet()) {
            this.collideWithBrick(brick);
        }
    }

    // update ball position: stick to the paddle; collide with the top, side walls; fall to the bottom -> reset; collide with paddle
    public void updatePosition(double SCREEN_WIDTH, double SCREEN_HEIGHT, Paddle paddle, boolean isMovingLeft, boolean isMovingRight, BrickSet brickSet) {
        // not released, stick to the paddle
        if (!this.released) {
            // stick to the paddle
            this.x = paddle.getX() + paddle.getWidth() / 2;
            this.y = paddle.getY() - BALL_RADIUS;
            // initialize Vx Vy
            if (this.Vx >= MAX_VX || this.Vx <= -MAX_VX) {
                this.changeVx = -this.changeVx;
            }
            this.Vx += this.changeVx;
            this.Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(this.Vx, 2));
            // initial Vx representative line
            this.line.setVisible(true);
            this.line.setStartX(this.x);
            this.line.setStartY(this.y + BALL_RADIUS + paddle.getHeight() + 5);
            this.line.setEndX(this.x + this.Vx * ((paddle.getWidth() / 2) / MAX_VX));
            this.line.setEndY(this.y + BALL_RADIUS + paddle.getHeight() + 5);
        } else {
            this.line.setVisible(false);
            // collide with the side walls
            if (this.x <= BALL_RADIUS || this.x >= SCREEN_WIDTH - BALL_RADIUS) {
                if (this.x <= BALL_RADIUS) {
                    this.x = BALL_RADIUS;
                } else {
                    this.x = SCREEN_WIDTH - BALL_RADIUS;
                }
                this.Vx = -this.Vx;
            }
            // collide with the top
            if (this.y <= BALL_RADIUS) {
                this.y = BALL_RADIUS;
                this.Vy = -this.Vy;
            }
            // fall to the bottom -> reset
            if (this.y >= SCREEN_HEIGHT - BALL_RADIUS) {
                this.y = SCREEN_HEIGHT - BALL_RADIUS;
                this.released = false;
                this.Vx = 0;
            }
            // Collide with the paddle
            this.collideWithPaddle(paddle, isMovingLeft, isMovingRight);
            this.collideWithBrickSet(brickSet);
            this.x += this.Vx;
            this.y += this.Vy;
        }
        this.ball.setCenterX(this.x);
        this.ball.setCenterY(this.y);
    }
}
