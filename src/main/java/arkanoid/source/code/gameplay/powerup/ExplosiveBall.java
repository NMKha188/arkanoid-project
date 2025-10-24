package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.brick.Brick;
import arkanoid.source.code.gameplay.brick.BrickSet;
import javafx.scene.paint.Color;

public class ExplosiveBall extends PowerUp {
    private static boolean inExplosiveMode = false;

    private static final int[] di = {0, -1, 0, 1, 0, -1, 1, -1, 1};
    private static final int[] dj = {0, 0, -1, 0, 1, -1, -1, 1, 1};

    // constructor
    public ExplosiveBall(double x, double y) {
        super(x, y, Config.EXPLOSIVE_BALL_PROBABILITY, Config.EXPLOSIVE_BALL_DURATION);
        shape.setFill(Color.PURPLE);
    }

    // getter setter BEGIN
    public static boolean isInExplosiveMode() {
        return inExplosiveMode;
    }
    // getter setter END

    public void applyEffect(GameObject o) {
        inExplosiveMode = true;
    }

    public static void explosiveDamage(BrickSet brickSet, int i, int j, PowerUpList powerUpList) {
        for (int t = 0; t < di.length; t++) {
            int iTemporary = i + di[t];
            int jTemporary = j + dj[t];
            Brick brick = brickSet.getOneBrickAt(iTemporary, jTemporary);
            if (brick == null || brick.isDestroyed()) {
                continue;
            }
            if (t == 0) {
                brick.getHit(3, powerUpList);
            } else if (t <= 4) {
                brick.getHit(2, powerUpList);
            } else {
                brick.getHit(1, powerUpList);
            }
        }
    }

    public void removeEffect(GameObject o) {
        inExplosiveMode = false;
    }
}
