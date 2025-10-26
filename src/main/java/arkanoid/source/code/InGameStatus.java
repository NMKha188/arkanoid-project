package arkanoid.source.code;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import arkanoid.source.code.brick.Brick;
import javafx.stage.Stage;

public class InGameStatus {

    private static int score = 0;
    private static int lives = 3;
    private static Stage primaryStage;

    private static final Text scoreText = new Text("Score: " + score);
    private static final Text livesText  = new Text("Lives: " + lives);
    private static final Group group = new Group(scoreText, livesText);

    private static final double SCORE_X = 0;
    private static final double SCORE_Y = Brick.getBrickHeight();
    private static final double LIVES_X = Brick.getBrickWidth()*3;
    private static final double LIVES_Y = Brick.getBrickHeight();

    static {
        scoreText.setFont(Font.font("Consolas", 24));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(SCORE_X);
        scoreText.setY(SCORE_Y);

        livesText.setFont(Font.font("Consolas", 24));
        livesText.setFill(Color.BLACK);
        livesText.setX(LIVES_X);
        livesText.setY(LIVES_Y);
    }

    // getter setter BEGIN
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

    public static void loseLife() {
        if (lives > 0) lives--;
        updateTexts();
        if (lives == 0) {
            showGameOverScene();
        }
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Group getGroup() {
        return group;
    }
    // getter setter END

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
        // Use Platform.runLater to ensure this runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            if (primaryStage != null) {
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
}