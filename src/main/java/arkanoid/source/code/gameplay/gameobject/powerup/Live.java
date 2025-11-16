package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.gameobject.GameObject;
import arkanoid.source.code.gameplay.gamestatus.InGameStatus;
import arkanoid.source.code.graphic.Texture;

public class Live extends PowerUp {
    public Live(double x, double y) {
        super(x, y, Config.LIVE_PROBABILITY);
        Texture.applyTextureToPowerUp(shape, Texture.PowerUpType.LIVE);
    }

    public void applyEffect(GameObject o) {
        if (InGameStatus.getLives() < 5) {
            InGameStatus.recoverLife();
        }
    }

    public void removeEffect(GameObject o) {
    }
}
