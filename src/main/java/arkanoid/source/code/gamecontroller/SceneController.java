package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.gamestatus.InGameStatus;
import arkanoid.source.code.gameplay.gamestatus.Ranking;
import arkanoid.source.code.gameplay.gamestatus.SaveGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;


public class SceneController {
    @FXML
    private VBox mainMenu;
    @FXML
    private VBox instructionsOverlay;
    @FXML
    private VBox ldbOverlay;

    @FXML
    private Button playButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button ldbButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Button backButton;
    @FXML
    private Button ldbBackButton;

    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label score3;
    @FXML
    private Label score4;
    @FXML
    private Label score5;
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void initialize() {
        if (instructionsOverlay != null) {
            instructionsOverlay.setVisible(false);
        }
        if (ldbOverlay != null) {
            ldbOverlay.setVisible(false);
        }
        playButton.setOnAction(e -> {
            SaveGame.resetSaveGameFile();
            SaveGame.loadGame();
            startGame(playButton);
        });
        loadGameButton.setOnAction(e -> {
            SaveGame.loadGame();
            InGameStatus.updateTexts();
            startGame(loadGameButton);
        });
        exitButton.setOnAction(e -> {
            System.out.println("Closing game...");
            Platform.exit();
            System.exit(0);
        });
        ldbButton.setOnAction(e -> showLdb());
        ldbBackButton.setOnAction(event -> hideLdb());
        instructionsButton.setOnAction(e -> showInstructions());
        backButton.setOnAction(event -> hideInstructions());
    }

    public void startGame(Button a) {
        System.out.println("Starting game....");

        try {
            // Close the current menu stage
            Stage currentStage = (Stage) a.getScene().getWindow();

            Scene gameScene = InGameLogic.createGameScene(currentStage);
            currentStage.setScene(gameScene);
            currentStage.setTitle("Arkanoid - Game");
            currentStage.setResizable(false);
            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLdb() {
        addScore();
        mainMenu.setVisible(false);
        ldbOverlay.setVisible(true);
    }

    private void hideLdb() {
        mainMenu.setVisible(true);
        ldbOverlay.setVisible(false);
    }

    public void addScore() {
        List<Integer> a = Ranking.returnArr();
        Label[] scoreLabels = {score1, score2, score3, score4, score5};

        for (int i = 0; i < 5; i++) {
            if (a.get(i) != null) scoreLabels[i].setText(String.valueOf(a.get(i)));
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

    public static void completeLevel() {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                InGameLogic.stopGame();
                System.out.println("Switching to next level scene...");
                LevelClearScreen.levelClear(InGameStatus.getScore(), primaryStage);
            } else {
                System.out.println("You passed");
                System.out.println("Your score: " + InGameStatus.getScore());
                System.out.println("ERROR: primaryStage is null!");
                System.exit(0);
            }
        });
    }

    public static void showGameOverScene() {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                InGameLogic.stopGame();
                System.out.println("Switching to game over scene...");
                GameOverScreen.switchToGameOverScene(InGameStatus.getScore(), primaryStage);
            } else {
                System.out.println("You lose");
                System.out.println("Your final score: " + InGameStatus.getScore());
                System.out.println("ERROR: primaryStage is null!");
                System.exit(0);
            }
        });
    }
}