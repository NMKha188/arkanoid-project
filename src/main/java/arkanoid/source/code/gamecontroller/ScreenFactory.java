package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Factory class for creating and managing different game screens.
 * Centralizes screen creation and navigation logic.
 */
public class ScreenFactory {

    /**
     * Enum to identify different screen types
     */
    public enum ScreenType {
        MAINMENU,
        GAME,
        PAUSE,
        LEVEL_CLEAR,
        GAME_OVER
    }

    /**
     * Creates and displays the specified screen type
     *
     * @param screenType The type of screen to create
     * @param stage The stage to display the screen on
     */
    public static void createScreen(ScreenType screenType, Stage stage) {
        switch (screenType) {
            case MAINMENU:
                createMainMenu(stage);
                break;
            case GAME:
                createGameScreen(stage);
                break;
            case PAUSE:
                showPauseScreen(stage);
                break;
            case LEVEL_CLEAR:
                showLevelClearScreen(stage);
                break;
            case GAME_OVER:
                showGameOverScreen(stage);
                break;
            default:
                throw new IllegalArgumentException("Unknown screen type: " + screenType);
        }
    }

    private static void createMainMenu(Stage stage) {
        try {
            // Create a GameEngine instance and call its method
            GameEngine gameEngine = new GameEngine();
            gameEngine.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and starts the main game screen
     */
    private static void createGameScreen(Stage stage) {
        Scene gameScene = InGameLogic.createGameScene(stage);
        stage.setScene(gameScene);

        // Start the game engine
        GameEngine gameEngine = new GameEngine();
        try {
            gameEngine.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows the pause overlay screen
     */
    private static void showPauseScreen(Stage stage) {
        PauseScreen.showPauseOverlay(stage);
    }

    /**
     * Shows the level clear screen
     */
    private static void showLevelClearScreen(Stage stage) {
        LevelClearScreen.levelClear(InGameStatus.getScore(), stage);
    }

    /**
     * Shows the game over screen
     */
    private static void showGameOverScreen(Stage stage) {
        GameOverScreen.switchToGameOverScene(InGameStatus.getScore(), stage);
    }

}