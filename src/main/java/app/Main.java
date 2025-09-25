/**
 * Main class.
 * <p>
 * This is the entry point of the Fast Typing Game application. 
 * It initializes the JavaFX runtime and loads the main user interface 
 * from the FXML file.
 * </p>
 *
 * @author Lina Vanessa Cosme Arce - 2436459
 * @version 1.8
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * <p>
     * This method loads the FXML file, sets the scene, and displays
     * the primary stage (main window) of the application.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which 
     *        the application scene can be set
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Fast Typing Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Main method.
     * <p>
     * This method launches the JavaFX application by calling 
     * {@link Application#launch(String...)}.
     * </p>
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
