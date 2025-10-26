package arkanoid.source.code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class GameOverScreen {
    @FXML
    private Label scoreLabel;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button exitButton;

    private int finalScore;

    private static Stage primaryStage;

    @FXML
    private void initialize() {
        mainMenuButton.setOnAction(e -> SceneController.switchToMainMenu(primaryStage));
        exitButton.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        updateScoreLabel();
    }

    public void setFinalScore(int score) {
        this.finalScore = score;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            scoreLabel.setText("Final Score: " + finalScore);
        }
    }



    public static void switchToGameOverScene(int finalScore, Stage stage) {
        try {
            java.net.URL resourceUrl = GameOverScreen.class.getResource("/arkanoid/resources/sceneGameOver.fxml");
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Scene gameOverScene = new Scene(loader.load(), 560, 640);

            GameOverScreen controller = loader.getController();
            controller.setFinalScore(finalScore);

            stage.setScene(gameOverScene);
            stage.setTitle("Arkanoid - Game Over");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}