package arkanoid.source.code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Screen size
    private static final double SCREEN_WIDTH = 560;
    private final double SCREEN_HEIGHT = 640;

    // Check key press
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    // paddle
    private Paddle paddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);

    // ball
    private Ball ball = new Ball();

    //brick
    private Brick brick=new Brick();
    // root and scene
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    public static double getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        root.getChildren().add(ball.getLine());
        primaryStage.setScene(scene);
        primaryStage.show();
        brick.khoiTao(root);
        // handle key press
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:
                    isMovingLeft = true;
                    break;
                case RIGHT:
                    isMovingRight = true;
                    break;
                case SPACE:
                    if (!ball.getReleasedState()) {
                        ball.setReleasedState(true);
                    }
                    break;
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
                default:
                    // blank
            }
        });

        // animation control
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.updatePosition(isMovingLeft, isMovingRight, SCREEN_WIDTH);
                ball.updatePosition(SCREEN_WIDTH, SCREEN_HEIGHT, paddle, isMovingLeft, isMovingRight);
                brick.kiemTraVaCham(ball,root);
            }
        }.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}