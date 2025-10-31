package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.graphic.Texture;
import javafx.scene.paint.Color;

public class Live extends PowerUp {
    public Live(double x, double y) {
        super(x, y, Config.LIVE_PROBABILITY);
        Texture.applyTextureToPowerUp(shape, Texture.PowerUpType.LIVE);
    }

    public void applyEffect(GameObject o) {
        InGameStatus.recoverLife();
    }

    public void removeEffect(GameObject o) {
    }
}
