package arkanoid.source.code.gameplay.paddle;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javafx.embed.swing.JFXPanel;

import static org.junit.Assert.*;

public class PaddleTest {

    private Paddle paddle;
    private static final double DELTA = 0.001;

    @BeforeClass
    public static void initJavaFX() {
        new JFXPanel();
    }

    @Before
    public void setUp() {
        InGameLogic.setMovingLeft(false);
        InGameLogic.setMovingRight(false);
        paddle = new Paddle();
    }

    @Test
    public void testPaddleInitialization() {
        double expectedX = Config.EXTRA / 2.0 + (InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH) / 2.0;
        double expectedY = Config.EXTRA / 4.0 + InGameLogic.getGameplayScreenHeight() - 30;

        assertNotNull(paddle.getShape());
        assertEquals(expectedX, paddle.getX(), DELTA);
        assertEquals(expectedY, paddle.getY(), DELTA);
        assertEquals(Config.PADDLE_WIDTH, paddle.getWidth(), DELTA);
        assertEquals(Config.PADDLE_HEIGHT, paddle.getHeight(), DELTA);
        assertEquals(Config.PADDLE_SPEED, paddle.getSpeed(), DELTA);
    }

    @Test
    public void testUpdate_MoveLeft() {
        InGameLogic.setMovingLeft(true);

        double initialX = paddle.getX();
        paddle.updateLogic();

        assertEquals(initialX - Config.PADDLE_SPEED, paddle.getX(), DELTA);
    }

    @Test
    public void testUpdate_MoveRight() {
        InGameLogic.setMovingRight(true);

        double initialX = paddle.getX();
        paddle.updateLogic();

        assertEquals(initialX + Config.PADDLE_SPEED, paddle.getX(), DELTA);
    }

    @Test
    public void testUpdate_TouchWithLeftWall() {
        double leftBoundary = Config.EXTRA / 2.0;
        paddle.setX(leftBoundary);

        InGameLogic.setMovingLeft(true);
        paddle.updateLogic();

        assertEquals(leftBoundary, paddle.getX(), DELTA);
    }

    @Test
    public void testUpdate_TouchWithRightWall() {
        double rightBoundary = Config.EXTRA / 2.0 + InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH;
        paddle.setX(rightBoundary);

        InGameLogic.setMovingRight(true);
        paddle.updateLogic();

        assertEquals(rightBoundary, paddle.getX(), DELTA);
    }

    @Test
    public void testUpdate_BothPressed2Key() {
        InGameLogic.setMovingLeft(true);
        InGameLogic.setMovingRight(true);

        double initialX = paddle.getX();
        paddle.updateLogic();

        assertEquals(initialX, paddle.getX(), DELTA);
    }

    @Test
    public void testUpdate_NoPressedAnyKey() {
        double initialX = paddle.getX();
        paddle.updateLogic();

        assertEquals(initialX, paddle.getX(), DELTA);
    }

    @Test
    public void testReset() {
        paddle.setX(100.0);
        paddle.setY(200.0);

        paddle.reset();

        double expectedX = Config.EXTRA / 2.0 + (InGameLogic.getGameplayScreenWidth() - Config.PADDLE_WIDTH) / 2.0;
        double expectedY = Config.EXTRA / 4.0 + InGameLogic.getGameplayScreenHeight() - 30;

        assertEquals(expectedX, paddle.getX(), DELTA);
        assertEquals(expectedY, paddle.getY(), DELTA);
    }
}