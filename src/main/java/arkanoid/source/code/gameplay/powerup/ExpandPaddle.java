package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import javafx.scene.paint.Color;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.Paddle;

public class ExpandPaddle extends PowerUp {
    private static boolean alreadyApplyEffect = false;
    private static boolean alreadyRemoveEffect = false;
    private final double EXPANDED_RATIO = Config.EXPAND_PADDLE_EXPANDED_RATIO;

    public ExpandPaddle(double x, double y) {
        super(x, y, Config.EXPAND_PADDLE_PROBABILITY, Config.EXPAND_PADDLE_DURATION);
        shape.setFill(Color.GREEN);
    }

    public void applyEffect(GameObject o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;
            Paddle paddle = (Paddle) o;
            double expandedLength = Config.PADDLE_WIDTH * (EXPANDED_RATIO - 1);
            paddle.setWidth(paddle.getWidth() + expandedLength);
            paddle.setX(paddle.getX() - (expandedLength / 2));
            if (paddle.getX() < 0) {
                paddle.setX(0);
            } else if (paddle.getX() + paddle.getWidth() > InGameLogic.getGameplayScreenWidth()) {
                paddle.setX(InGameLogic.getGameplayScreenWidth() - paddle.getWidth());
            }
        }
    }

    public void removeEffect(GameObject o) {
        if (!alreadyRemoveEffect) {
            alreadyApplyEffect = false;
            alreadyRemoveEffect = true;
            Paddle paddle = (Paddle) o;
            double expandedLength = Config.PADDLE_WIDTH * (EXPANDED_RATIO - 1);
            paddle.setWidth(paddle.getWidth() - expandedLength);
            paddle.setX(paddle.getX() + expandedLength / 2);
        }
    }
}
