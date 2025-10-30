package arkanoid.source.code.gameplay.powerup;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameStatus;
import javafx.scene.paint.Color;

public class Life extends PowerUp {
    public Life(double x, double y) {
        super(x, y, Config.LIFE_PROBABILITY);
        shape.setFill(Color.PINK);
    }

    public void applyEffect(GameObject o) {
        InGameStatus.recoverLife();
    }

    public void removeEffect(GameObject o) {
        // none
    }
}
