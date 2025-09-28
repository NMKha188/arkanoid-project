package arkanoid.source.code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Create a canvas for drawing
        Canvas canvas = new Canvas(600, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw a paddle
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(250, 350, 100, 20);

        // Draw a ball
        gc.setFill(Color.RED);
        gc.fillOval(290, 320, 20, 20);

        // Set up scene
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 600, 400, Color.LIGHTGRAY);

        stage.setTitle("Arkanoid Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
