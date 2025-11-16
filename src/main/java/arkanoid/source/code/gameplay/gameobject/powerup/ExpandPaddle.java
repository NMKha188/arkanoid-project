package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.GameObject;
import arkanoid.source.code.graphic.Texture;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;

public class ExpandPaddle extends PowerUp {
    private static boolean alreadyApplyEffect = false; // prevent over applying effect
    private static boolean alreadyRemoveEffect = false; // prevent over removing effect

    private final double EXPANDED_RATIO = Config.EXPAND_PADDLE_EXPANDED_RATIO; // expand ratio

    public ExpandPaddle(double x, double y) {
        super(x, y, Config.EXPAND_PADDLE_PROBABILITY, Config.EXPAND_PADDLE_DURATION);
        Texture.applyTextureToPowerUp(shape, Texture.PowerUpType.EXPAND);
    }

    public void applyEffect(GameObject o) {
        if (!alreadyApplyEffect) {
            alreadyApplyEffect = true;
            alreadyRemoveEffect = false;
            Paddle paddle = (Paddle) o;
            double expandedLength = Config.PADDLE_WIDTH * (EXPANDED_RATIO - 1);
            paddle.setWidth(paddle.getWidth() + expandedLength);
            paddle.setX(paddle.getX() - (expandedLength / 2));
            if (paddle.getX() < Config.EXTRA / 2) {
                paddle.setX(Config.EXTRA / 2);
            } else if (paddle.getX() + paddle.getWidth() > Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth()) {
                paddle.setX(Config.EXTRA / 2 + InGameLogic.getGameplayScreenWidth() - paddle.getWidth());
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
