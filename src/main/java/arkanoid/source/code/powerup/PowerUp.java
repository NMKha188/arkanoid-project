package arkanoid.source.code.powerup;

import arkanoid.source.code.InGameLogic;
import arkanoid.source.code.Paddle;
import arkanoid.source.code.brick.Brick;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.time.Instant;

public abstract class PowerUp {
    // power up location and size
    protected double x;
    protected double y;
    protected static final double POWER_UP_WIDTH = Brick.getBrickWidth() / 2;
    protected static final double POWER_UP_HEIGHT = POWER_UP_WIDTH / 2;
    protected final Rectangle shape;
    // falling speed
    protected static final double FALLING_SPEED = 1;
    // the time power up being caught by paddle
    protected Instant effectStartTime;

    // constructor
    public PowerUp(double x, double y) {
        this.x = x;
        this.y = y;
        this.shape = new Rectangle(this.x, this.y, POWER_UP_WIDTH, POWER_UP_HEIGHT);
        this.effectStartTime = null;
    }

    // getter setter BEGIN
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        shape.setX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        shape.setY(y);
    }

    public static double getPowerUpWidth () {
        return POWER_UP_WIDTH;
    }

    public static double getPowerUpHeight() {
        return POWER_UP_HEIGHT;
    }

    public Rectangle getShape() {
        return shape;
    }

    public static double getFallingSpeed() {
        return FALLING_SPEED;
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

    public void updatePosition() {
        y += FALLING_SPEED;
        shape.setY(y);
    }

    public boolean isFallenToBottom() {
        return effectStartTime == null && y >= InGameLogic.getScreenHeight();
    }

    public void caughtByPaddle(Paddle paddle) {
        if (effectStartTime == null && Shape.intersect(shape, paddle.getShape()).getBoundsInLocal().getHeight() > 0) {
            effectStartTime = Instant.now();
            shape.setVisible(false);
        }
    }

    public abstract boolean onDuration();

    public abstract void applyEffect(Object o);

    public abstract boolean runOutOfDuration();

    public abstract void removeEffect(Object o);
}
