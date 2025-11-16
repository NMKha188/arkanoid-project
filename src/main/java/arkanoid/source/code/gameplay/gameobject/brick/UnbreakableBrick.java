package arkanoid.source.code.gameplay.gameobject.brick;

import arkanoid.source.code.graphic.Texture;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick(double x, double y) {
        super(x, y);
        hitPoints = Integer.MAX_VALUE;
        Texture.applyTextureToBrick(shape, 6);
    }
}
