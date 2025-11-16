package arkanoid.source.code.gameplay.gameobject.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gamestatus.InGameStatus;
import arkanoid.source.code.gameplay.gameobject.RectangleGameObject;
import arkanoid.source.code.gameplay.gameobject.powerup.PowerUpList;
import arkanoid.source.code.graphic.Texture;
import javafx.application.Platform;

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
            brickSet.loseBrick();
            Platform.runLater(() -> {
                this.removeShapeFromGameRoot();
                InGameStatus.setScore(InGameStatus.getScore() + score);
                powerUpList.createPowerUp(this);
            });
        } else {
            Platform.runLater(() -> {
                Texture.applyTextureToBrick(shape, hitPoints);
            });
        }
    }

    public void updateLogic() {
    }

    public void updateVisual() {
    }

    // reset HP
    public void reset() {
        switch (this) {
            case NormalBrick normalBrick -> {
                hitPoints = Config.NORMAL_BRICK_HP;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, hitPoints);
                });
            }
            case HardBrick hardBrick -> {
                hitPoints = Config.HARD_BRICK_HP;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, hitPoints);
                });
            }
            case RegenerativeBrick regenerativeBrick -> {
                hitPoints = Config.REGENERATIVE_BRICK_HP;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, hitPoints);
                });
                regenerativeBrick.setHitMoment(null);
            }
            case UnbreakableBrick unbreakableBrick -> {
                hitPoints = Integer.MAX_VALUE;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, 6);
                });
            }
            case ResonanceBrick resonanceBrick -> {
                hitPoints = 1;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, 7);
                });
            }
            case ExplosiveBrick explosiveBrick -> {
                hitPoints = 2;
                Platform.runLater(() -> {
                    Texture.applyTextureToBrick(shape, 8);
                });
            }
            default -> {
            }
        }
    }
}