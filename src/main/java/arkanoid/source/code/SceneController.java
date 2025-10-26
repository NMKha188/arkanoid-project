package arkanoid.source.code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class SceneController {
    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    private static Stage primaryStage;
    @FXML
    public void initialize() {
        playButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
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

    public static void switchToMainMenu(Stage stage) {
        try {
            // Create a GameEngine instance and call its method
            GameEngine gameEngine = new GameEngine();
            gameEngine.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Stage getActiveStage() {
        return primaryStage;
    }

}
