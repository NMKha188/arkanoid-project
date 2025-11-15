package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.config.Config;
import arkanoid.source.code.gameplay.GameLogicThread;
import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PauseScreen {
    @FXML
    private Button continueButton;

    @FXML
    private Button backtomainButton;

    private static Stage pauseStage;
    private static Stage gameStage;

    @FXML
    private void initialize() {
        continueButton.setOnAction(e -> {
            resumeGame();
        });
        backtomainButton.setOnAction(e -> {
            exitToMainMenu();
        });
    }

    private void resumeGame() {
        if (pauseStage != null) {
            pauseStage.hide();
        }

        if (InGameLogic.getGameLogicThread() == null) {
            InGameLogic.setGameLogicThread(
                    new GameLogicThread(
                            InGameLogic.getPaddle(),
                            InGameLogic.getBallList(),
                            InGameLogic.getBrickSet(),
                            InGameLogic.getPowerUpList()
                    )
            );
            new Thread(InGameLogic.getGameLogicThread()).start();
        }

        InGameStatus.startDownRecAnimation();
    }

    private void exitToMainMenu() {
        InGameLogic.stopGame();
        InGameStatus.resetGame();
        hidePauseOverlay();
        if (gameStage != null) {
            SceneController.switchToMainMenu(gameStage);
        } else {
            // Fallback: get stage from button
            Stage currentStage = (Stage) backtomainButton.getScene().getWindow();
            SceneController.switchToMainMenu(currentStage);
        }
    }

    public static void showPauseOverlay(Stage gameStage) {
        try {
            PauseScreen.gameStage = gameStage;

            pauseStage = new Stage();
            pauseStage.initStyle(StageStyle.TRANSPARENT);
            pauseStage.initOwner(gameStage);

            java.net.URL resourceUrl = PauseScreen.class.getResource("/arkanoid/resources/pauseScreen.fxml");
            FXMLLoader loader = new FXMLLoader(resourceUrl);

            // Create a semi-transparent overlay
            Pane pauseRoot = loader.load();
            pauseRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent black

            Scene pauseScene = new Scene(pauseRoot,
                    InGameLogic.getGameplayScreenWidth() + Config.EXTRA,
                    InGameLogic.getGameplayScreenHeight()+ Config.EXTRA/2);

            // Make the scene transparent
            pauseScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            pauseStage.setScene(pauseScene);
            pauseStage.setTitle("Arkanoid - Paused");

            pauseStage.show();
            pauseStage.setY(gameStage.getY() + 30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hidePauseOverlay() {
        if (pauseStage != null) {
            pauseStage.hide();
        }
    }
}