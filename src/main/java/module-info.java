module arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    opens arkanoid.source.code to javafx.fxml;
    exports arkanoid.source.code;
}
