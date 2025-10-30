package arkanoid.source.code.gameplay.ball;

import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.Paddle;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;

import java.util.ArrayList;

public class BallList implements GameObject {
    private final ArrayList<Ball> ballList;
    private boolean released;

    public BallList() {
        ballList = new ArrayList<>();
        Ball firstBall = new Ball();
        ballList.add(firstBall);
        released = false;
    }

    // getter setter BEGIN
    public ArrayList<Ball> getBallList() {
        return ballList;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
    // getter setter END

    public void addBallToList(Ball ball) {
        ballList.add(ball);
    }

    public void addShapeToGameRoot() {
        for (Ball ball : ballList) {
            ball.addShapeToGameRoot();
        }
    }

    public void removeShapeFromGameRoot() {
        for (Ball ball : ballList) {
            ball.removeShapeFromGameRoot();
        }
    }

    public void update(Paddle paddle, BrickSet brickSet, PowerUpList powerUpList) {
        if (!released && ballList.size() == 1) {
            ballList.getFirst().initializeVelocity(paddle);
            ballList.getFirst().getShape().setCenterX(ballList.getFirst().getX());
            ballList.getFirst().getShape().setCenterY(ballList.getFirst().getY());
        } else {
            for (int i = 0; i < ballList.size(); i++) {
                Ball ball = ballList.get(i);
                ball.collideWithWalls();
                if (ball.isAtBottom()) {
                    if (ballList.size() == 1) {
                        released = false;
                        ball.setVx(0);
                        InGameStatus.loseLife();
                    } else {
                        ball.removeShapeFromGameRoot();
                        ballList.remove(i--);
                    }
                }
                ball.collideWithPaddle(paddle);

                ball.collideWithBrickSet(brickSet, powerUpList);

                ball.setX(ball.getX() + ball.getVx());
                ball.setY(ball.getY() + ball.getVy());
                ball.getShape().setCenterX(ball.getX());
                ball.getShape().setCenterY(ball.getY());
            }
        }

    }

    public void update() {
        // none
    }
}
