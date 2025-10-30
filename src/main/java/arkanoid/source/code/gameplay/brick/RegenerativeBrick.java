package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.graphic.Texture;
import java.time.Duration;
import java.time.Instant;

public class RegenerativeBrick extends Brick {
    private Instant hitMoment;
    private static final long REGENERATE_TIME = Config.REGENERATIVE_TIME;

    public RegenerativeBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.REGENERATIVE_BRICK_HP;
        Texture.applyTextureToBrick(shape, hitPoints);
        hitMoment = null;
    }

    private boolean isAtFullHealth() {
        return hitPoints == 3;
    }

    public void getHit(int hitPointsLoss, PowerUpList powerUpList) {
        super.getHit(hitPointsLoss, powerUpList);
        hitMoment = Instant.now();
    }

    public void regenerate() {
        if (isDestroyed() || isAtFullHealth()) {
            hitMoment = null;
            return;
        }
        if (Duration.between(hitMoment, Instant.now()).getSeconds() >= REGENERATE_TIME) {
            hitPoints += 1;
            Texture.applyTextureToBrick(shape, hitPoints);
            hitMoment = Instant.now();
        }
    }
}
