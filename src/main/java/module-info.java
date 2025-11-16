module arkanoid.source.code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;
    opens arkanoid.source.code.gameplay to javafx.fxml;
    opens arkanoid.source.code.gamecontroller to javafx.fxml;
    exports arkanoid.source.code.gameplay.gameobject.brick;
    exports arkanoid.source.code.gameplay.gameobject.powerup;
    exports arkanoid.source.code.gameplay;
    exports arkanoid.source.code.gamecontroller;
    exports arkanoid.source.code.graphic;
    opens arkanoid.source.code.graphic to javafx.fxml;
    exports arkanoid.source.code.gameplay.gameobject.ball;
    opens arkanoid.source.code.gameplay.gameobject.ball to javafx.fxml;
    exports arkanoid.source.code.gameplay.gameobject.paddle;
    opens arkanoid.source.code.gameplay.gameobject.paddle to javafx.fxml;
    exports arkanoid.source.code.sound;
    opens arkanoid.source.code.sound to javafx.fxml;
    exports arkanoid.source.code.gameplay.gamecommand;
    opens arkanoid.source.code.gameplay.gamecommand to javafx.fxml;
    exports arkanoid.source.code.gameplay.gameobject;
    opens arkanoid.source.code.gameplay.gameobject to javafx.fxml;
    exports arkanoid.source.code.gameplay.gamestatus;
    opens arkanoid.source.code.gameplay.gamestatus to javafx.fxml;
}