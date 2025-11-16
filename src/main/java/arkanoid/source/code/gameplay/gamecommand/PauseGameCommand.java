package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gamecontroller.PauseScreen;
import arkanoid.source.code.gameplay.InGameLogic;
import javafx.stage.Stage;

public class PauseGameCommand implements GameCommand {
    public void execute() {
        InGameLogic.stopGame();
        PauseScreen.showPauseOverlay((Stage) InGameLogic.getGameScene().getWindow());
    }
}
