package arkanoid.source.code;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.time.LocalTime;

public class Ball {
    // Toạ độ+Bán Kính
    public double x;
    private double y;
    private final double radius = 10;

    private final Circle ball;
    private final double maxAngle = Math.toRadians(70);

    private double dx = 2;
    private double dy = -5;
    public boolean start = false;//Khi bấm space

    public Ball(Paddle paddle) {
        this.x = paddle.getX() + paddle.getWIDTH() / 2;
        this.y = paddle.getY() - radius;
        this.ball = new Circle(x, y, radius);
    }


    public void update(double SCREEN_WIDTH, double SCREEN_HEIGHT,Paddle paddle) {
        final double paddleCenterX = paddle.getX() + paddle.getWIDTH() / 2;

        double tiLe = Math.abs((ball.getCenterX() - paddleCenterX) / (paddle.getWIDTH() / 2));
        if (y > SCREEN_HEIGHT + 4*radius) {
            start = false;
            x = paddle.getX() + paddle.getWIDTH() / 2;
            y = paddle.getY() - radius;
            dx=2;
            dy=-5;
        }

        if (start == true) {
            x += dx;
            y += dy;
        }
        if ((x + radius) >= SCREEN_WIDTH) {
            x = SCREEN_WIDTH - radius;
            dx = -dx;
        }
        if ((x - radius) <= 0) {
            x = radius;
            dx = -dx;
        }
        if ((y - radius) <= 0) {
            y = radius;
            dy = -dy;
        }
        if (dy > 0 && ((y + radius >= paddle.getY()) && (y + radius <= paddle.getY() + paddle.getHEIGHT()))) {
            double speed = 8;
            if ((x >= paddle.getX() && x <= paddleCenterX)) {
                dx = Math.abs(speed * Math.sin(tiLe * maxAngle)) * (-1);
                dy = Math.abs(speed * Math.cos(tiLe * maxAngle)) * (-1);
            } else if (x > paddleCenterX && x < paddle.getX() + paddle.getWIDTH()) {
                dx = Math.abs(speed * Math.sin(tiLe * maxAngle));
                dy = Math.abs(speed * Math.cos(tiLe * maxAngle)) * (-1);
            }
        }

        ball.setCenterX(x);
        ball.setCenterY(y);
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Circle getBall() {
        return this.ball;
    }


}
