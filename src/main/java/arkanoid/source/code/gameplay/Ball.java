package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.brick.Brick;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.gameplay.powerup.ExplosiveBall;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Ball implements GameObject {
    // ball center and radius
    private double x;
    private double y;
    private final double BALL_RADIUS = Config.BALL_RADIUS;
    private final Circle shape;
    // velocity
    private double ballSpeed = Config.BALL_SPEED;
    private double Vx;
    private double Vy;
    private final double MAX_VX = ballSpeed * 0.67;
    private double changeVx = 0.05;
    // Vx representative line while sticking to the paddle (GUI for player to know which way will the ball fly after being released)
    private final Line velocityRepresentativeLine;
    // check if ball is released or sticks to the paddle
    private boolean released;

    public Ball() {
        shape = new Circle(BALL_RADIUS);
        released = false;
        velocityRepresentativeLine = new Line();
        velocityRepresentativeLine.setStrokeWidth(5);
        velocityRepresentativeLine.setStroke(Color.NAVY);
    }

    // getter setter BEGIN
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        shape.setCenterX(this.x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        shape.setCenterY(this.y);
    }

    public double getRadius() {
        return BALL_RADIUS;
    }

    public Circle getShape() {
        return shape;
    }

    public double getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public boolean getReleasedState() {
        return released;
    }

    public void setReleasedState(boolean released) {
        this.released = released;
    }

    public Line getvelocityRepresentativeLine() {
        return velocityRepresentativeLine;
    }
    // getter setter END

    // stick to the paddle and initialize Vx Vy
    private void initializeVelocity(Paddle paddle) {
        // initialize Vx Vy
        if (Vx >= MAX_VX || Vx <= -MAX_VX) {
            changeVx = -changeVx;
        }
        Vx += changeVx;
        Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));

        // stick to the paddle
        x = paddle.getX() + paddle.getWidth() / 2;
        y = paddle.getY() - BALL_RADIUS;

        // Vx representative line (GUI for player to know which way will the ball fly after being released)
        velocityRepresentativeLine.setStartX(x);
        velocityRepresentativeLine.setStartY(y + BALL_RADIUS + paddle.getHeight() + 5);
        velocityRepresentativeLine.setEndX(x + Vx * ((paddle.getWidth() / 2) / MAX_VX));
        velocityRepresentativeLine.setEndY(y + BALL_RADIUS + paddle.getHeight() + 5);
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
        if (x <= BALL_RADIUS || x >= InGameLogic.getGameplayScreenWidth() - BALL_RADIUS) {
            Vx = -Vx;
            if (x <= BALL_RADIUS) {
                x = BALL_RADIUS + 1;
            } else {
                x = InGameLogic.getGameplayScreenWidth() - BALL_RADIUS - 1;
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
        if (y >= InGameLogic.getGameplayScreenHeight() + BALL_RADIUS) {
            released = false;
            Vx = 0;
            velocityRepresentativeLine.setVisible(true);
            InGameStatus.loseLife();
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
                if (InGameLogic.isMovingLeft()) {
                    Vx = Vx * 0.7 - 0.3 * paddle.getSpeed();
                } else if (InGameLogic.isMovingRight()) {
                    Vx = Vx * 0.7 + 0.3 * paddle.getSpeed();
                }
                Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));
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
                Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));
            }
        }
    }

    // private method for collision between ball and corner of brick
    private double calculateRatioBetweenVxAndVy(Brick brick) {
        // logic: Vx/Vy = dx/dy = k (k limit is between 0.75 and 1.25)-> Vx^2 + Vy^2 = ballSpeed -> Vx = ..., Vy = ...
        double dx;
        double dy;
        if (x < brick.getX()) {
            dx = Math.abs(x - brick.getX());
        } else {
            dx = Math.abs(x - (brick.getX()) + Config.BRICK_WIDTH);
        }
        if (y < brick.getY()) {
            dy = Math.abs(y - brick.getY());
        } else {
            dy = Math.abs(y - (brick.getY() + Config.BRICK_HEIGHT));
        }
        double k = dx / dy;
        if (k > 1.25) {
            k = 1.25;
        } else if (k < 0.75) {
            k = 0.75;
        }
        return k;
    }

    // collide with only 1 brick logic (already check collision)
    private void collideWithBrick(Brick brick) {
        // collide with the top or bottom of the brick
        if (x >= brick.getX() && x <= brick.getX() + brick.getWidth()) {
            Vy = -Vy;
            if (y < brick.getY() + brick.getHeight() / 2) {
                y = brick.getY() - BALL_RADIUS - 1;
            } else {
                y = brick.getY() + brick.getHeight() + BALL_RADIUS + 1;
            }
            shape.setCenterY(y);
        }
        // collide with the sides of the brick
        else if (y >= brick.getY() && y <= brick.getY() + brick.getHeight()) {
            Vx = -Vx;
            if (x < brick.getX() + brick.getWidth() / 2) {
                x = brick.getX() - BALL_RADIUS - 1;
            } else {
                x = brick.getX() + brick.getWidth() + BALL_RADIUS + 1;
            }
            shape.setCenterX(x);
        }
        // collide with corners of the brick
        else {
            Vy = ballSpeed / Math.sqrt(Math.pow(calculateRatioBetweenVxAndVy(brick), 2) + 1);
            Vx = Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vy, 2));
            if (x < brick.getX()) {
                Vx = -Vx;
            }
            if (y < brick.getY()) {
                Vy = -Vy;
            }
        }
    }

    // collide with brick set
    private void collideWithBrickSet(BrickSet brickSet, PowerUpList powerUpList) {
        for (int i = 0; i < brickSet.getBricksRow(); i++) {
            for (int j = 0; j < brickSet.getBricksPerRow(); j++) {
                Brick currentBrick = brickSet.getOneBrickAt(i, j);
                if (currentBrick == null) {
                    continue;
                }
                if (!currentBrick.isDestroyed() && this.checkCollision(currentBrick)) {
                    // create temporary references to left, right, top and bottom bricks of the current brick
                    Brick leftBrick = brickSet.getOneBrickAt(i, j - 1);
                    Brick rightBrick = brickSet.getOneBrickAt(i, j + 1);
                    Brick topBrick = brickSet.getOneBrickAt(i - 1, j);
                    Brick bottomBrick = brickSet.getOneBrickAt(i + 1, j);

                    // collide with current brick and either left or right brick
                    if ((leftBrick != null && !leftBrick.isDestroyed() && this.checkCollision(leftBrick))
                            || (rightBrick != null && !rightBrick.isDestroyed() && this.checkCollision(rightBrick))) {
                        // hit current brick and either left or right brick
                        if (leftBrick != null && this.checkCollision(leftBrick)) {
                            if (ExplosiveBall.isInExplosiveMode()) {
                                ExplosiveBall.explosiveDamage(brickSet, i, j - 1, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else {
                                leftBrick.getHit(1, powerUpList);
                                currentBrick.getHit(1, powerUpList);
                            }
                        } else if (rightBrick != null && this.checkCollision(rightBrick)) {
                            if (ExplosiveBall.isInExplosiveMode()) {
                                ExplosiveBall.explosiveDamage(brickSet, i, j + 1, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else {
                                rightBrick.getHit(1, powerUpList);
                                currentBrick.getHit(1, powerUpList);
                            }
                        }
                        // change Vy and set new position
                        Vy = -Vy;
                        if (y < currentBrick.getY() + Config.BRICK_HEIGHT / 2) {
                            y = currentBrick.getY() - BALL_RADIUS - 1;
                        } else {
                            y = currentBrick.getY() + Config.BRICK_HEIGHT + BALL_RADIUS + 1;
                        }
                        shape.setCenterY(y);
                    }
                    // collide with current brick and either top or bottom brick
                    else if (topBrick != null && !topBrick.isDestroyed() && this.checkCollision(topBrick)
                            || (bottomBrick != null && !bottomBrick.isDestroyed() && this.checkCollision(bottomBrick))) {
                        // hit current brick and either top or bottom brick
                        if (topBrick != null && this.checkCollision(topBrick)) {
                            if (ExplosiveBall.isInExplosiveMode()) {
                                ExplosiveBall.explosiveDamage(brickSet, i - 1, j, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else {
                                topBrick.getHit(1, powerUpList);
                                currentBrick.getHit(1, powerUpList);
                            }
                        } else if (bottomBrick != null && this.checkCollision(bottomBrick)) {
                            if (ExplosiveBall.isInExplosiveMode()) {
                                ExplosiveBall.explosiveDamage(brickSet, i, j + 1, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else {
                                bottomBrick.getHit(1, powerUpList);
                                currentBrick.getHit(1, powerUpList);
                            }
                            currentBrick.getHit(1, powerUpList);
                            bottomBrick.getHit(1, powerUpList);
                        }
                        // change Vx and set new position
                        Vx = -Vx;
                        if (x < currentBrick.getX() + Config.BRICK_WIDTH / 2) {
                            x = currentBrick.getX() - BALL_RADIUS - 1;
                        } else {
                            x = currentBrick.getX() + Config.BRICK_WIDTH + BALL_RADIUS + 1;
                        }
                        shape.setCenterX(x);
                    }
                    // collide with only current brick
                    else {
                        if (ExplosiveBall.isInExplosiveMode()) {
                            ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                        } else {
                            currentBrick.getHit(1, powerUpList);
                        }
                        this.collideWithBrick(currentBrick);
                    }
                }
            }
        }

    }

    public void update() {
    }

    // update ball position: stick to the paddle; collide with the top, side walls; fall to the bottom -> reset; collide with paddle
    public void update(Paddle paddle, BrickSet brickSet, PowerUpList powerUpList) {
        // not released, stick to the paddle
        if (!released) {
            this.initializeVelocity(paddle);
        } else {
            velocityRepresentativeLine.setVisible(false);
            //collide with top and side walls, fall to the bottom -> reset
            this.collideWithWalls();
            // Collide with the paddle
            this.collideWithPaddle(paddle);
            // collide with bricks
            this.collideWithBrickSet(brickSet, powerUpList);
            // position change
            x += Vx;
            y += Vy;
        }
        shape.setCenterX(x);
        shape.setCenterY(y);
    }
}
