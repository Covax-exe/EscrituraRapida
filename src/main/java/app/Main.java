package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Main application class that launches the JavaFX UI.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Escritura Rápida - Mini Proyecto");
        stage.setResizable(true);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
