package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.Ranking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelClearScreen {
    @FXML
    private Label scoreLabel;

    @FXML
    private Button nextLevelButton;

    @FXML
    private Button backtoMMButton;

    private int currentScore;

    private static Stage primaryStage;

    @FXML
    private void initialize() {
        nextLevelButton.setOnAction(e -> {
            InGameLogic.reset();
            SceneController controller = new SceneController();
            controller.startGame(nextLevelButton);
        });
        updateScoreLabel();
        backtoMMButton.setOnAction(e -> {
            Ranking.printAndSaveRankScore(currentScore);
            InGameLogic.reset();
            switchToMainMenu();
        });
    }

    public void currentScore(int score) {
        this.currentScore = score;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + currentScore);
        }
    }

    private void switchToMainMenu() {
        InGameStatus.resetGame();
        if (primaryStage != null) {
            SceneController.switchToMainMenu(primaryStage);
        } else {
            // Fallback: get stage from button
            Stage currentStage = (Stage) backtoMMButton.getScene().getWindow();
            SceneController.switchToMainMenu(currentStage);
        }
    }

    public static void levelClear(int finalScore, Stage stage) {
        try {
            java.net.URL resourceUrl = GameOverScreen.class.getResource("/arkanoid/resources/sceneLevelClear.fxml");
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Scene gameOverScene = new Scene(loader.load(), InGameLogic.getGameplayScreenWidth() + Config.EXTRA, InGameLogic.getGameplayScreenHeight() + Config.EXTRA / 2);

            LevelClearScreen controller = loader.getController();
            controller.currentScore(finalScore);

            stage.setScene(gameOverScene);
            stage.setTitle("Arkanoid - Game Over");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
