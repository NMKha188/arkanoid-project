package arkanoid.source.code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick {
    // brick location and size
    private double x;
    private double y;
    private static final double BRICK_WIDTH = Main.getScreenWidth() / BrickSet.getBricksEachRow();
    private static final double BRICK_HEIGHT = BRICK_WIDTH / 2;
    private final Rectangle brick;
    // brick type
    private int type;
    // brick HP
    private int hitPoints;

    public Brick(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.brick = new Rectangle(this.x, this.y, BRICK_WIDTH, BRICK_HEIGHT);
        this.brick.setStroke(Color.BLACK);
        this.type = type;
        switch (type) {
            case 1:
                this.hitPoints = 1;
                this.brick.setFill(Color.RED);
                this.brick.setVisible(true);
                break;
            default:
                this.hitPoints = 0;
                this.brick.setVisible(false);
        }
    }

    // getter setter BEGIN
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
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

    public Rectangle getBrick() {
        return this.brick;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setVisibility(boolean isShowed) {
        this.brick.setVisible(isShowed);
    }
    // getter setter END
}
