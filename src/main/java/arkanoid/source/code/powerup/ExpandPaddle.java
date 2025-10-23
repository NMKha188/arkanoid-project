package arkanoid.source.code.powerup;

import javafx.scene.paint.Color;
import arkanoid.source.code.InGameLogic;
import arkanoid.source.code.Paddle;
import java.time.Duration;
import java.time.Instant;

public class ExpandPaddle extends PowerUp {
    private static boolean alreadyApplyEffect = false;
    private static boolean alreadyRemoveEffect = false;
    private static final int PROBABILITY = 49; // 20% chance
    private static final long DURATION = 10;
    private static final double EXPANDED_LENGTH = 80;

    public ExpandPaddle(double x, double y) {
        super(x, y);
        shape.setFill(Color.GREEN);
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
            Paddle paddle = (Paddle) o;
            paddle.setWidth(paddle.getWidth() + EXPANDED_LENGTH);
            paddle.setX(paddle.getX() - (EXPANDED_LENGTH / 2));
            if (paddle.getX() < 0) {
                paddle.setX(0);
            } else if (paddle.getX() + paddle.getWidth() > InGameLogic.getScreenWidth()) {
                paddle.setX(InGameLogic.getScreenWidth() - paddle.getWidth());
            }
        }
    }

    public boolean runOutOfDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() >= DURATION;
    }

    public void removeEffect(Object o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;
            Paddle paddle = (Paddle) o;
            paddle.setWidth(paddle.getWidth() - EXPANDED_LENGTH);
            paddle.setX(paddle.getX() + EXPANDED_LENGTH / 2);
        }
    }
}
