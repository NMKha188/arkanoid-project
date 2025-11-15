package arkanoid.source.code.gamecontroller;

import arkanoid.source.code.gameplay.InGameLogic;
import arkanoid.source.code.gameplay.InGameStatus;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenFactory {
    public enum ScreenType {
        MAINMENU,
        GAME,
        PAUSE,
        LEVEL_CLEAR,
        GAME_OVER
    }

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
            GameEngine gameEngine = new GameEngine();
            gameEngine.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createGameScreen(Stage stage) {
        Scene gameScene = InGameLogic.createGameScene(stage);
        stage.setScene(gameScene);

        GameEngine gameEngine = new GameEngine();
        try {
            gameEngine.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showPauseScreen(Stage stage) {
        PauseScreen.showPauseOverlay(stage);
    }

    private static void showLevelClearScreen(Stage stage) {
        LevelClearScreen.levelClear(InGameStatus.getScore(), stage);
    }

    private static void showGameOverScreen(Stage stage) {
        GameOverScreen.switchToGameOverScene(InGameStatus.getScore(), stage);
    }
}