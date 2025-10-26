package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.gameplay.Texture;
import javafx.scene.paint.Color;

public class HardBrick extends Brick {
    public HardBrick(double x, double y) {
        super(x, y);
        hitPoints = 5;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
