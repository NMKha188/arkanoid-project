package arkanoid.source.code;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InGameLogic{
    private static final double SCREEN_WIDTH = 560;
    private static final double SCREEN_HEIGHT = 640;

    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;

    // paddle
    private final Paddle paddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);

    // ball
    private final Ball ball = new Ball();

    // brickSet
    private final BrickSet brickSet = new BrickSet();
    String dataPath = "/arkanoid/resources/map1.txt";

    // root and scene
    Pane root = new Pane();

    public static double getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static double getScreenHeight() {
        return SCREEN_HEIGHT;
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

        Scene gameScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        // handle key press
        gameScene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:
                    isMovingLeft = true;
                    break;
                case RIGHT:
                    isMovingRight = true;
                    break;
                default:
                    // blank
            }
        });

        // Handle key release
        gameScene.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case LEFT:
                    isMovingLeft = false;
                    break;
                case RIGHT:
                    isMovingRight = false;
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
                ball.updatePosition(SCREEN_WIDTH, SCREEN_HEIGHT, paddle, isMovingLeft, isMovingRight, brickSet);
                paddle.updatePosition(isMovingLeft, isMovingRight, SCREEN_WIDTH);
            }
        }.start();

        return gameScene;
    }
}
