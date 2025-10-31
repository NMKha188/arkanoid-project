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
        Texture.applyTextureToBrick(shape, 7);
    }

    // specialized get hit method
    public static void resonanceHit(int hitPointsLoss, PowerUpList powerUpList, BrickSet brickSet, int i, int j) {
        Brick currentBrick = brickSet.getOneBrickAt(i, j);
        if (currentBrick instanceof ResonanceBrick && !currentBrick.isDestroyed()) {
            currentBrick.getHit(hitPointsLoss, powerUpList);
            for (int t = 0; t < di.length; t++) {
                resonanceHit(hitPointsLoss, powerUpList, brickSet, i + di[t], j + dj[t]);
            }
        }
    }
}
