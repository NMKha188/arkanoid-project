package arkanoid.source.code;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;


import java.util.LinkedList;

public class Brick {
    private double x;
    private double y;
    private double width;
    private double height;
    private double centerX;
    private double centerY;
    LinkedList<Rectangle> basic_bricks = new LinkedList<Rectangle>();
    LinkedList<blockBrick> block_brick = new LinkedList<blockBrick>();

    public class blockBrick {
        Rectangle brick;
        int times;

        blockBrick(Rectangle brick, int times) {
            this.brick = brick;
            this.times = times;
        }
    }

    public Brick(double SCREEN_WIDTH) {
        this.x = 0;
        this.y = 60;
        this.width = SCREEN_WIDTH / 5;
        this.height = 30;
        this.centerX = width / 2;
        this.centerY = height / 2;
    }

    public int[][] map1 = {
            {0, 0, 1, 2, 0, 1, 0, 2, 0, 1},
            {1, 2, 0, 0, 1, 2, 1, 0, 2, 0},
            {0, 1, 2, 1, 0, 0, 2, 1, 0, 2},
            {2, 0, 0, 1, 2, 1, 0, 2, 1, 0},
            {1, 0, 2, 0, 1, 0, 2, 1, 0, 2},
            {0, 2, 1, 0, 2, 1, 0, 0, 1, 2},
            {2, 1, 0, 2, 1, 0, 2, 1, 0, 0},
            {0, 0, 2, 1, 0, 2, 1, 0, 2, 1},
            {1, 2, 0, 0, 1, 0, 2, 1, 0, 2},
            {0, 1, 2, 1, 0, 2, 1, 0, 2, 0}
    };
    public int[][][] maps = {map1};

    public void khoiTao(Pane root) {
        for (int k = 0; k < maps.length; k++) {
            for (int i = 0; i < maps[k].length; i++) {
                for (int j = 0; j < maps[k][i].length; j++) {
                    Rectangle rec = new Rectangle();
                    rec.setWidth(width);
                    rec.setHeight(height);
                    rec.setX(x + j * width);
                    rec.setY(y + i * height);
                    if (maps[k][i][j] == 1) {
                        rec.setFill(Color.PINK);
                        rec.setStroke(Color.BLACK);
                        rec.setStrokeWidth(5);
                        root.getChildren().add(rec);
                        basic_bricks.add(rec);
                    } else if (maps[k][i][j] == 2) {
                        rec.setFill(Color.RED);
                        rec.setStroke(Color.BLACK);
                        rec.setStrokeWidth(5);
                        root.getChildren().add(rec);
                        block_brick.add(new blockBrick(rec, 3));
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public void vaCham(Ball ball, Pane root) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(ball.getX(), ball.getY(), ball.getRadius());

        Rectangle touchedBrick = null;
        blockBrick touchedBlock = null;

        if (ball.getReleasedState()) {
            for (Rectangle brick : basic_bricks) {
                Shape inter = Shape.intersect(ball.getBall(), brick);
                if (inter.getBoundsInLocal().getWidth() > 0 && inter.getBoundsInLocal().getHeight() > 0) {
                    if (ball.getX() >= brick.getX() && ball.getX() <= brick.getX() + brick.getWidth()) {
                        ball.setVy(-ball.getVy());
                        touchedBrick = brick;
                    } else if (ball.getY() >= brick.getY() && ball.getY() <= brick.getY() + brick.getHeight()) {
                        ball.setVx(-ball.getVx());
                        touchedBrick = brick;
                    }

                }
            }

            if (touchedBrick == null) {
                for (blockBrick br : block_brick) {
                    Shape inter = Shape.intersect(circle, br.brick);
                    if (inter.getBoundsInLocal().getWidth() > 0 && inter.getBoundsInLocal().getHeight() > 0) {
                        if (ball.getX() >= br.brick.getX() && ball.getX() <= br.brick.getX() + br.brick.getWidth()) {
                            ball.setVy(-ball.getVy());
                            br.times--;
                            if (br.times == 0) {
                                touchedBlock = br;
                            }
                            break;
                        } else if (ball.getY() >= br.brick.getY() && ball.getY() <= br.brick.getY() + br.brick.getHeight()) {
                            ball.setVx(-ball.getVx());
                            br.times--;
                            if (br.times == 0) {
                                touchedBlock = br;
                            }
                            break;
                        }

                    }
                }
            }
        }

        if (touchedBrick != null) {
            root.getChildren().remove(touchedBrick);
            basic_bricks.remove(touchedBrick);
        }
        if (touchedBlock != null) {
            root.getChildren().remove(touchedBlock.brick);
            block_brick.remove(touchedBlock);
        }
    }

}