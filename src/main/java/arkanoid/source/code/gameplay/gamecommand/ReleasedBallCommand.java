package arkanoid.source.code.gameplay.gamecommand;

import arkanoid.source.code.gameplay.gameobject.ball.BallList;

public class ReleasedBallCommand implements GameCommand {
    private final BallList ballListReceiver;

    public ReleasedBallCommand(BallList ballList) {
        this.ballListReceiver = ballList;
    }

    public void execute() {
        ballListReceiver.setReleased(true);
        ballListReceiver.hideDirectionLine();
    }
}
