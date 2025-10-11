package arkanoid.source.code;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;


import java.util.LinkedList;

public class Brick {
    private double x = 0;
    private double y = 60;
    private double width = Main.getSCREEN_WIDTH()/10;
    private double height=15;
    private int hitPoints;
    private Rectangle rec;
    private boolean daVaCham = false;

    public Brick getBrick() {
        return this;
    }

    LinkedList<Brick> bricks = new LinkedList<Brick>();

    public Brick() {
    }

    public Brick(Rectangle rec, int hitPoints) {
        this.rec = rec;
        this.hitPoints = hitPoints;
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
                    rec.setStrokeWidth(5);
                    if (maps[k][i][j] == 1) {
                        rec.setFill(Color.PINK);
                        rec.setStroke(Color.BLACK);
                        root.getChildren().add(rec);
                        bricks.add(new Brick(rec, 1));
                    } else if (maps[k][i][j] == 2) {
                        rec.setFill(Color.RED);
                        rec.setStroke(Color.BLACK);
                        root.getChildren().add(rec);
                        bricks.add(new Brick(rec, 3));
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public double getX(Brick brick) {
        return brick.x;
    }



    public void kiemTraVaCham(Ball ball, Pane root) {
        if (ball.getReleasedState()) {
            Brick touchedBrick = null;
            for (Brick brick : bricks) {
                Shape inter = Shape.intersect(ball.getBall(), brick.rec);
                if (inter.getBoundsInLocal().getWidth() > 0 && inter.getBoundsInLocal().getHeight() > 0) {
                    if (ball.getX() > brick.rec.getX() && ball.getX() < brick.rec.getX() + brick.rec.getWidth()) {
                        ball.setVy(-ball.getVy());
                        daVaCham = true;
                    } else if (ball.getY() > brick.rec.getY() && ball.getY() < brick.rec.getY() + brick.rec.getHeight()) {
                        ball.setVx(-ball.getVx());
                        daVaCham = true;
                    } else {
                        ball.setVy(-ball.getVy());
                        ball.setVx(-ball.getVx());
                        daVaCham = true;
                    }
                    if (daVaCham) {
                        kiemTraLoaiGach(brick);
                        if (brick.hitPoints <= 0) {
                            touchedBrick = brick;
                        }
                        break;
                    }
                }
            }
            if (touchedBrick != null) {
                root.getChildren().remove(touchedBrick.rec);
                bricks.remove(touchedBrick);
            }
            daVaCham = false;
        }
    }

    public void kiemTraLoaiGach (Brick brick){
            switch (brick.hitPoints) {
                case 1 -> {
                    brick.hitPoints=0;
                }
                case 2 -> {
                    brick.hitPoints--;
                }
                default -> {}
            }
    }

}