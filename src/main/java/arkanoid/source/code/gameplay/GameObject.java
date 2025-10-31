package arkanoid.source.code.gameplay;

public interface GameObject {
    void addShapeToGameRoot();

    void removeShapeFromGameRoot();

    void update();

    void reset();
}
