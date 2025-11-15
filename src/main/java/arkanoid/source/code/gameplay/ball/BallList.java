package arkanoid.source.code.gameplay.ball;

import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.paddle.Paddle;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.sound.Sound;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BallList implements GameObject {
    private final ArrayList<Ball> ballList;
    private boolean released; // check if ball has been released or stick to the paddle
    private final Line directionLine;

    public BallList() {
        ballList = new ArrayList<>();
        Ball firstBall = new Ball();
        ballList.add(firstBall);
        released = false;
        directionLine = new Line();
        directionLine.setStrokeWidth(5);
        directionLine.setStroke(Color.web("#B74127"));
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

    public void addDirectionLineToGameRoot() {
        InGameLogic.getRoot().getChildren().add(directionLine);
    }

    public void hideDirectionLine() {
        directionLine.setVisible(false);
    }

    private void showDirectionLine() {
        directionLine.setVisible(true);
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

    public void updateLogic() {
        Paddle paddle = InGameLogic.getPaddle();
        PowerUpList powerUpList = InGameLogic.getPowerUpList();

        if (!released && ballList.size() == 1) {
            Ball defaultBall = ballList.getFirst();

            defaultBall.initializeVelocity(paddle);
        } else {
            for (int i = 0; i < ballList.size(); i++) {
                Ball ball = ballList.get(i);

                ball.updateLogic();

                if (ball.isAtBottom()) {
                    if (ballList.size() == 1) {
                        released = false;
                        ball.reset();
                        InGameStatus.loseLife();
                        Platform.runLater(() -> {
                            showDirectionLine();
                            Sound.playLoseLife();
                            powerUpList.reset();
                        });
                    } else {
                        Platform.runLater(() -> {
                            ball.removeShapeFromGameRoot();
                        });
                        ballList.remove(i--);
                    }
                }
            }
        }
    }

    public void updateVisual() {
        Paddle paddle = InGameLogic.getPaddle();

        if (!released && ballList.size() == 1) {
            Ball defaultBall = ballList.getFirst();

            defaultBall.updateVisual();

            double currentAngle = Math.atan2(defaultBall.getVy(), defaultBall.getVx());

            double startX = paddle.getX() + paddle.getWidth() / 2;
            double startY = paddle.getY() - defaultBall.getRadius();
            double endX = startX + 50 * Math.cos(currentAngle);
            double endY = startY + 50 * Math.sin(currentAngle);

            directionLine.setStartX(startX);
            directionLine.setStartY(startY);
            directionLine.setEndX(endX);
            directionLine.setEndY(endY);
        } else {
            for (int i = 0; i < ballList.size(); i++) {
                Ball ball = ballList.get(i);

                ball.updateVisual();
            }
        }
    }

    // clear all balls, add new default ball
    public void reset() {
        this.removeShapeFromGameRoot();
        ballList.clear();
        Ball firstBall = new Ball();
        ballList.add(firstBall);
        released = false;
        this.addShapeToGameRoot();
        showDirectionLine();
    }
}
