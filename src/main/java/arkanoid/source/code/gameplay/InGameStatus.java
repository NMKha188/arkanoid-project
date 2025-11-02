package arkanoid.source.code.gameplay;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gamecontroller.GameEngine;
import arkanoid.source.code.gamecontroller.GameOverScreen;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InGameStatus {

    private static int score = 0;
    private static int lives = 3;
    private static Stage primaryStage;

    private static final Text scoreText = new Text("Score: " + score);
    private static final Text livesText = new Text("Lives: " + lives);

    private static final Rectangle topRec = new Rectangle(0, 0, Config.EXTRA +  Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4);
    private static final Rectangle downRec = new Rectangle(0, Config.EXTRA / 4 + Config.GAMEPLAY_SCREEN_HEIGHT, Config.EXTRA + Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4);
    private static final Rectangle leftRec = new Rectangle(0, Config.EXTRA / 4, Config.EXTRA / 2, Config.GAMEPLAY_SCREEN_HEIGHT);
    private static final Rectangle rightRec = new Rectangle(Config.EXTRA / 2 + Config.GAMEPLAY_SCREEN_WIDTH, Config.EXTRA / 4, Config.EXTRA / 2, Config.GAMEPLAY_SCREEN_HEIGHT);

    private static final Group group = new Group(topRec, downRec, leftRec, rightRec, scoreText, livesText);

    private static final double SCORE_X = Config.BRICK_WIDTH * ((Config.EXTRA / (2 * Config.BRICK_WIDTH)) + 2);
    private static final double SCORE_Y = Config.BRICK_HEIGHT;
    private static final double LIVES_X = Config.GAMEPLAY_SCREEN_WIDTH + Config.EXTRA - SCORE_X - Config.BRICK_WIDTH * 2;
    private static final double LIVES_Y = Config.BRICK_HEIGHT;

    static {
        scoreText.setFont(Font.font("Consolas", 25));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(SCORE_X);
        scoreText.setY(SCORE_Y);

        livesText.setFont(Font.font("Consolas", 25));
        livesText.setFill(Color.BLACK);
        livesText.setX(LIVES_X);
        livesText.setY(LIVES_Y);

        topRec.setFill(Color.BLUE);
        downRec.setFill(Color.BLUE);
        leftRec.setFill(Color.BLUE);
        rightRec.setFill(Color.BLUE);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int value) {
        score = value;
        updateTexts();
    }

    public static int getLives() {
        return lives;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void recoverLife() {
        lives++;
        updateTexts();
    }

    public static void loseLife() {
        if (lives > 0) lives--;
        updateTexts();
        if (lives == 0) {
            showGameOverScene();
        }
    }

    public static Group getGroup() {
        return group;
    }

    private static void updateTexts() {
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
    }

    public static void resetGame() {
        score = 0;
        lives = 3;
        updateTexts();
    }

    private static void showGameOverScene() {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                InGameLogic.stopGame();
                System.out.println("Switching to game over scene...");
                GameOverScreen.switchToGameOverScene(score, primaryStage);
            } else {
                System.out.println("You lose");
                System.out.println("Your final score: " + score);
                System.out.println("ERROR: primaryStage is null!");
                System.exit(0);
            }
        });
    }

    public static void addGroupToGameRoot() {
        InGameLogic.getRoot().getChildren().add(InGameStatus.getGroup());
    }
}