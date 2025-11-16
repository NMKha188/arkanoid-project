package arkanoid.source.code.gameplay.gameobject.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.powerup.PowerUpList;
import arkanoid.source.code.sound.Sound;
import arkanoid.source.code.graphic.Texture;
import javafx.application.Platform;

public class ExplosiveBrick extends Brick {
    // arrays for flood fill technique
    private static final int[] di = {-1, 0, 1, 0, -1, 1, -1, 1};
    private static final int[] dj = {0, -1, 0, 1, -1, -1, 1, 1};

    public ExplosiveBrick(double x, double y) {
        super(x, y);
        hitPoints = 2;
        Texture.applyTextureToBrick(shape, 8);
        score = Config.EXPLOSIVE_BRICK_SCORE;
    }

    // specialized get hit method
    public void getHit(int hitPointsLoss, BrickSet brickSet, PowerUpList powerUpList) {
        super.getHit(hitPointsLoss, brickSet, powerUpList);
        if (hitPoints == 1) {
            Platform.runLater(() -> {
                Texture.applyTextureToBrick(shape, 9);
            });
        }
        if (this.isDestroyed()) {
            Platform.runLater(() -> {
                Texture.playExplosionAnimation(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
                Sound.playExplosion();
            });
            int i = (int) (Math.round((y - Config.EXTRA / 4) / Config.BRICK_HEIGHT));
            int j = (int) (Math.round((x - Config.EXTRA / 2) / Config.BRICK_WIDTH));
            for (int t = 0; t < di.length; t++) {
                int iTemporary = i + di[t];
                int jTemporary = j + dj[t];
                Brick brick = brickSet.getOneBrickAt(iTemporary, jTemporary);
                if (brick == null || brick.isDestroyed()) {
                    continue;
                }
                if (t <= 3) {
                    if (brick instanceof ExplosiveBrick && brick.getHitPoints() <= 2) {
                        brickSet.addExplosiveBrick((ExplosiveBrick) brick);
                    } else {
                        brick.getHit(2, brickSet, powerUpList);
                    }
                } else {
                    if (brick instanceof ExplosiveBrick && brick.getHitPoints() <= 1) {
                        brickSet.addExplosiveBrick((ExplosiveBrick) brick);
                    } else {
                        brick.getHit(1, brickSet, powerUpList);
                    }
                }
            }
        }
    }
}