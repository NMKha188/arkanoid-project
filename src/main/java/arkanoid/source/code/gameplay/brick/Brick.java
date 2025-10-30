package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.RectangleGameObject;
import arkanoid.source.code.graphic.Texture;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.scene.paint.Color;

public abstract class Brick extends RectangleGameObject {
    // brick HP
    protected int hitPoints;

    // constructor
    public Brick(double x, double y) {
        super(x, y, Config.BRICK_WIDTH, Config.BRICK_HEIGHT);
    }

    // getter setter BEGIN
    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    // getter setter END

    // check if brick has been destroyed by ball
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    // get hit by ball, increase game score and may create power up when destroyed
    public void getHit(int hitPointsLoss, PowerUpList powerUpList) {
        if (this instanceof UnbreakableBrick) {
            return;
        }
        hitPoints -= hitPointsLoss;
        if (isDestroyed()) {
            this.removeShapeFromGameRoot();
            InGameStatus.setScore(InGameStatus.getScore() + 10);
            powerUpList.createPowerUp(this);
        } else {
            Texture.applyTextureToBrick(shape, hitPoints);
        }
    }

    public void update() {
        // none
    }
}
