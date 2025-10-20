package arkanoid.source.code.brick;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y) {
        super(x, y);
        shape.setFill(Color.YELLOW);
        hitPoints = 1;
    }
}
