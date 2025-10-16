package arkanoid.source.code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
            currentStage.close();

            // Start game
            Main mainGame = new Main();
            Stage gameStage = new Stage();
            mainGame.start(gameStage);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
