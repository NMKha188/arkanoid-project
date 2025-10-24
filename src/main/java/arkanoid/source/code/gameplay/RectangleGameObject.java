package arkanoid.source.code.gameplay;

import javafx.scene.shape.Rectangle;

public abstract class RectangleGameObject implements GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Rectangle shape;

    // constructor
    public RectangleGameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shape = new Rectangle(this.x, this.y, this.width, this.height);
    }

    // getter setter BEGIN
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        shape.setX(this.x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        shape.setY(this.y);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        shape.setWidth(this.width);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        shape.setHeight(this.height);
    }

    public Rectangle getShape() {
        return shape;
    }
    // getter setter END
}
