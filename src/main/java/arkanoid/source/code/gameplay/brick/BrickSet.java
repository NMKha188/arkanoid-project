package arkanoid.source.code.gameplay.brick;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameObject;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.powerup.PowerUpList;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public abstract class BrickSet implements GameObject {
    // number of rows and number of bricks each row
    private final int BRICKS_ROW = Config.BRICKS_ROW;
    private final int BRICKS_PER_ROW = Config.BRICKS_PER_ROW;
    private final Brick[][] brickSet = new Brick[BRICKS_ROW][BRICKS_PER_ROW]; // a matrix contains all Brick objects
    private int totalNumOfBricks = 0;
    private int numOfBricksLeft;

    private final Queue<ResonanceBrick> resonanceBrickQueue = new LinkedList<>();
    private final Queue<ExplosiveBrick> explosiveBrickQueue = new LinkedList<>();

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

    public void loseBrick() {
        numOfBricksLeft--;
    }

    public boolean isClear() {
        return numOfBricksLeft == 0;
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
                    int type = scanner.nextInt();
                    if (type != 0) {
                        double x = Config.EXTRA / 2 + j * Config.BRICK_WIDTH;
                        double y = Config.EXTRA / 4 + i * Config.BRICK_HEIGHT;

                        brickSet[i][j] = createBrick(x, y, type);

                        if (brickSet[i][j] != null && !(brickSet[i][j] instanceof UnbreakableBrick)) {
                            totalNumOfBricks++;
                        }
                    }
                }
            }
            numOfBricksLeft = totalNumOfBricks;
            scanner.close();
        }
    }

    public void addResonanceBrick(ResonanceBrick resonanceBrick) {
        resonanceBrickQueue.offer(resonanceBrick);
    }

    public void addExplosiveBrick(ExplosiveBrick explosiveBrick) {
        explosiveBrickQueue.offer(explosiveBrick);
    }

    public void updateLogic() {
        PowerUpList powerUpList = InGameLogic.getPowerUpList();

        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                if (getOneBrickAt(i, j) instanceof RegenerativeBrick) {
                    ((RegenerativeBrick) getOneBrickAt(i, j)).regenerate();
                }
            }
        }
        for (int i = 0; i < 1; i++) {
            ResonanceBrick resonanceBrick = resonanceBrickQueue.poll();
            if (resonanceBrick != null && !resonanceBrick.isDestroyed()) {
                resonanceBrick.getHit(1, this, powerUpList);
            }

            ExplosiveBrick explosiveBrick = explosiveBrickQueue.poll();
            if (explosiveBrick != null && !explosiveBrick.isDestroyed()) {
                explosiveBrick.getHit(2, this, powerUpList);
            }
        }
    }

    public void updateVisual() {
    }

    // reset all brick state
    public void reset() {
        this.removeShapeFromGameRoot();
        for (int i = 0; i < BRICKS_ROW; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                Brick brick = this.getOneBrickAt(i, j);
                if (brick != null) {
                    brick.reset();
                }
            }
        }
        numOfBricksLeft = totalNumOfBricks;
        resonanceBrickQueue.clear();
        explosiveBrickQueue.clear();
        this.addShapeToGameRoot();
    }

    // abstract factory method
    protected abstract Brick createBrick(double x, double y, int type);
}