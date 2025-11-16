package arkanoid.source.code.gameplay.gameobject.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.graphic.Texture;

public class HardBrick extends Brick {
    public HardBrick(double x, double y) {
        super(x, y);
        hitPoints = Config.HARD_BRICK_HP;
        score = Config.HARD_BRICK_SCORE;
        Texture.applyTextureToBrick(shape, hitPoints);
    }
}