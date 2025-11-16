package arkanoid.source.code.gameplay.gameobject;

public interface GameObject {
    void addShapeToGameRoot();

    void removeShapeFromGameRoot();

    void updateLogic();

    void updateVisual();

    void reset();
}
