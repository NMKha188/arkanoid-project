package arkanoid.source.code.gameplay.ball;

import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BallList implements GameObject {
    private final ArrayList<Ball> ballList;
    private boolean released; // check if ball has been released or stick to the paddle
    private final Line velocityRepresentativeLine;

    public BallList() {
        ballList = new ArrayList<>();
        Ball firstBall = new Ball();
        ballList.add(firstBall);
        released = false;
        velocityRepresentativeLine = new Line();
        velocityRepresentativeLine.setStrokeWidth(5);
        velocityRepresentativeLine.setStroke(Color.web("#B74127"));
    }

    public ArrayList<Ball> getBallList() {
        return ballList;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public void addVelocityRepresentativeLineToGameRoot() {
        InGameLogic.getRoot().getChildren().add(velocityRepresentativeLine);
    }

    public void hideVelocityRepresentativeLine() {
        velocityRepresentativeLine.setVisible(false);
    }

    private void showVelocityRepresentativeLine() {
        velocityRepresentativeLine.setVisible(true);
    }

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

    // update all balls state
    public void update(Paddle paddle, BrickSet brickSet, PowerUpList powerUpList) {
        if (!released && ballList.size() == 1) {
            Ball defaultBall = ballList.getFirst();
            defaultBall.initializeVelocity(paddle);
            defaultBall.getShape().setCenterX(ballList.getFirst().getX());
            defaultBall.getShape().setCenterY(ballList.getFirst().getY());

            velocityRepresentativeLine.setStartX(defaultBall.getX());
            velocityRepresentativeLine.setStartY(defaultBall.getY() + defaultBall.getRadius() + paddle.getHeight() + 5);
            velocityRepresentativeLine.setEndX(defaultBall.getX() + defaultBall.getVx() * ((paddle.getWidth() / 2) / defaultBall.getMaxVx()));
            velocityRepresentativeLine.setEndY(defaultBall.getY() + defaultBall.getRadius() + paddle.getHeight() + 5);
        } else {
            for (int i = 0; i < ballList.size(); i++) {
                Ball ball = ballList.get(i);

                ball.collideWithWalls();

                if (ball.isAtBottom()) {
                    if (ballList.size() == 1) {
                        released = false;
                        showVelocityRepresentativeLine();
                        ball.setVx(0);
                        powerUpList.reset(paddle, this);
                        InGameStatus.loseLife();
                    } else {
                        ball.removeShapeFromGameRoot();
                        ballList.remove(i--);
                    }
                }

                ball.collideWithPaddle(paddle);

                ball.collideWithBrickSet(brickSet, powerUpList);

                ball.update();
            }
        }
    }

    public void update() {
    }

    // clear all balls, add new default ball
    public void reset() {
        this.removeShapeFromGameRoot();
        ballList.clear();
        Ball firstBall = new Ball();
        ballList.add(firstBall);
        released = false;
        this.addShapeToGameRoot();
    }
}
