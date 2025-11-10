package arkanoid.source.code.config;

public class Config {
    // gameplay screen
    public static final double GAMEPLAY_SCREEN_WIDTH = 560;
    public static final double GAMEPLAY_SCREEN_HEIGHT = 640;
    public static final double EXTRA = GAMEPLAY_SCREEN_WIDTH / 3;

    // paddle
    public static final double PADDLE_WIDTH = 80;
    public static final double PADDLE_HEIGHT = 15;
    public static final double PADDLE_SPEED = 3;

    // ball
    public static final double BALL_RADIUS = 10;
    public static final double BALL_SPEED = 3.5;

    // 1 set of bricks
    public static final int BRICKS_ROW = 16;
    public static final int BRICKS_PER_ROW = 12;
    // 1 brick
    public static final double BRICK_WIDTH = GAMEPLAY_SCREEN_WIDTH / BRICKS_PER_ROW;
    public static final double BRICK_HEIGHT = BRICK_WIDTH / 2;
    // normal brick
    public static final int NORMAL_BRICK_HP = 1;
    public static final int NORMAL_BRICK_SCORE = 100;
    // hard brick
    public static final int HARD_BRICK_HP = 5;
    public static final int HARD_BRICK_SCORE = 600;
    // unbreakable brick
    //
    // resonance brick
    public static final int RESONANCE_BRICK_SCORE = 75;
    // explosive brick
    public static final int EXPLOSIVE_BRICK_SCORE = 150;
    // regenerative brick
    public static final int REGENERATIVE_BRICK_HP = 3;
    public static final int REGENERATIVE_BRICK_SCORE = 450;
    public static final long REGENERATIVE_TIME = 10;

    // power up
    public static final double POWER_UP_WIDTH = BRICK_WIDTH * 0.9;
    public static final double POWER_UP_HEIGHT = POWER_UP_WIDTH / 2;
    public static final double POWER_UP_FALLING_SPEED = 1;
    // power up: expand paddle
    public static final int EXPAND_PADDLE_PROBABILITY = 24;
    public static final long EXPAND_PADDLE_DURATION = 10;
    public static final double EXPAND_PADDLE_EXPANDED_RATIO = 2;
    // power up: speed up paddle
    public static final int SPEED_UP_PADDLE_PROBABILITY = 19;
    public static final long SPEED_UP_PADDLE_DURATION = 10;
    public static final double SPEED_UP_RATIO = 1.5;
    // power up: slow ball
    public static final int SLOW_BALL_PROBABILITY = 19;
    public static final long SLOW_BALL_DURATION = 10;
    public static final double SLOW_RATIO = 0.67;
    // power up: explosive ball
    public static final int EXPLOSIVE_BALL_PROBABILITY = 14;
    public static final long EXPLOSIVE_BALL_DURATION = 5;
    // power up: triple ball
    public static final int TRIPLE_BALL_PROBABILITY = 14;
    // power up: live
    public static final int LIVE_PROBABILITY = 9;
}
