package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;
import arkanoid.source.code.gameplay.gameobject.RectangleGameObject;
import javafx.scene.shape.Shape;

import java.time.Duration;
import java.time.Instant;

public abstract class PowerUp extends RectangleGameObject {
    protected int probability;
    protected long duration;

    protected double fallingSpeed = Config.POWER_UP_FALLING_SPEED;

    protected Instant effectStartTime; // the moment power up is caught by paddle and start applying effect

    public PowerUp(double x, double y, int probability) {
        super(x, y, Config.POWER_UP_WIDTH, Config.POWER_UP_HEIGHT);
        this.probability = probability;
        this.effectStartTime = null;
    }

    public PowerUp(double x, double y, int probability, long duration) {
        this(x, y, probability);
        this.duration = duration;
    }

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

    public void setEffectStartTime(Instant moment) {
        this.effectStartTime = moment;
    }

    public void addShapeToRoot() {
        InGameLogic.getRoot().getChildren().add(shape);
    }

    public void removeShapeFromRoot() {
        InGameLogic.getRoot().getChildren().remove(shape);
    }

    public void updateLogic() {
        y += fallingSpeed;
    }

    public void updateVisual() {
        shape.setY(y);
    }

    // check if power up has fallen to bottom
    public boolean isFallenToBottom() {
        return effectStartTime == null && y >= Config.EXTRA / 4 + InGameLogic.getGameplayScreenHeight();
    }

    // get caught by paddle
    public boolean caughtByPaddle(Paddle paddle) {
        return effectStartTime == null && Shape.intersect(shape, paddle.getShape()).getBoundsInLocal().getHeight() > 0;
    }

    // check if power up is on duration (after getting caught by paddle)
    public boolean onDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() < duration;
    }

    // check if power up has run out of duration (after getting caught by paddle)
    public boolean runOutOfDuration() {
        return effectStartTime != null && Duration.between(effectStartTime, Instant.now()).getSeconds() >= duration;
    }

    public abstract void applyEffect(GameObject o);

    public abstract void removeEffect(GameObject o);

    // same effect as removeEffect
    public void reset() {
    }
}
