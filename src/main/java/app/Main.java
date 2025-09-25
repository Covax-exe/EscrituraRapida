package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Main application class that launches the Fast Typing Game.
 * <p>
 * This class is the entry point of the application. It initializes the
 * JavaFX runtime, loads the main FXML view, applies the stylesheet,
 * and shows the primary window of the game.
 * </p>
 *
 * @author Lina Vanessa Cosme Arce - 2436459
 * @version 1.8
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * <p>
     * This method loads the FXML file for the main view, applies the
     * CSS stylesheet, sets up the scene, and displays the main stage
     * of the application.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *              the scene will be set
     * @throws Exception if the FXML file or CSS cannot be loaded
     */
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

    /**
     * Main method that launches the application.
     * <p>
     * This method calls {@link Application#launch(String...)} to start
     * the JavaFX lifecycle.
     * </p>
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
