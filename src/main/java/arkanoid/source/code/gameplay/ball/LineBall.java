package arkanoid.source.code.gameplay.ball;


import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.paddle.Paddle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineBall {
    public static Line lineBall;
    private static double Vx = 0;
    private static double changeVx = 0.05;
    private static double maxVx = Config.BALL_SPEED * 0.67;

    private static boolean visible;
    static {
        lineBall = new Line();
        lineBall.setStrokeWidth(5);
        lineBall.setStroke(Color.RED);
    }

    public static void setVisibility(boolean a) {
        lineBall.setVisible(a);
    }

    public static void setVx() {
        Vx =0;
    }

    public static void showLineBall(Paddle paddle) {
        double startX = paddle.getX() + paddle.getWidth() / 2;
        double startY = paddle.getY() - 10;

        if (Vx >= maxVx || Vx <= -maxVx) {
            changeVx = -changeVx;
        }
        Vx += changeVx;

        double currentVxRad = Vx * 30 - (Math.PI / 2);

        double endX = startX + 50 * Math.cos(currentVxRad);
        double endY = startY + 50 * Math.sin(currentVxRad);

        lineBall.setStartX(startX);
        lineBall.setStartY(startY);
        lineBall.setEndX(endX);
        lineBall.setEndY(endY);
    }

}
