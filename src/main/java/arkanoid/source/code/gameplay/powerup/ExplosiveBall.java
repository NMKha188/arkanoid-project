package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.ball.Ball;
import arkanoid.source.code.gameplay.ball.BallList;
import arkanoid.source.code.gameplay.brick.Brick;
import arkanoid.source.code.gameplay.brick.BrickSet;
import arkanoid.source.code.gameplay.brick.ResonanceBrick;
import arkanoid.source.code.graphic.Texture;
import javafx.application.Platform;

public class ExplosiveBall extends PowerUp {
    private static boolean inExplosiveMode = false; // turn on/off explosive mode
    // arrays for flood fill technique
    private static final int[] di = {0, -1, 0, 1, 0, -1, 1, -1, 1};
    private static final int[] dj = {0, 0, -1, 0, 1, -1, -1, 1, 1};

    public ExplosiveBall(double x, double y) {
        super(x, y, Config.EXPLOSIVE_BALL_PROBABILITY, Config.EXPLOSIVE_BALL_DURATION);
        Texture.applyTextureToPowerUp(shape, Texture.PowerUpType.EXPLOSIVEBALL);
    }

    public static boolean isInExplosiveMode() {
        return inExplosiveMode;
    }

    public void applyEffect(GameObject o) {
        inExplosiveMode = true;
        if (o instanceof BallList) {
            for (Ball ball : ((BallList) o).getBallList()) {
                Platform.runLater(() -> {
                    Texture.applyExplosiveTextureToBall(ball.getShape());
                });
            }
        }
    }

    // static method called to deal explosive damage
    public static void explosiveDamage(BrickSet brickSet, int i, int j, PowerUpList powerUpList) {
        for (int t = 0; t < di.length; t++) {
            int iTemporary = i + di[t];
            int jTemporary = j + dj[t];
            Brick brick = brickSet.getOneBrickAt(iTemporary, jTemporary);
            if (brick == null || brick.isDestroyed()) {
                continue;
            }
            if (t == 0) {
                brick.getHit(3, brickSet, powerUpList);
            } else if (t <= 4) {
                brick.getHit(2, brickSet, powerUpList);
            } else {
                brick.getHit(1, brickSet, powerUpList);
            }
        }
    }

    public void removeEffect(GameObject o) {
        inExplosiveMode = false;
        if (o instanceof BallList) {
            for (Ball ball : ((BallList) o).getBallList()) {
                Platform.runLater(() -> {
                    Texture.applyTextureToBall(ball.getShape());
                });
            }
        }
    }
}