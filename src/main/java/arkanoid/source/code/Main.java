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
    private static boolean movingLeft = false;
    private static boolean movingRight = false;

    // root and scene
    private static Pane root = new Pane();
    private static Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    // paddle
    private final Paddle paddle = new Paddle();

    // ball
    private final Ball ball = new Ball();

    // brickSet
    private final BrickSet brickSet = new BrickSet();
    String dataPath = "/arkanoid/resources/map1.txt";

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

    public static Pane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
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

        primaryStage.setScene(scene);
        primaryStage.show();

        // handle key press
        scene.setOnKeyPressed(event -> {
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
        scene.setOnKeyReleased(event -> {
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
                ball.updatePosition( paddle, brickSet);
                paddle.updatePosition();
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}