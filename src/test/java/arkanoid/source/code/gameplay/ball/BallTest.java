package arkanoid.source.code.gameplay.ball;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.gameobject.ball.Ball;
import arkanoid.source.code.gameplay.gameobject.paddle.Paddle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import javafx.embed.swing.JFXPanel;

public class BallTest {

    private Ball ball;
    private Paddle paddle;
    private static final double DELTA = 0.001;

    @BeforeClass
    public static void initJavaFX() {
        new JFXPanel();
    }

    @Before
    public void setUp() {
        InGameLogic.loadMap();
        paddle = new Paddle();
        ball = new Ball();
        ball.setBallSpeed(10.0);
        ball.setMaxVx(10.0 * 0.75);
        ball.setChangeVx(0.05);

        paddle.setX(300);
        paddle.setY(570);
        paddle.setWidth(Config.PADDLE_WIDTH);
        paddle.setHeight(Config.PADDLE_HEIGHT);
    }

    @Test
    public void testBallInitialization() {
        assertNotNull(ball.getShape());
        assertEquals(Config.BALL_RADIUS, ball.getRadius(), DELTA);
        assertEquals(0.0, ball.getX(), DELTA);
        assertEquals(0.0, ball.getY(), DELTA);
    }

    @Test
    public void testInitializeVelocity_inTheMiddleOfPaddle() {
        ball.initializeVelocity(paddle);
        double expectedX = paddle.getX() + Config.PADDLE_WIDTH / 2.0;
        assertEquals(expectedX, ball.getX(), DELTA);
        assertEquals(paddle.getY() - Config.BALL_RADIUS, ball.getY(), DELTA);
    }


    @Test
    public void testUpdate() {
        ball.setX(300);
        ball.setY(600);
        ball.setVx(5);
        ball.setVy(10);

        ball.updateLogic();
        ball.updateVisual();

        assertEquals(305, ball.getX(), DELTA);
        assertEquals(610, ball.getY(), DELTA);
        assertEquals(305, ball.getShape().getCenterX(), DELTA);
        assertEquals(610, ball.getShape().getCenterY(), DELTA);
    }
}