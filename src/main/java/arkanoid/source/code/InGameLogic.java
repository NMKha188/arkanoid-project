package arkanoid.source.code;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InGameLogic{
    private static final double SCREEN_WIDTH = 560;
    private static final double SCREEN_HEIGHT = 640;

    private static boolean movingLeft = false;
    private static boolean movingRight = false;

    // paddle
    private final Paddle paddle = new Paddle();

    // ball
    private final Ball ball = new Ball();

    // brickSet
    private final BrickSet brickSet = new BrickSet();
    String dataPath = "/arkanoid/resources/map1.txt";

    // root
    private final Pane root = new Pane();
    private final Scene gameScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    public static double getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static double getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static boolean isMovingLeft() {
        return movingLeft;
    }

    public static boolean isMovingRight() {
        return movingRight;
    }

    public Scene createGameScene(Stage primaryStage) {
        brickSet.readData(dataPath);

        root.getChildren().add(paddle.getShape());
        root.getChildren().add(ball.getShape());
        root.getChildren().add(ball.getLine());
        for (int i = 0; i < BrickSet.getBrickRow(); i++) {
            for (int j = 0; j < BrickSet.getBricksEachRow(); j++) {
                if (brickSet.getOneBrickAt(i, j) != null) {
                    root.getChildren().add(brickSet.getOneBrickAt(i, j).getShape());
                }
            }
        }

        // handle key press
        gameScene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:
                    movingLeft = true;
                    break;
                case RIGHT:
                    movingRight = true;
                    break;
                default:
                    // blank
            }
        });

        // Handle key release
        gameScene.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case LEFT:
                    movingLeft = false;
                    break;
                case RIGHT:
                    movingRight = false;
                    break;
                case SPACE:
                    ball.setReleasedState(true);
                default:
                    // blank
            }
        });

        // animation control
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.updatePosition(paddle, brickSet);
                paddle.updatePosition();
            }
        }.start();

        return gameScene;
    }
}
