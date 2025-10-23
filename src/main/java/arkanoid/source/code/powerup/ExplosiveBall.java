package arkanoid.source.code.powerup;

import arkanoid.source.code.brick.Brick;
import arkanoid.source.code.brick.BrickSet;
import javafx.scene.paint.Color;
import java.time.Duration;
import java.time.Instant;

public class ExplosiveBall extends PowerUp {
    private static boolean inExplosiveMode = false;

    private static final int PROBABILITY = 99; // probability of getting power up
    private static final long DURATION = 10;

    private static final int[] di = {0, -1, 0, 1, 0, -1, 1, -1, 1};
    private static final int[] dj = {0, 0, -1, 0, 1, -1, -1, 1, 1};

    public ExplosiveBall(double x, double y) {
        super(x, y);
        shape.setFill(Color.PURPLE);
    }

    // getter setter BEGIN
    public static boolean isInExplosiveMode() {
        return inExplosiveMode;
    }

    public static int getProbability() {
        return PROBABILITY;
    }

    public static long getDuration() {
        return DURATION;
    }
    // getter setter END

    public boolean onDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() < DURATION;
    }

    public void applyEffect(Object o) {
        inExplosiveMode = true;
    }

    public static void explosiveDamage(BrickSet brickSet, int i, int j, PowerUpList powerUpList) {
        for (int t = 0; t < di.length; t++) {
            int iTemporary = i + di[t];
            int jTemporary = j + dj[t];
            Brick brick = brickSet.getOneBrickAt(iTemporary, jTemporary);
            if (brick == null || brick.getHitPoints() <= 0) {
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

    public boolean runOutOfDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() >= DURATION;
    }

    public void removeEffect(Object o) {
        inExplosiveMode = false;
    }
}
