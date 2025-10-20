package arkanoid.source.code.brick;

import arkanoid.source.code.InGameLogic;
import arkanoid.source.code.InGameStatus;
import arkanoid.source.code.powerup.PowerUpList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Brick {
    // brick location and size
    protected double x;
    protected double y;
    protected static final double BRICK_WIDTH = InGameLogic.getScreenWidth() / BrickSet.getBricksEachRow();
    protected static final double BRICK_HEIGHT = BRICK_WIDTH / 2;
    protected final Rectangle shape;
    // brick HP
    protected int hitPoints;

    public Brick(double x, double y) {
        this.x = x;
        this.y = y;
        shape = new Rectangle(this.x, this.y, BRICK_WIDTH, BRICK_HEIGHT);
        shape.setStroke(Color.BLACK);
    }

    // getter setter BEGIN
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static double getBrickWidth() {
        return BRICK_WIDTH;
    }

    public static double getBrickHeight() {
        return BRICK_HEIGHT;
    }

    public Rectangle getShape() {
        return shape;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    // getter setter END

    public void getHit(int hitPointsLoss, PowerUpList powerUpList) {
        hitPoints -= hitPointsLoss;
        if (hitPoints == 2) {
            shape.setFill(Color.DARKORANGE);
        } else if (hitPoints == 1) {
            shape.setFill(Color.YELLOW);
        } else if (hitPoints <= 0) {
            this.setVisibility(false);
            powerUpList.createPowerUp(this);
            InGameStatus.setScore(InGameStatus.getScore() + 1);
        }
    }

    public void setVisibility(boolean isShowed) {
        shape.setVisible(isShowed);
    }
}
