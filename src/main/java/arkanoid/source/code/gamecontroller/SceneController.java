package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.gameplay.InGameLogic;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    @FXML
    private VBox mainMenu;

    @FXML
    private VBox instructionsOverlay;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button backButton;
//    private static Stage primaryStage;

    @FXML
    public void initialize() {
        if (instructionsOverlay != null) {
            instructionsOverlay.setVisible(false);
        }
        playButton.setOnAction(e -> {
            startGame();
        });
        exitButton.setOnAction(e -> {
            System.out.println("Closing game...");
            Platform.exit();
            System.exit(0);
        });
        instructionsButton.setOnAction(e -> {
            showInstructions();
        });
        backButton.setOnAction(event -> {
            hideInstructions();
        });
    }

    private void startGame() {
        System.out.println("Starting game....");
        try {
            // Close the current menu stage
            Stage currentStage = (Stage) playButton.getScene().getWindow();

            Scene gameScene = InGameLogic.createGameScene(currentStage);
            currentStage.setScene(gameScene);
            currentStage.setTitle("Arkanoid - Game");
            currentStage.setResizable(false);
            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInstructions() {
        mainMenu.setVisible(false);
        instructionsOverlay.setVisible(true);
    }

    private void hideInstructions() {
        mainMenu.setVisible(true);
        instructionsOverlay.setVisible(false);
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
}