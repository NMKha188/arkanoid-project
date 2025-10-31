package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import java.io.InputStream;
import java.util.Scanner;

public class BrickSet implements GameObject {
    // number of rows and number of bricks each row
    private final int BRICKS_ROW = Config.BRICKS_ROW;
    private final int BRICKS_PER_ROW = Config.BRICKS_PER_ROW;
    private final Brick[][] brickSet = new Brick[BRICKS_ROW][BRICKS_PER_ROW]; // a matrix contains all Brick objects

    public int getBricksRow() {
        return BRICKS_ROW;
    }

    public int getBricksPerRow() {
        return BRICKS_PER_ROW;
    }

    public Brick[][] getBrickSet() {
        return brickSet;
    }

    // add all brick shape to game root
    public void addShapeToGameRoot() {
        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                if (this.getOneBrickAt(i, j) != null) {
                    this.getOneBrickAt(i, j).addShapeToGameRoot();
                }
            }
        }
    }

    // remove all brick shape from game root
    public void removeShapeFromGameRoot() {
        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                if (this.getOneBrickAt(i, j) != null) {
                    this.getOneBrickAt(i, j).removeShapeFromGameRoot();
                }
            }
        }
    }

    // get a particular brick at (i, j) in the matrix
    public Brick getOneBrickAt(int i, int j) {
        if (i < 0 || i >= BRICKS_ROW || j < 0 || j >= BRICKS_PER_ROW) {
            return null;
        }
        return brickSet[i][j];
    }

    // read data from a data path to create a brick set (using title map method)
    public void readData(String path) {
        InputStream inputStream = InGameLogic.class.getResourceAsStream(path);
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            for (int i = 0; i < BRICKS_ROW; i++) {
                for (int j = 0; j < BRICKS_PER_ROW; j++) {
                    if (!scanner.hasNextInt()) {
                        return;
                    }
                    double x = Config.EXTRA / 2 + j * Config.BRICK_WIDTH;
                    double y = Config.EXTRA / 4 + i * Config.BRICK_HEIGHT;
                    int type = scanner.nextInt();
                    brickSet[i][j] = constructBrick(x, y, type);
                }
            }
            scanner.close();
        }
    }

    // private method to construct new brick based on data read
    private static Brick constructBrick(double x, double y, int type) {
        Brick newBrick = null;
        switch (type) {
            case 1 -> {
                newBrick = new NormalBrick(x, y);
            }
            case 2 -> {
                newBrick = new HardBrick(x, y);
            }
            case 3 -> {
                newBrick = new UnbreakableBrick(x, y);
            }
            case 4 -> {
                newBrick = new ResonanceBrick(x, y);
            }
            case 5 -> {
                newBrick = new RegenerativeBrick(x, y);
            }
            default -> {
            }
        }
        return newBrick;
    }

    // update all brick state (not including getting hit since that method has already been called inside a ball collision method)
    public void update() {
        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                if (getOneBrickAt(i, j) instanceof RegenerativeBrick) {
                    ((RegenerativeBrick) getOneBrickAt(i, j)).regenerate();
                }
            }
        }
    }

    // reset all brick state
    public void reset() {
        this.removeShapeFromGameRoot();
        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                Brick brick = this.getOneBrickAt(i, j);
                // ADD NULL CHECK HERE
                if (brick != null) {
                    brick.reset();
                }
            }
        }
        this.addShapeToGameRoot();
    }
}
