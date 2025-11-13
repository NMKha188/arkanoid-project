package arkanoid.source.code.gameplay.ball;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.brick.Brick;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.gameplay.powerup.ExplosiveBall;
import arkanoid.source.code.graphic.Texture;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball implements GameObject {
    private double x;
    private double y;
    private final double BALL_RADIUS = Config.BALL_RADIUS;
    private final Circle shape;

    private double ballSpeed = Config.BALL_SPEED;
    private double Vx;
    private double Vy;
    private double maxVx = ballSpeed * 0.75;
    private double changeVx = 0.05;

    public Ball() {
        shape = new Circle(BALL_RADIUS);
        Texture.applyTextureToBall(shape);
    }

    // this constructor copy characteristics from another ball, except Vx and Vy
    public Ball(Ball other, double Vx, double Vy) {
        this();
        this.x = other.x;
        this.y = other.y;
        this.ballSpeed = other.ballSpeed;
        this.Vx = Vx;
        this.Vy = Vy;
        this.maxVx = other.maxVx;
        this.changeVx = other.changeVx;
    }

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

    public double getMaxVx() {
        return maxVx;
    }

    public void setMaxVx(double maxVx) {
        this.maxVx = maxVx;
    }

    public double getChangeVx() {
        return changeVx;
    }

    public void setChangeVx(double changeVx) {
        this.changeVx = changeVx;
    }

    public void addShapeToGameRoot() {
        InGameLogic.getRoot().getChildren().add(shape);
    }

    public void removeShapeFromGameRoot() {
        InGameLogic.getRoot().getChildren().remove(shape);
    }

    // check if ball has fallen to bottom
    public boolean isAtBottom() {
        return (y >= Config.EXTRA / 3 + InGameLogic.getGameplayScreenHeight() + BALL_RADIUS);
    }

    // stick to the paddle and initialize Vx Vy (has not been released)
    public void initializeVelocity(Paddle paddle) {
        // stick to the paddle
        x = paddle.getX() + paddle.getWidth() / 2;
        y = paddle.getY() - BALL_RADIUS;

        // initialize Vx Vy
        if (Vx >= maxVx || Vx <= -maxVx) {
            changeVx = -changeVx;
        }
        Vx += changeVx;
        Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));
    }

    // check collision with other Game Objects
    private boolean checkCollision(GameObject o) {
        if (o instanceof Paddle) {
            return Shape.intersect(shape, ((Paddle) o).getShape()).getBoundsInLocal().getHeight() > 0;
        } else if (o instanceof Brick) {
            return Shape.intersect(shape, ((Brick) o).getShape()).getBoundsInLocal().getHeight() > 0;
        }
        return false;
    }

    // collide with top and side walls
    public void collideWithWalls() {
        // collide with the side walls
        if (x <= Config.EXTRA / 2 + BALL_RADIUS || x >= Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth() - BALL_RADIUS) {
            Vx = -Vx;
            if (x <= Config.EXTRA / 2 + BALL_RADIUS) {
                x = Config.EXTRA / 2 + BALL_RADIUS + 1;
            } else {
                x = Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth() - BALL_RADIUS - 1;
            }
            shape.setCenterX(x);
        }

        // collide with the top
        if (y <= Config.EXTRA / 4 + BALL_RADIUS) {
            Vy = -Vy;
            y = Config.EXTRA / 4 + BALL_RADIUS + 1;
            shape.setCenterY(y);
        }
    }

    // collide with paddle logic
    public void collideWithPaddle(Paddle paddle) {
        if (this.checkCollision(paddle)) {
            // collide with the top surface of the paddle
            if (paddle.getX() <= x && x <= paddle.getX() + paddle.getWidth()) {
                // effect from the distance between the position of collision and the center of the paddle
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                double dx = Math.abs(paddleCenter - x);
                Vx = maxVx * (dx / (paddle.getWidth() / 2));
                if ((Vx > 0 && x < paddleCenter) || (Vx < 0 && x > paddleCenter)) {
                    Vx = -Vx;
                }
                // effect from paddle speed
                if (InGameLogic.isMovingLeft()) {
                    Vx = Vx * 0.7 - 0.3 * paddle.getSpeed();
                    if (Vx < -maxVx) {
                        Vx = -maxVx;
                    }
                } else if (InGameLogic.isMovingRight()) {
                    Vx = Vx * 0.7 + 0.3 * paddle.getSpeed();
                    if (Vx > maxVx) {
                        Vx = maxVx;
                    }
                }
                Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));
                y = paddle.getY() - BALL_RADIUS - 1;
                shape.setCenterY(y);
            }
            // collide with the sides of the paddle
            else if (paddle.getY() <= y && y <= paddle.getY() + paddle.getHeight()) {
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
                    Vx = -maxVx;
                } else {
                    Vx = maxVx;
                }
                Vy = -Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(Vx, 2));
            }
        }
    }

    // collide with brick set (all bricks appear on the screen)
    public void collideWithBrickSet(BrickSet brickSet, PowerUpList powerUpList) {
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
                            // explosive ball is on
                            if (ExplosiveBall.isInExplosiveMode()) {
                                Texture.playExplosionAnimation(x, y);
                                ExplosiveBall.explosiveDamage(brickSet, i, j - 1, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else { // normal ball
                                leftBrick.getHit(1, brickSet, powerUpList);
                                currentBrick.getHit(1, brickSet, powerUpList);
                            }
                        } else if (rightBrick != null && this.checkCollision(rightBrick)) {
                            // explosive ball is on
                            if (ExplosiveBall.isInExplosiveMode()) {
                                Texture.playExplosionAnimation(x, y);
                                ExplosiveBall.explosiveDamage(brickSet, i, j + 1, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else { // normal ball
                                rightBrick.getHit(1, brickSet, powerUpList);
                                currentBrick.getHit(1, brickSet, powerUpList);
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
                            // explosive ball is on
                            if (ExplosiveBall.isInExplosiveMode()) {
                                Texture.playExplosionAnimation(x, y);
                                ExplosiveBall.explosiveDamage(brickSet, i - 1, j, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else { // normal ball
                                topBrick.getHit(1, brickSet, powerUpList);
                                currentBrick.getHit(1, brickSet, powerUpList);
                            }
                        } else if (bottomBrick != null && this.checkCollision(bottomBrick)) {
                            // explosive ball is on
                            if (ExplosiveBall.isInExplosiveMode()) {
                                Texture.playExplosionAnimation(x, y);
                                ExplosiveBall.explosiveDamage(brickSet, i + 1, j, powerUpList);
                                ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                            } else { // normal ball
                                bottomBrick.getHit(1, brickSet, powerUpList);
                                currentBrick.getHit(1, brickSet, powerUpList);
                            }
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
                            Texture.playExplosionAnimation(x, y);
                            ExplosiveBall.explosiveDamage(brickSet, i, j, powerUpList);
                        } else {
                            currentBrick.getHit(1, brickSet, powerUpList);
                        }
                        this.collideWithBrick(currentBrick);
                    }
                }
            }
        }
    }

    // collide with only 1 brick logic (already check collision) (private method used for collide with brick set)
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

    // private method to calculate ratio between Vx and Vy when ball collides with corners of 1 brick
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
        if (k > 1.1) {
            k = 1.1;
        } else if (k < 0.9) {
            k = 0.9;
        }
        return k;
    }

    public void update() {
        x += Vx;
        y += Vy;
        shape.setCenterX(x);
        shape.setCenterY(y);
    }

    public void reset() {
    }
}
