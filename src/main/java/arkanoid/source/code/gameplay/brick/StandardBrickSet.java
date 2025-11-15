package arkanoid.source.code.gameplay.brick;

public class StandardBrickSet extends BrickSet {
    public Brick createBrick(double x, double y, int type) {
        return switch (type) {
            case 1 -> new NormalBrick(x, y);
            case 2 -> new HardBrick(x, y);
            case 3 -> new UnbreakableBrick(x, y);
            case 4 -> new ResonanceBrick(x, y);
            case 5 -> new ExplosiveBrick(x, y);
            case 6 -> new RegenerativeBrick(x, y);
            default -> null;
        };
    }
}
