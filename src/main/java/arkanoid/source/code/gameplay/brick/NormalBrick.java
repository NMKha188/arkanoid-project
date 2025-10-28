package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.graphic.Texture;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y) {
        super(x, y);
        hitPoints = 1;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
