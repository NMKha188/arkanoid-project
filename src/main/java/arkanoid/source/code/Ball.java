package arkanoid.source.code;

import javafx.scene.shape.Circle;

public class Ball {
    // Toạ độ+Bán Kính
    private double x;
    private double y;
    private final double RADIUS = 10;

    private Circle ball;
    private final double MAX_ANGLE = Math.toRadians(70);

    private double dx = 2;
    private double dy = -5;
    private boolean start = false;//Khi bấm space

    public Ball(Paddle paddle) {
        this.x = paddle.getX() + paddle.getWIDTH() / 2;
        this.y = paddle.getY() - RADIUS;
        this.ball = new Circle(x, y, RADIUS);
    }


    public void update(double SCREEN_WIDTH, double SCREEN_HEIGHT,Paddle paddle) {
        double paddleCenterX = paddle.getX() + paddle.getWIDTH() / 2;

        double tiLe = Math.abs((ball.getCenterX() - paddleCenterX) / (paddle.getWIDTH() / 2));
        if (y > SCREEN_HEIGHT + 4* RADIUS) {
            start = false;
            x = paddle.getX() + paddle.getWIDTH() / 2;
            y = paddle.getY() - RADIUS;
            dx=2;
            dy=-5;
        }

        if (start == true) {
            x += dx;
            y += dy;
        }
        if ((x + RADIUS) >= SCREEN_WIDTH) {
            x = SCREEN_WIDTH - RADIUS;
            dx = -dx;
        }
        if ((x - RADIUS) <= 0) {
            x = RADIUS;
            dx = -dx;
        }
        if ((y - RADIUS) <= 0) {
            y = RADIUS;
            dy = -dy;
        }
        if (dy > 0 && ((y + RADIUS >= paddle.getY()) && (y + RADIUS <= paddle.getY() + paddle.getHEIGHT()))) {
            double speed = 6;
            if ((x >= paddle.getX() && x <= paddleCenterX)) {
                dx = Math.abs(speed * Math.sin(tiLe * MAX_ANGLE)) * (-1);
                dy = Math.abs(speed * Math.cos(tiLe * MAX_ANGLE)) * (-1);
            } else if (x > paddleCenterX && x < paddle.getX() + paddle.getWIDTH()) {
                dx = Math.abs(speed * Math.sin(tiLe * MAX_ANGLE));
                dy = Math.abs(speed * Math.cos(tiLe * MAX_ANGLE)) * (-1);
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
    public boolean getStart() {
        return this.start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }


}
