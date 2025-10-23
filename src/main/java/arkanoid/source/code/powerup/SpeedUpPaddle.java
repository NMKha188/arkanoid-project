package arkanoid.source.code.powerup;

import javafx.scene.paint.Color;
import arkanoid.source.code.Paddle;
import java.time.Duration;
import java.time.Instant;

public class SpeedUpPaddle extends PowerUp {
    private static boolean alreadyApplyEffect = false;
    private static boolean alreadyRemoveEffect = false;
    private static final int PROBABILITY = 49; // 20% chance
    private static final long DURATION = 10;
    private static final double SPEED_UP = 2;

    public SpeedUpPaddle(double x, double y) {
        super(x, y);
        shape.setFill(Color.BLUE);
    }

    // getter setter BEGIN
    public static int getProbability() {
        return PROBABILITY;
    }
    // getter setter END

    public boolean onDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() < DURATION;
    }

    public void applyEffect(Object o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;
            ((Paddle) o).setPaddleSpeed(((Paddle) o).getSpeed() + SPEED_UP);
        }
    }

    public boolean runOutOfDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() >= DURATION;
    }

    public void removeEffect(Object o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;
            ((Paddle) o).setPaddleSpeed(((Paddle) o).getSpeed() - SPEED_UP);
        }
    }
}
