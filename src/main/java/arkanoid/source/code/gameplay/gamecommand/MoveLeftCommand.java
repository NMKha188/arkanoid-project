package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gameplay.InGameLogic;

public class MoveLeftCommand implements GameCommand {
    public void execute() {
        InGameLogic.setMovingLeft(true);
    }
}
