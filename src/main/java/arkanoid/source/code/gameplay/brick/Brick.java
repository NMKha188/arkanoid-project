package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.RectangleGameObject;
import arkanoid.source.code.gameplay.Texture;
import arkanoid.source.code.gameplay.powerup.PowerUpList;
import javafx.scene.paint.Color;

public abstract class Brick extends RectangleGameObject {
    // brick HP
    protected int hitPoints;

    // constructor
    public Brick(double x, double y) {
        super(x, y, Config.BRICK_WIDTH, Config.BRICK_HEIGHT);
        shape.setStroke(Color.BLACK);
    }

    // getter setter BEGIN
    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    // getter setter END

    // brick has been destroyed by ball
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    // get hit by ball, increase game score and may create power up when destroyed
    public void getHit(int hitPointsLoss, PowerUpList powerUpList) {
        hitPoints -= hitPointsLoss;
        if (isDestroyed()) {
            shape.setVisible(false);
            InGameStatus.setScore(InGameStatus.getScore() + 10);
            powerUpList.createPowerUp(this);
        } else {
            if (!(this instanceof UnbreakableBrick)) {
                Texture.applyTextureToBrick(this.shape, this.hitPoints);
            }
        }
    }

    public void update() {
        //blank
    }
}
