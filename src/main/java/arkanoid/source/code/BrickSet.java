package arkanoid.source.code;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickSet {
    private static final int BRICK_ROW = 18;
    private static final int BRICKS_EACH_ROW = 29;

    private final ArrayList<Brick> brickSet = new ArrayList<>();

    // getter setter BEGIN
    public static int getBricksEachRow() {
        return BRICKS_EACH_ROW;
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
                    int number = scanner.nextInt();
                    Brick brick = new Brick(j * Brick.getBrickWidth(), i * Brick.getBrickHeight(), number);
                    brickSet.add(brick);
                }
            }
            scanner.close();
        }
    }

    public ArrayList<Brick> getBrickSet() {
        return this.brickSet;
    }
}
