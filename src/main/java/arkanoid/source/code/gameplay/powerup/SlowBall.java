package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.ball.Ball;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.ball.BallList;
import javafx.scene.paint.Color;

public class SlowBall extends PowerUp {
    private static boolean alreadyApplyEffect = false; // prevent over applying effect
    private static boolean alreadyRemoveEffect = false; // prevent over removing effect

    private final double SLOW_RATIO = Config.SLOW_RATIO; // slow ratio

    public SlowBall(double x, double y) {
        super(x, y, Config.SLOW_BALL_PROBABILITY, Config.SLOW_BALL_DURATION);
        shape.setFill(Color.GRAY);
    }

    public void applyEffect(GameObject o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;

            BallList ballList = (BallList) o;
            for (Ball ball :  ballList.getBallList()) {
                ball.setBallSpeed(ball.getBallSpeed() * SLOW_RATIO);
                ball.setVx(ball.getVx() * SLOW_RATIO);
                ball.setVy(ball.getVy() * SLOW_RATIO);
                ball.setMaxVx(ball.getMaxVx() * SLOW_RATIO);
                ball.setChangeVx(ball.getChangeVx() * SLOW_RATIO);
            }
        }
    }

    public void removeEffect(GameObject o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;

            BallList ballList = (BallList) o;
            for (Ball ball :  ballList.getBallList()) {
                ball.setBallSpeed(ball.getBallSpeed() / SLOW_RATIO);
                ball.setVx(ball.getVx() / SLOW_RATIO);
                ball.setVy(ball.getVy() / SLOW_RATIO);
                ball.setMaxVx(ball.getMaxVx() / SLOW_RATIO);
                ball.setChangeVx(ball.getChangeVx() / SLOW_RATIO);
            }
        }
    }
}
