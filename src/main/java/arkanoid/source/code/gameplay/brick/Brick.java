package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.RectangleGameObject;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import arkanoid.source.code.graphic.Texture;

public abstract class Brick extends RectangleGameObject {
    protected int hitPoints;
    protected int score;

    public Brick(double x, double y) {
        super(x, y, Config.BRICK_WIDTH, Config.BRICK_HEIGHT);
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    // get hit by ball, increase game score and may create power up when destroyed
    public void getHit(int hitPointsLoss, BrickSet brickSet, PowerUpList powerUpList) {
        if (this instanceof UnbreakableBrick) {
            return;
        }
        hitPoints -= hitPointsLoss;
        if (isDestroyed()) {
            this.removeShapeFromGameRoot();
            brickSet.loseBrick();
            InGameStatus.setScore(InGameStatus.getScore() + score);
            powerUpList.createPowerUp(this);
        } else {
            Texture.applyTextureToBrick(shape, hitPoints);
        }
    }

    public void update() {
    }

    // reset HP
    public void reset() {
        switch (this) {
            case NormalBrick normalBrick -> {
                hitPoints = Config.NORMAL_BRICK_HP;
                Texture.applyTextureToBrick(shape, hitPoints);
            }
            case HardBrick hardBrick -> {
                hitPoints = Config.HARD_BRICK_HP;
                Texture.applyTextureToBrick(shape, hitPoints);
            }
            case RegenerativeBrick regenerativeBrick -> {
                hitPoints = Config.REGENERATIVE_BRICK_HP;
                Texture.applyTextureToBrick(shape, hitPoints);
                regenerativeBrick.setHitMoment(null);
            }
            case UnbreakableBrick unbreakableBrick -> {
                hitPoints = Integer.MAX_VALUE;
                Texture.applyTextureToBrick(shape, 6);
            }
            case ResonanceBrick resonanceBrick -> {
                hitPoints = 1;
                Texture.applyTextureToBrick(shape, 7);
            }
            case ExplosiveBrick explosiveBrick -> {
                hitPoints = 2;
                Texture.applyTextureToBrick(shape, 8);
            }
            default -> {
            }
        }
    }
}
