package arkanoid.source.code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick {
    // brick location and size
    private double x;
    private double y;
    private static final double BRICK_WIDTH = Main.getScreenWidth() / BrickSet.getBricksEachRow();
    private static final double BRICK_HEIGHT = BRICK_WIDTH / 2;
    private final Rectangle shape;
    // brick type
    private int type;
    // brick HP
    private int hitPoints;

    public Brick(double x, double y, int type) {
        this.x = x;
        this.y = y;
        shape = new Rectangle(this.x, this.y, BRICK_WIDTH, BRICK_HEIGHT);
        shape.setStroke(Color.BLACK);
        this.type = type;
        switch (type) {
            case 1:
                hitPoints = 1;
                shape.setFill(Color.YELLOW);
                shape.setVisible(true);
                break;
            case 2:
                hitPoints = 3;
                shape.setFill(Color.RED);
                shape.setVisible(true);
                break;
            default:

        }
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void getHit(int hitPointsLoss) {
        hitPoints -= hitPointsLoss;
        if (hitPoints == 2) {
            shape.setFill(Color.DARKORANGE);
        } else if (hitPoints == 1) {
            shape.setFill(Color.YELLOW);
        } else if (hitPoints <= 0) {
            this.setVisibility(false);
        }
    }

    public void setVisibility(boolean isShowed) {
        shape.setVisible(isShowed);
    }
    // getter setter END
}
