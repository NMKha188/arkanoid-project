package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gameplay.InGameLogic;

public class StopMoveRightCommand implements GameCommand {
    public void execute() {
        InGameLogic.setMovingRight(false);
    }
}
