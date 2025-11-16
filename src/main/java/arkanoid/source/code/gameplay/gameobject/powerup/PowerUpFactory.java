package arkanoid.source.code.gameplay.gameobject.powerup;

import arkanoid.source.code.config.Config;

public class PowerUpFactory {
    public static PowerUp createRandomPowerUp(double x, double y) {
        int typeRoll = (int) (Math.random() * 6);

        int probabilityRoll = (int) (Math.random() * 100);

        return switch (typeRoll) {
            case 0 -> {
                if (probabilityRoll <= Config.EXPAND_PADDLE_PROBABILITY) {
                    yield new ExpandPaddle(x, y);
                }
                yield null;
            }
            case 1 -> {
                if (probabilityRoll <= Config.SPEED_UP_PADDLE_PROBABILITY) {
                    yield new SpeedUpPaddle(x, y);
                }
                yield null;
            }
            case 2 -> {
                if (probabilityRoll <= Config.SLOW_BALL_PROBABILITY) {
                    yield new SlowBall(x, y);
                }
                yield null;
            }
            case 3 -> {
                if (probabilityRoll <= Config.EXPLOSIVE_BALL_PROBABILITY) {
                    yield new ExplosiveBall(x, y);
                }
                yield null;
            }
            case 4 -> {
                if (probabilityRoll <= Config.TRIPLE_BALL_PROBABILITY) {
                    yield new TripleBall(x, y);
                }
                yield null;
            }
            case 5 -> {
                if (probabilityRoll <= Config.LIVE_PROBABILITY) {
                    yield new Live(x, y);
                }
                yield null;
            }
            default -> null;
        };
    }
}
