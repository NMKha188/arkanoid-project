package arkanoid.source.code;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        java.net.URL resourceUrl = getClass().getResource("/arkanoid/resources/sceneMenu.fxml");
        System.out.println("Resource URL: " + resourceUrl);

        if (resourceUrl == null) {
            System.err.println("ERROR: sceneMenu.fxml not found");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);

        Scene scene = new Scene(root, 560, 640);
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
