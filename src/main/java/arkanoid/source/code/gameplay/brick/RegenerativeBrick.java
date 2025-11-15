package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.graphic.Texture;
import javafx.application.Platform;

import java.time.Duration;
import java.time.Instant;

public class RegenerativeBrick extends Brick {
    private Instant hitMoment; // the last moment this brick get hit
    private static final long REGENERATE_TIME = Config.REGENERATIVE_TIME; // time taken to regenerate after the last hit

    public RegenerativeBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.REGENERATIVE_BRICK_HP;
        score = Config.REGENERATIVE_BRICK_SCORE;
        Texture.applyTextureToBrick(shape, hitPoints);
        hitMoment = null;
    }

    public Instant getHitMoment() {
        return hitMoment;
    }

    public void setHitMoment(Instant hitMoment) {
        this.hitMoment = hitMoment;
    }

    // check if brick is at full HP
    private boolean isAtFullHealth() {
        return hitPoints == Config.REGENERATIVE_BRICK_HP;
    }

    // specialized get hit method
    public void getHit(int hitPointsLoss, BrickSet brickSet, PowerUpList powerUpList) {
        super.getHit(hitPointsLoss, brickSet, powerUpList);
        hitMoment = Instant.now(); // store the last get hit moment
    }

    // regenerate HP
    public void regenerate() {
        if (isDestroyed() || isAtFullHealth()) {
            hitMoment = null;
            return;
        }
        if (Duration.between(hitMoment, Instant.now()).getSeconds() >= REGENERATE_TIME) {
            Platform.runLater(() -> {
                Texture.playHealAnimation(x + width / 2, y + height / 2);
            });
            hitPoints += 1;
            Platform.runLater(() -> {
                Texture.applyTextureToBrick(shape, hitPoints);
            });
            hitMoment = Instant.now();
        }
    }
}
