package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.graphic.Texture;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.NORMAL_BRICK_HP;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
