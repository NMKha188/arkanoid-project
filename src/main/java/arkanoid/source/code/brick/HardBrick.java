package arkanoid.source.code.brick;

import javafx.scene.paint.Color;

public class HardBrick extends Brick {
    public HardBrick(double x, double y) {
        super(x, y);
        shape.setFill(Color.RED);
        hitPoints = 3;
    }
}
