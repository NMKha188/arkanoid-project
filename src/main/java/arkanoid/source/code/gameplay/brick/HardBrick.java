package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.graphic.Texture;

public class HardBrick extends Brick {
    public HardBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.HARD_BRICK_HP;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
