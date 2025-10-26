package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import javafx.scene.paint.Color;
import arkanoid.source.code.gameplay.Paddle;

public class SpeedUpPaddle extends PowerUp {
    private static boolean alreadyApplyEffect = false;
    private static boolean alreadyRemoveEffect = false;
    private final double SPEED_UP_RATIO = Config.SPEED_UP_RATIO;

    public SpeedUpPaddle(double x, double y) {
        super(x, y, Config.SPEED_UP_PADDLE_PROBABILITY, Config.SPEED_UP_PADDLE_DURATION);
        shape.setFill(Color.BLUE);
    }

    public void applyEffect(GameObject o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;
            ((Paddle) o).setPaddleSpeed(((Paddle) o).getSpeed() * SPEED_UP_RATIO);
        }
    }

    public void removeEffect(GameObject o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;
            ((Paddle) o).setPaddleSpeed(((Paddle) o).getSpeed() / SPEED_UP_RATIO);
        }
    }
}
