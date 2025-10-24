package arkanoid.source.code.config;

public class Config {
    // gameplay screen
    public static final double GAMEPLAY_SCREEN_WIDTH = 560;
    public static final double GAMEPLAY_SCREEN_HEIGHT = 640;

    // paddle
    public static final double PADDLE_WIDTH = 80;
    public static final double PADDLE_HEIGHT = 15;
    public static final double PADDLE_SPEED = 3;

    // ball
    public static final double BALL_RADIUS = 10;
    public static final double BALL_SPEED = 4.5;

    // 1 set of bricks
    public static final int BRICKS_ROW = 10;
    public static final int BRICKS_PER_ROW = 12;
    // 1 brick
    public static final double BRICK_WIDTH = GAMEPLAY_SCREEN_WIDTH / BRICKS_PER_ROW;
    public static final double BRICK_HEIGHT = BRICK_WIDTH / 2;

    // power up
    public static final double POWER_UP_WIDTH = BRICK_WIDTH / 2;
    public static final double POWER_UP_HEIGHT = POWER_UP_WIDTH / 2;
    public static final double POWER_UP_FALLING_SPEED = 1;
    // power up: expand paddle
    public static final int EXPAND_PADDLE_PROBABILITY = 49;
    public static final long EXPAND_PADDLE_DURATION = 10;
    public static final double EXPAND_PADDLE_EXPANDED_RATIO = 2;
    // power up: speed up paddle
    public static final int SPEED_UP_PADDLE_PROBABILITY = 49;
    public static final long SPEED_UP_PADDLE_DURATION = 10;
    public static final double SPEED_UP_RATIO = 1.5;
    // power up: explosive ball
    public static final int EXPLOSIVE_BALL_PROBABILITY = 49;
    public static final long EXPLOSIVE_BALL_DURATION = 5;
}
