package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.Ball;
import arkanoid.source.code.gameplay.GameObject;
import javafx.scene.paint.Color;

public class SlowBall extends PowerUp {
    private static boolean alreadyApplyEffect = false;
    private static boolean alreadyRemoveEffect = false;
    private final double SLOW_RATIO = Config.SLOW_RATIO;

    public SlowBall(double x, double y) {
        super(x, y, Config.SLOW_BALL_PROBABILITY, Config.SLOW_BALL_DURATION);
        shape.setFill(Color.GRAY);
    }

    public void applyEffect(GameObject o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;
            Ball ball = (Ball) o;
            ball.setBallSpeed(ball.getBallSpeed() * SLOW_RATIO);
            ball.setVx(ball.getVx() * SLOW_RATIO);
            ball.setVy(ball.getVy() * SLOW_RATIO);
            ball.setMaxVx(ball.getMaxVx() * SLOW_RATIO);
            ball.setChangeVx(ball.getChangeVx() * SLOW_RATIO);
        }
    }

    public void removeEffect(GameObject o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;
            Ball ball = (Ball) o;
            ball.setBallSpeed(ball.getBallSpeed() / SLOW_RATIO);
            ball.setVx(ball.getVx() / SLOW_RATIO);
            ball.setVy(ball.getVy() / SLOW_RATIO);
            ball.setMaxVx(ball.getMaxVx() / SLOW_RATIO);
            ball.setChangeVx(ball.getChangeVx() / SLOW_RATIO);
        }
    }
}
