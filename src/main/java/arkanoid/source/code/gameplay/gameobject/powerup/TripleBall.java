package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.GameObject;
import arkanoid.source.code.gameplay.gameobject.ball.Ball;
import arkanoid.source.code.gameplay.gameobject.ball.BallList;
import arkanoid.source.code.graphic.Texture;
import javafx.application.Platform;

import java.util.ArrayList;

public class TripleBall extends PowerUp {
    public TripleBall(double x, double y) {
        super(x, y, Config.EXPLOSIVE_BALL_PROBABILITY, Config.EXPLOSIVE_BALL_DURATION);
        Texture.applyTextureToPowerUp(shape, Texture.PowerUpType.TRIPLEBALL);
    }

    public void applyEffect(GameObject o) {
        BallList ballList = (BallList) o;
        if (!ballList.isReleased()) {
            return;
        }
        ArrayList<Ball> newBalls = new ArrayList<>();
        for (Ball ball : ballList.getBallList()) {
            splitBall(ball, newBalls);
        }
        for (Ball ball : newBalls) {
            ballList.addBallToList(ball);
            Platform.runLater(() -> {
                ball.addShapeToGameRoot();
            });
        }
        newBalls.clear();
    }

    // split current ball to create 2 new balls and add to newBalls (temporary list)
    private void splitBall(Ball ball, ArrayList<Ball> newBalls) {
        double Vx = ball.getVx();
        double Vy = ball.getVy();

        double VxLeft = Vx - 0.25;
        double VyLeft = Math.sqrt(Math.pow(ball.getBallSpeed(), 2) - Math.pow(VxLeft, 2)) * (Vy / Math.abs(Vy));
        newBalls.add(new Ball(ball, VxLeft, VyLeft));

        double VxRight = Vx + 0.25;
        double VyRight = Math.sqrt(Math.pow(ball.getBallSpeed(), 2) - Math.pow(VxRight, 2)) * (Vy / Math.abs(Vy));
        newBalls.add(new Ball(ball, VxRight, VyRight));
    }

    public void removeEffect(GameObject o) {
    }
}
