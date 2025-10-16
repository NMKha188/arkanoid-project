package arkanoid.source.code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Ball {
    // ball center and radius
    private double x;
    private double y;
    private final double BALL_RADIUS = 10;
    private final Circle shape;
    // x and y velocity
    private double Vx;
    private double Vy;
    // initialize Vx while sticking to the paddle
    private double changeVx = 0.05;
    private final double MAX_VX = 3;
    // keep overall speed stable
    private final double BALL_SPEED = 4.5;
    // check if ball is released or sticks to the paddle
    private boolean released;
    // Vx representative line while sticking to the paddle (GUI for player to know which way will the ball fly after being released)
    private final Line line;

    public Ball() {
        shape = new Circle(BALL_RADIUS);
        released = false;
        line = new Line();
        line.setStrokeWidth(5);
        line.setStroke(Color.NAVY);
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

    public double getRadius() {
        return BALL_RADIUS;
    }

    public double getVx() {
        return Vx;
    }

    public void setVx(double Vx) {
        this.Vx = Vx;
    }

    public double getVy() {
        return Vy;
    }

    public void setVy(double Vy) {
        this.Vy = Vy;
    }

    public Circle getShape() {
        return shape;
    }

    public boolean getReleasedState() {
        return released;
    }

    public void setReleasedState(boolean released) {
        this.released = released;
    }

    public Line getLine() {
        return line;
    }
    // getter setter END

    // stick to the paddle and initialize Vx Vy
    private void initializeVelocity(Paddle paddle) {
        // initialize Vx Vy
        if (Vx >= MAX_VX || Vx <= -MAX_VX) {
            changeVx = -changeVx;
        }
        Vx += changeVx;
        Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(Vx, 2));

        // stick to the paddle
        x = paddle.getX() + paddle.getWidth() / 2;
        y = paddle.getY() - BALL_RADIUS;

        // Vx representative line (GUI for player to know which way will the ball fly after being released)
        line.setVisible(true);
        line.setStartX(x);
        line.setStartY(y + BALL_RADIUS + paddle.getHeight() + 5);
        line.setEndX(x + Vx * ((paddle.getWidth() / 2) / MAX_VX));
        line.setEndY(y + BALL_RADIUS + paddle.getHeight() + 5);
    }

    // check collision with other Object
    private boolean checkCollision(Object o) {
        if (o instanceof Paddle) {
            return Shape.intersect(shape, ((Paddle) o).getShape()).getBoundsInLocal().getHeight() > 0;
        } else if (o instanceof Brick) {
            return Shape.intersect(shape, ((Brick) o).getShape()).getBoundsInLocal().getHeight() > 0;
        }
        return false;
    }

    // collide with top and side walls, fall to the bottom -> reset logic
    private void collideWithWalls() {
        // collide with the side walls
        if (x <= BALL_RADIUS || x >= Main.getScreenWidth() - BALL_RADIUS) {
            Vx = -Vx;
            if (x <= BALL_RADIUS) {
                x = BALL_RADIUS + 1;
            } else {
                x = Main.getScreenWidth() - BALL_RADIUS - 1;
            }
            shape.setCenterX(x);
        }

        // collide with the top
        if (y <= BALL_RADIUS) {
            Vy = -Vy;
            y = BALL_RADIUS + 1;
            shape.setCenterY(y);
        }

        // fall to the bottom -> reset
        if (y >= Main.getScreenHeight() + BALL_RADIUS) {
            released = false;
            Vx = 0;
        }
    }

    // collide with paddle logic
    private void collideWithPaddle(Paddle paddle) {
        if (this.checkCollision(paddle)) {
            // collide with the top surface of the paddle
            if (paddle.getX() <= x && x <= paddle.getX() + paddle.getWidth()) {
                // effect from the distance between the position of collision and the center of the paddle
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                double dx = Math.abs(paddleCenter - x);
                Vx = MAX_VX * (dx / (paddle.getWidth() / 2));
                if ((Vx > 0 && x < paddleCenter) || (Vx < 0 && x > paddleCenter)) {
                    Vx = -Vx;
                }

                // effect from paddle speed
                if (Main.isMovingLeft()) {
                    Vx = Vx * 0.7 - 0.3 * paddle.getSpeed();
                } else if (Main.isMovingRight()) {
                    Vx = Vx * 0.7 + 0.3 * paddle.getSpeed();
                }
                Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(Vx, 2));
                y = paddle.getY() - BALL_RADIUS - 1;
                shape.setCenterY(y);
            }
            // collide with the sides of the paddle
            else if (paddle.getY() <= y) {
                Vx = -Vx;
                if (x <= paddle.getX() + paddle.getWidth() / 2) {
                    x = paddle.getX() - BALL_RADIUS - 1;
                } else {
                    x = paddle.getX() + paddle.getWidth() + BALL_RADIUS + 1;
                }
                shape.setCenterX(x);
            }
            // collide with the corners of the paddle
            else {
                if (x < paddle.getX() + paddle.getWidth()) {
                    Vx = -MAX_VX;
                } else {
                    Vx = MAX_VX;
                }
                Vy = -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(Vx, 2));
            }
        }
    }

    // collide with only 1 brick logic (already check collision)
    private void collideWithBrick(Brick brick) {
        // collide with the top or bottom of the brick
        if (x >= brick.getX() && x <= brick.getX() + Brick.getBrickWidth()) {
            Vy = -Vy;
            if (y < brick.getY() + Brick.getBrickHeight() / 2) {
                y = brick.getY() - BALL_RADIUS - 1;
            } else {
                y = brick.getY() + Brick.getBrickHeight() + BALL_RADIUS + 1;
            }
            shape.setCenterY(y);
        }
        // collide with the sides of the brick
        else if (y >= brick.getY() && y <= brick.getY() + Brick.getBrickHeight()) {
            Vx = -Vx;
            if (x < brick.getX() + Brick.getBrickWidth() / 2) {
                x = brick.getX() - BALL_RADIUS - 1;
            } else {
                x = brick.getX() + Brick.getBrickWidth() + BALL_RADIUS + 1;
            }
            shape.setCenterX(x);
        }
        // collide with corners of the brick
        else {
            // calculate Vx and Vy, logic: Vx/Vy = dx/dy = k (k limit is between 0.75 and 1.25)-> Vx^2 + Vy^2 = BALL_SPEED -> Vx = ..., Vy = ...
            double dx;
            double dy;
            if (x < brick.getX()) {
                dx = Math.abs(x - brick.getX());
            } else {
                dx = Math.abs(x - (brick.getX()) + Brick.getBrickWidth());
            }
            if (y < brick.getY()) {
                dy = Math.abs(y - brick.getY());
            } else {
                dy = Math.abs(y - (brick.getY() + Brick.getBrickHeight()));
            }
            double k = dx / dy;
            if (k > 1.25) {
                k = 1.25;
            } else if (k < 0.75) {
                k = 0.75;
            }
            Vy = BALL_SPEED / Math.sqrt(Math.pow(k, 2) + 1);
            Vx = k * Vy;
            if (x < brick.getX()) {
                Vx = -Vx;
            }
            if (y < brick.getY()) {
                Vy = -Vy;
            }
        }
    }

    // collide with brick set
    private void collideWithBrickSet(BrickSet brickSet) {
        for (int i = 0; i < BrickSet.getBrickRow(); i++) {
            for (int j = 0; j < BrickSet.getBricksEachRow(); j++) {
                Brick currentBrick = brickSet.getOneBrickAt(i, j);
                if (currentBrick == null) {
                    continue;
                }
                if (currentBrick.getHitPoints() > 0 && this.checkCollision(currentBrick)) {
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
                        // hit current brick and either left or right brick
                        if (leftBrick != null && this.checkCollision(leftBrick)) {
                            leftBrick.getHit(1);
                            currentBrick.getHit(1);
                        } else if (rightBrick != null && this.checkCollision(rightBrick)) {
                            currentBrick.getHit(1);
                            rightBrick.getHit(1);
                        }
                        // change Vy and set new position
                        Vy = -Vy;
                        if (y < currentBrick.getY() + Brick.getBrickHeight() / 2) {;
                            y = currentBrick.getY() - BALL_RADIUS - 1;
                        } else {
                            y = currentBrick.getY() + Brick.getBrickHeight() + BALL_RADIUS + 1;
                        }
                        shape.setCenterY(y);
                    }
                    // collide with current brick and either top or bottom brick
                    else if (topBrick != null && topBrick.getHitPoints() > 0 && this.checkCollision(topBrick)
                            || (bottomBrick != null && bottomBrick.getHitPoints() > 0 && this.checkCollision(bottomBrick))) {
                        // hit current brick and either top or bottom brick
                        if (topBrick != null && this.checkCollision(topBrick)) {
                            currentBrick.getHit(1);
                            topBrick.getHit(1);
                        } else if (bottomBrick != null && this.checkCollision(bottomBrick)) {
                            currentBrick.getHit(1);
                            bottomBrick.getHit(1);
                        }
                        // change Vx and set new position
                        Vx = -Vx;
                        if (x < currentBrick.getX() + Brick.getBrickWidth() / 2) {
                            x = currentBrick.getX() - BALL_RADIUS - 1;
                        } else {
                            x = currentBrick.getX() + Brick.getBrickWidth() + BALL_RADIUS + 1;
                        }
                        shape.setCenterX(x);
                    }
                    // collide with only current brick
                    else {
                        currentBrick.getHit(1);
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
    public void updatePosition(Paddle paddle, BrickSet brickSet) {
        // not released, stick to the paddle
        if (!released) {
            this.initializeVelocity(paddle);
        } else {
            line.setVisible(false);
            //collide with top and side walls, fall to the bottom -> reset
            this.collideWithWalls();
            // Collide with the paddle
            this.collideWithPaddle(paddle);
            // collide with bricks
            this.collideWithBrickSet(brickSet);
            // position change
            x += Vx;
            y += Vy;
        }
        shape.setCenterX(x);
        shape.setCenterY(y);
    }
}
