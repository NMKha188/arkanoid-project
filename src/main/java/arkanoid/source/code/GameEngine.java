package arkanoid.source.code;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameEngine extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        InGameStatus.setPrimaryStage(primaryStage);

        java.net.URL resourceUrl = getClass().getResource("/arkanoid/resources/sceneMenu.fxml");

        if (resourceUrl == null) {
            System.err.println("ERROR: sceneMenu.fxml not found");
            return;
        }

        FXMLLoader loader = new FXMLLoader(resourceUrl);
        Parent menuRoot = loader.load();

        Scene scene = new Scene(menuRoot, InGameLogic.getScreenWidth(), InGameLogic.getScreenHeight());
        primaryStage.setTitle("Arkanoid - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
