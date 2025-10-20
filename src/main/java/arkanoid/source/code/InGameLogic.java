package arkanoid.source.code;

import arkanoid.source.code.brick.BrickSet;
import arkanoid.source.code.powerup.PowerUpList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InGameLogic {
    // Screen size
    private static final double SCREEN_WIDTH = 560;
    private static final double SCREEN_HEIGHT = 640;

    // Check key press
    private static boolean movingLeft = false;
    private static boolean movingRight = false;

    // paddle
    private static final Paddle paddle = new Paddle();

    // ball
    private static final Ball ball = new Ball();

    // brickSet
    private static final BrickSet brickSet = new BrickSet();
    String dataPath = "/arkanoid/resources/map1.txt";

    // power ups
    private static final PowerUpList powerUpList = new PowerUpList();

    // root and scene
    private static final Pane root = new Pane();
    private static final Scene gameScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    // getter setter BEGIN
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
    // getter setter END

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
        root.getChildren().add(InGameStatus.getGroup());

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
                ball.updatePosition( paddle, brickSet, powerUpList);
                paddle.updatePosition();
                powerUpList.update(paddle);
            }
        }.start();

        return gameScene;
    }
}