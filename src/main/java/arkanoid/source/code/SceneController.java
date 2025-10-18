package arkanoid.source.code;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Platform;

public class SceneController {
    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        playButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void startGame() {
        System.out.println("Starting game....");
        try {
            // Close the current menu stage
            Stage currentStage = (Stage) playButton.getScene().getWindow();

            // Start game
            InGameLogic mainGame = new InGameLogic();

            Scene gameScene = mainGame.createGameScene(currentStage);
            currentStage.setScene(gameScene);
            currentStage.setTitle("Arkanoid - Game");
            currentStage.setResizable(false);
            currentStage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
