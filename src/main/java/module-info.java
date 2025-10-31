module arkanoid.source.code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
//    requires arkanoid.source.code;
    opens arkanoid.source.code.gameplay to javafx.fxml;
    opens arkanoid.source.code.gamecontroller to javafx.fxml;
    exports arkanoid.source.code.gameplay.brick;
    exports arkanoid.source.code.gameplay.powerup;
    exports arkanoid.source.code.gameplay;
    exports arkanoid.source.code.gamecontroller;
    exports arkanoid.source.code.graphic;
    opens arkanoid.source.code.graphic to javafx.fxml;
    exports arkanoid.source.code.gameplay.ball;
    opens arkanoid.source.code.gameplay.ball to javafx.fxml;
    exports arkanoid.source.code.gameplay.paddle;
    opens arkanoid.source.code.gameplay.paddle to javafx.fxml;
}