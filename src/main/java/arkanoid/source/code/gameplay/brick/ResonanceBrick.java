package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.graphic.Texture;

public class ResonanceBrick extends Brick {
    // arrays for flood fill technique
    private static final int[] di = {-1, 0, 1, 0, -1, 1, -1, 1};
    private static final int[] dj = {0, -1, 0, 1, -1, -1, 1, 1};

    // constructor
    public ResonanceBrick(double x, double y) {
        super(x, y);
        hitPoints = 1;
        score = Config.RESONANCE_BRICK_SCORE;
        Texture.applyTextureToBrick(shape, 7);
    }

    // specialized get hit method
    public void getHit(int hitPointsLoss, BrickSet brickSet, PowerUpList powerUpList) {
        super.getHit(hitPointsLoss, brickSet, powerUpList);
        if (this.isDestroyed()) {
            int i = (int) (Math.round((y - Config.EXTRA / 4) / Config.BRICK_HEIGHT));
            int j = (int) (Math.round((x - Config.EXTRA / 2) / Config.BRICK_WIDTH));
            for (int t = 0; t < di.length; t++) {
                int iTemporary = i + di[t];
                int jTemporary = j + dj[t];
                Brick brick = brickSet.getOneBrickAt(iTemporary, jTemporary);
                if (brick == null || brick.isDestroyed()) {
                    continue;
                }
                if (brick instanceof ResonanceBrick && !brick.isDestroyed()) {
                    brickSet.addResonanceBrick((ResonanceBrick) brick);
                }
            }
        }
    }
}
