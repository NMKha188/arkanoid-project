package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gameplay.InGameLogic;

public class StopMoveLeftCommand implements GameCommand {
    public void execute() {
        InGameLogic.setMovingLeft(false);
    }
}
