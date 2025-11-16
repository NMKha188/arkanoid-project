package arkanoid.source.code.gameplay.gameobject.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.graphic.Texture;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.NORMAL_BRICK_HP;
        score = Config.NORMAL_BRICK_SCORE;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}
