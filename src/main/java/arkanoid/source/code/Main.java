package arkanoid.source.code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Screen size
    private static final double SCREEN_WIDTH = 560;
    private static final double SCREEN_HEIGHT = 640;

    // Check key press
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
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    public static double getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static double getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    @Override
    public void start(Stage primaryStage) {
        brickSet.readData(dataPath);

        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        root.getChildren().add(ball.getLine());
        for (int i = 0; i < BrickSet.getBrickRow(); i++) {
            for (int j = 0; j < BrickSet.getBricksEachRow(); j++) {
                root.getChildren().add(brickSet.getBrickSet()[i][j].getBrick());
            }
        }

        primaryStage.setScene(scene);
        primaryStage.show();

        // handle key press
        scene.setOnKeyPressed(event -> {
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
        scene.setOnKeyReleased(event -> {
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
                paddle.updatePosition(isMovingLeft, isMovingRight, SCREEN_WIDTH);
                ball.updatePosition(SCREEN_WIDTH, SCREEN_HEIGHT, paddle, isMovingLeft, isMovingRight, brickSet);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}