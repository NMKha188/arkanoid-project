package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gameplay.InGameLogic;

public class MoveRightCommand implements GameCommand {
    public void execute() {
        InGameLogic.setMovingRight(true);
    }
}
