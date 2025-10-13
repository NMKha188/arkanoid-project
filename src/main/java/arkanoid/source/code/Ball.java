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

    // Vx representative line while sticking to the paddle (GUI for player to know which way will the ball fly after being released)
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

    // stick to the paddle and initialize Vx Vy
    private void initializeVelocity(Paddle paddle) {
        // stick to the paddle
        this.x = paddle.getX() + paddle.getWidth() / 2;
        this.y = paddle.getY() - BALL_RADIUS;

        // initialize Vx Vy
        if (this.Vx >= MAX_VX || this.Vx <= -MAX_VX) {
            this.changeVx = -this.changeVx;
        }
        this.Vx += this.changeVx;
        this.Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(this.Vx, 2));

        // Vx representative line (GUI for player to know which way will the ball fly after being released)
        this.line.setVisible(true);
        this.line.setStartX(this.x);
        this.line.setStartY(this.y + BALL_RADIUS + paddle.getHeight() + 5);
        this.line.setEndX(this.x + this.Vx * ((paddle.getWidth() / 2) / MAX_VX));
        this.line.setEndY(this.y + BALL_RADIUS + paddle.getHeight() + 5);
    }

    // check collision with other Object
    private boolean checkCollision(Object o) {
        if (o instanceof Paddle) {
            return Shape.intersect(this.ball, ((Paddle)o).getPaddle()).getBoundsInLocal().getHeight() > 0;
        } else if (o instanceof Brick) {
            return Shape.intersect(this.ball, ((Brick)o).getBrick()).getBoundsInLocal().getHeight() > 0;
        }
        return false;
    }

    // collide with top and side walls, fall to the bottom -> reset logic
    private void collideWithWalls(double SCREEN_WIDTH, double SCREEN_HEIGHT) {
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
        if (this.y >= SCREEN_HEIGHT + BALL_RADIUS) {
            this.released = false;
            this.Vx = 0;
        }
    }

    // collide with paddle logic
    private void collideWithPaddle(Paddle paddle, boolean isMovingLeft, boolean isMovingRight) {
        if (this.checkCollision(paddle)) {
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
            }
            // collide with the sides of the paddle
            else if (paddle.getY() <= this.y) {
                this.Vx = -this.Vx;
            }
            // collide with the corners of the paddle
            else {
                if (this.x < paddle.getX() + paddle.getWidth()) {
                    this.Vx = -MAX_VX;
                } else {
                    this.Vx = MAX_VX;
                }
                this.Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(this.Vx, 2));
            }
        }
    }

    // collide with only 1 brick logic (already check collision)
    private void collideWithBrick(Brick brick) {
        // collide with the top or bottom of the brick
        if (this.x >= brick.getX() && this.x <= brick.getX() + Brick.getBrickWidth()) {
            this.Vy = -this.Vy;
        }
        // collide with the sides of the brick
        else if (this.y >= brick.getY() && this.y <= brick.getY() + Brick.getBrickHeight()) {
            this.Vx = -this.Vx;
        }
        // collide with corners of the brick
        else {
            // calculate Vx and Vy, logic: Vx/Vy = dx/dy = k (k limit is between 0.75 and 1.25)-> Vx^2 + Vy^2 = BALL_SPEED -> Vx = ..., Vy = ...
            double dx;
            double dy;
            if (this.x < brick.getX()) {
                dx = Math.abs(this.x - brick.getX());
            } else {
                dx = Math.abs(this.x - (brick.getX()) + Brick.getBrickWidth());
            }
            if (this.y < brick.getY()) {
                dy = Math.abs(this.y - brick.getY());
            } else {
                dy = Math.abs(this.y - (this.getY() + Brick.getBrickHeight()));
            }
            double k = dx / dy;
            if (k > 1.25) {
                k = 1.25;
            } else if (k < 0.75) {
                k = 0.75;
            }

            this.Vy = BALL_SPEED / Math.sqrt(Math.pow(k, 2) + 1);
            this.Vx = k * this.Vy;

            if (this.x < brick.getX()) {
                this.Vx = -this.Vx;
            }
            if (this.y < brick.getY()) {
                this.Vy = -this.Vy;
            }
        }
    }

    // collide with brick set
    private void collideWithBrickSet(BrickSet brickSet) {
        for (int i = 0; i < BrickSet.getBrickRow(); i++) {
            for (int j = 0; j < BrickSet.getBricksEachRow(); j++) {
                Brick currentBrick = brickSet.getOneBrickAt(i, j);
                if (currentBrick.getHitPoints() > 0 && this.checkCollision(currentBrick)) {
                    currentBrick.getHit(1);
                    // create temporary references to left, right, top and bottom bricks of the current brick
                    Brick leftBrick = null;
                    Brick rightBrick = null;
                    Brick topBrick = null;
                    Brick bottomBrick = null;
                    if (j - 1 >= 0) {
                        leftBrick = brickSet.getOneBrickAt(i, j - 1);
                    }
                    if (j + 1 < BrickSet.getBricksEachRow()) {
                        rightBrick = brickSet.getOneBrickAt(i, j + 1);
                    }
                    if (i - 1 >= 0) {
                        topBrick = brickSet.getOneBrickAt(i - 1, j);
                    }
                    if (i + 1 < BrickSet.getBrickRow()) {
                        bottomBrick = brickSet.getOneBrickAt(i + 1, j);
                    }

                    // collide with current brick and either left or right brick
                    if ((leftBrick != null && leftBrick.getHitPoints() > 0 && this.checkCollision(leftBrick))
                            || (rightBrick != null && rightBrick.getHitPoints() > 0 && this.checkCollision(rightBrick))) {
                        this.Vy = -this.Vy;
                        if (leftBrick != null && this.checkCollision(leftBrick)) {
                            leftBrick.getHit(1);
                        } else if (rightBrick != null && this.checkCollision(rightBrick)) {
                            rightBrick.getHit(1);
                        }
                    }
                    // collide with current brick and either top or bottom brick
                    else if (topBrick != null && topBrick.getHitPoints() > 0 && this.checkCollision(topBrick)
                            || (bottomBrick != null && bottomBrick.getHitPoints() > 0 && this.checkCollision(bottomBrick))) {
                        this.Vx = -this.Vx;
                        if (topBrick != null && this.checkCollision(topBrick)) {
                            topBrick.getHit(1);
                        } else if (bottomBrick != null && this.checkCollision(bottomBrick)) {
                            bottomBrick.getHit(1);
                        }
                    }
                    // collide with only current brick
                    else {
                        this.collideWithBrick(currentBrick);
                    }
                    // delete temporary references
                    leftBrick = null;
                    rightBrick = null;
                    topBrick = null;
                    bottomBrick = null;
                }
            }
        }

    }

    // update ball position: stick to the paddle; collide with the top, side walls; fall to the bottom -> reset; collide with paddle
    public void updatePosition(double SCREEN_WIDTH, double SCREEN_HEIGHT, Paddle paddle, boolean isMovingLeft, boolean isMovingRight, BrickSet brickSet) {
        // not released, stick to the paddle
        if (!this.released) {
            this.initializeVelocity(paddle);
        } else {
            this.line.setVisible(false);
            //collide with top and side walls, fall to the bottom -> reset
            this.collideWithWalls(SCREEN_WIDTH, SCREEN_HEIGHT);
            // Collide with the paddle
            this.collideWithPaddle(paddle, isMovingLeft, isMovingRight);
            // collide with bricks
            this.collideWithBrickSet(brickSet);
            // position change
            this.x += this.Vx;
            this.y += this.Vy;
        }
        this.ball.setCenterX(this.x);
        this.ball.setCenterY(this.y);
    }
}
