package arkanoid.source.code;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Brick {
    private double x;
    private double y;
    private double width;
    private double height;
    LinkedList<Rectangle> basic_bricks = new LinkedList<Rectangle>();
    LinkedList<blockBrick> block_brick = new LinkedList<blockBrick>();
    public class blockBrick{
        Rectangle brick;
        int times;
        blockBrick(Rectangle brick, int times){
            this.brick = brick;
            this.times = times;
        }
    }
    public Brick(double SCREEN_WIDTH) {
        this.x = 0;
        this.y = 60;
        this.width =SCREEN_WIDTH/5;
        this.height = 30;
    }
    public int [][]map1={{0,0,0,1,0},{1,2,2,1,0},{0,0,1,2,0}};
    public int[][][] maps={map1};
    public void khoiTao(Pane root){
        for (int k=0;k<maps.length;k++){
            for (int i = 0; i < maps[k].length; i++) {
                for (int j = 0; j < maps[k][i].length; j++) {
                    Rectangle rec=new Rectangle();
                    rec.setWidth(width);
                    rec.setHeight(height);
                    rec.setX(x+j*width);
                    rec.setY(y+i*height);
                    if(maps[k][i][j]==1){
                        rec.setFill(Color.PINK);
                        rec.setStroke(Color.BLACK);
                        rec.setStrokeWidth(15);
                        root.getChildren().add(rec);
                        basic_bricks.add(rec);
                    }
                    else if(maps[k][i][j]==2){
                        rec.setFill(Color.RED);
                        rec.setStroke(Color.BLACK);
                        rec.setStrokeWidth(15);
                        root.getChildren().add(rec);
                        block_brick.add(new blockBrick(rec,3));
                    }
                    else{
                        continue;
                    }
                }
            }
        }
    }
    public void vaCham(Ball ball,Pane root) {
        Rectangle touchedBrick = null;
        for (Rectangle brick : basic_bricks) {
            if (ball.getReleasedState()) {
                if (ball.getX() >= brick.getX() && ball.getX() <= brick.getX() + brick.getWidth()) {
                    if ((ball.getY() + ball.getRadius() > brick.getY() && ball.getY() + ball.getRadius() < brick.getY() + brick.getHeight())
                            || ((ball.getY() - ball.getRadius() < brick.getY() + brick.getHeight() && ball.getY() - ball.getRadius() < brick.getY()))) {
                        ball.setVy(-ball.getVy());
                        touchedBrick = brick;
                        break;
                    }
                }
                if (ball.getY() >= brick.getY() && ball.getY() <= brick.getY() + brick.getHeight()) {
                    if ((ball.getX() + ball.getRadius() > brick.getX() && ball.getX() + ball.getRadius() < brick.getX() + brick.getWidth())
                            || ((ball.getX() - ball.getRadius() < brick.getX() + brick.getWidth() && ball.getX() - ball.getRadius() < brick.getX()))) {
                        ball.setVy(-ball.getVy());
                        touchedBrick = brick;
                        break;
                    }
                }
            }
        }
        blockBrick bb = null;
        for (blockBrick br : block_brick) {
            if (ball.getReleasedState()) {
                if (ball.getX() >= br.brick .getX() && ball.getX() <= br.brick.getX() + br.brick.getWidth()) {
                    if ((ball.getY() + ball.getRadius() > br.brick.getY() && ball.getY() + ball.getRadius() < br.brick.getY() + br.brick.getHeight())
                            || ((ball.getY() - ball.getRadius() < br.brick.getY() + br.brick.getHeight() && ball.getY() - ball.getRadius() < br.brick.getY()))) {
                        ball.setVy(-ball.getVy());
                        br.times--;
                        if (br.times == 0) {
                            bb = br;
                        }
                        break;
                    }
                }
                if (ball.getY() >= br.brick.getY() && ball.getY() <= br.brick.getY() + br.brick.getHeight()) {
                    if ((ball.getX() + ball.getRadius() > br.brick.getX() && ball.getX() + ball.getRadius() < br.brick.getX() + br.brick.getWidth())
                            || ((ball.getX() - ball.getRadius() < br.brick.getX() + br.brick.getWidth() && ball.getX() - ball.getRadius() < br.brick.getX()))) {
                        ball.setVy(-ball.getVy());
                        br.times--;
                        if (br.times == 0) {
                            bb = br;
                        }
                        break;
                    }
                }
            }
        }
        if (touchedBrick != null) {
            root.getChildren().remove(touchedBrick);
            basic_bricks.remove(touchedBrick);
        }
        if (bb != null)
        {
            root.getChildren().remove(bb.brick);
            block_brick.remove(bb);
        }
    }
}
