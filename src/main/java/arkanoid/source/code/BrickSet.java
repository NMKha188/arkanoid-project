package arkanoid.source.code;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickSet {
    private static final int BRICK_ROW = 10;
    private static final int BRICKS_EACH_ROW = 12;

    private final Brick[][] brickSet = new Brick[BRICK_ROW][BRICKS_EACH_ROW];

    // getter setter BEGIN
    public static int getBrickRow() {
        return BRICK_ROW;
    }

    public static int getBricksEachRow() {
        return BRICKS_EACH_ROW;
    }

    public Brick[][] getBrickSet() {
        return this.brickSet;
    }

    public Brick getOneBrickAt(int i, int j) {
        return this.brickSet[i][j];
    }
    // getter setter END

    /*
    read 2-dimensional array data from a file path, turn that data into List of Brick objects
    using title map method
     */
    public void readData(String path) {
        InputStream inputStream = Main.class.getResourceAsStream(path);
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            for (int i = 0; i < BRICK_ROW; i++) {
                for (int j = 0; j < BRICKS_EACH_ROW; j++) {
                    if (!scanner.hasNextInt()) {
                        return;
                    }
                    int number = scanner.nextInt();
                    if (number == 0) {
                        continue;
                    }
                    Brick brick = new Brick(j * Brick.getBrickWidth(), i * Brick.getBrickHeight(), number);
                    brickSet[i][j] = brick;
                }
            }
            scanner.close();
        }
    }
}
