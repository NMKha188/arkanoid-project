package arkanoid.source.code.gameplay.gamecommand;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Map<KeyCode, GameCommand> keyPressedMap = new HashMap<>();
    private final Map<KeyCode, GameCommand> keyReleasedMap = new HashMap<>();

    public void bindKey(KeyCode key, GameCommand pressCommand, GameCommand releasedCommand) {
        keyPressedMap.put(key, pressCommand);
        keyReleasedMap.put(key, releasedCommand);
    }

    public void setupInput(Scene gameScene) {
        gameScene.setOnKeyPressed(e -> {
            GameCommand command = keyPressedMap.get(e.getCode());
            if (command != null) {
                command.execute();
            }
        });

        gameScene.setOnKeyReleased(e -> {
            GameCommand command = keyReleasedMap.get(e.getCode());
            if (command != null) {
                command.execute();
            }
        });
    }
}
