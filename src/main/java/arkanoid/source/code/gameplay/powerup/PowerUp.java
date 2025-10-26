package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.Paddle;
import arkanoid.source.code.gameplay.RectangleGameObject;
import javafx.scene.shape.Shape;

import java.time.Duration;
import java.time.Instant;

public abstract class PowerUp extends RectangleGameObject {
    // probability of getting power up and duration
    protected int probability;
    protected long duration;
    // falling speed
    protected double fallingSpeed = Config.POWER_UP_FALLING_SPEED;
    // the moment power up is caught by paddle and start applying effect
    protected Instant effectStartTime;

    // constructor
    public PowerUp(double x, double y, int probability, long duration) {
        super(x, y, Config.POWER_UP_WIDTH, Config.POWER_UP_HEIGHT);
        this.probability = probability;
        this.duration = duration;
        this.effectStartTime = null;
    }

    // getter setter BEGIN
    public int getProbability() {
        return probability;
    }

    public long getDuration() {
        return duration;
    }

    public double getFallingSpeed() {
        return fallingSpeed;
    }

    public Instant getEffectStartTime() {
        return effectStartTime;
    }
    // getter setter END

    public void addShapeToRoot() {
        InGameLogic.getRoot().getChildren().add(shape);
    }

    public void removeShapeFromRoot() {
        InGameLogic.getRoot().getChildren().remove(shape);
    }

    public void update() {
        y += fallingSpeed;
        shape.setY(y);
    }

    public boolean isFallenToBottom() {
        return effectStartTime == null && y >= Config.EXTRA / 4 + InGameLogic.getGameplayScreenHeight();
    }

    public void caughtByPaddle(Paddle paddle) {
        if (effectStartTime == null && Shape.intersect(shape, paddle.getShape()).getBoundsInLocal().getHeight() > 0) {
            effectStartTime = Instant.now();
            shape.setVisible(false);
        }
    }

    public boolean onDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() < duration;
    }

    public abstract void applyEffect(GameObject o);

    public boolean runOutOfDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() >= duration;
    }

    public abstract void removeEffect(GameObject o);
}
