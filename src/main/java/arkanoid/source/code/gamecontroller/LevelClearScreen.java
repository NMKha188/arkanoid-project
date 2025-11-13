package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import arkanoid.source.code.gameplay.Ranking;
import arkanoid.source.code.gameplay.SaveGame;
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
    private Label bonus;
    @FXML
    private Label finalScoreLabel;
    @FXML
    private Button nextLevelButton;

    @FXML
    private Button backtoMMButton;

    private int currentScore;
    private int bonusPercentage;
    private int finalScore;
    private static Stage primaryStage;

    @FXML
    private void initialize() {
        if(InGameStatus.hasFinishedGame()) {
            nextLevelButton.setVisible(false);
        }
        nextLevelButton.setOnAction(e -> {
//            InGameLogic.loadMap();
//            InGameLogic.reset();
            SceneController controller = new SceneController();
            controller.startGame(nextLevelButton);
        });
        updateScoreLabels();
        backtoMMButton.setOnAction(e -> {
            if(InGameStatus.hasFinishedGame()) {
                SaveGame.resetSaveGameFile();
            }
            else {
                SaveGame.saveGame();
            }
            Ranking.printAndSaveRankScore(currentScore);
            InGameLogic.reset();
            switchToMainMenu();
        });
    }

    public void currentScore(int score) {
        this.currentScore = score;
        calculateScore();
        updateScoreLabels();
    }

    private void calculateScore() {
        // Get bonus percentage based on remaining lives
        this.bonusPercentage = InGameStatus.getBonusScorePercentage();

        // Calculate final score with bonus
        this.finalScore = (int) (currentScore * (1 + ((double) bonusPercentage) / 100));
    }

    private void updateScoreLabels() {
        if (scoreLabel != null) {
            scoreLabel.setText("Base Score: " + currentScore);
        }
        updateBonus();
        updateFinalScore();
    }

    private void updateBonus() {
        if (bonus != null) {
            bonus.setText("Remaining Lives Bonus: " + bonusPercentage + "%");
        }
    }

    private void updateFinalScore() {
        if (finalScoreLabel != null) {
            finalScoreLabel.setText("Final Score: " + finalScore);
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
