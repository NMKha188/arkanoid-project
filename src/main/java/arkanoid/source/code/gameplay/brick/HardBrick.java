package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.graphic.Texture;

public class HardBrick extends Brick {
    public HardBrick(double x, double y) {
        super(x, y);
        hitPoints = 5;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
