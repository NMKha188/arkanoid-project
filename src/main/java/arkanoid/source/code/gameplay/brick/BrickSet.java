package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import java.io.InputStream;
import java.util.Scanner;

public class BrickSet {
    // number of rows and number of bricks each row
    private final int BRICKS_ROW = Config.BRICKS_ROW;
    private final int BRICKS_PER_ROW = Config.BRICKS_PER_ROW;
    // a matrix contains all Brick objects
    private final Brick[][] brickSet = new Brick[BRICKS_ROW][BRICKS_PER_ROW];

    // getter setter BEGIN
    public int getBricksRow() {
        return BRICKS_ROW;
    }

    public int getBricksPerRow() {
        return BRICKS_PER_ROW;
    }

    public Brick[][] getBrickSet() {
        return brickSet;
    }

    // get a particular brick at (i, j) in the matrix
    public Brick getOneBrickAt(int i, int j) {
        if (i < 0 || i >= BRICKS_ROW || j < 0 || j >= BRICKS_PER_ROW) {
            return null;
        }
        return brickSet[i][j];
    }

    private static Brick constructBrick(double x, double y, int type) {
        Brick newBrick = null;
        switch (type) {
            case 1:
                newBrick = new NormalBrick(x, y);
                break;
            case 2:
                newBrick = new HardBrick(x, y);
            default:
        }
        return newBrick;
    }

    /*
    read 2-dimensional array data from a file path, turn that data into List of Brick objects
    using title map method
     */
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
}
