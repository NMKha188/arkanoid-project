package arkanoid.source.code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Screen size
    private final double SCREEN_WIDTH = 560;
    private final double SCREEN_HEIGHT = 640;

    // Check key press
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    // Game objects
    private Paddle paddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);

    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(paddle.getPaddle());
        primaryStage.setScene(scene);
        primaryStage.show();

        /**
         * Handle key press.
         */
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

        /**
         * Handle key release.
         */
        scene.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case LEFT:
                    isMovingLeft = false;
                    break;
                case RIGHT:
                    isMovingRight = false;
                    break;
                default:
                    // blank
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.updatePosition(isMovingLeft, isMovingRight, SCREEN_WIDTH);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}