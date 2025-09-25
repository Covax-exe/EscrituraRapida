package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Game;
import model.WordGenerator;

/**
 * GameController class.
 * <p>
 * This is the main controller for the Fast Typing Game. It manages the
 * user interface (FXML), the game timer, level progression, and word
 * validation logic. It connects the model ({@link Game} and {@link WordGenerator})
 * with the UI components.
 * </p>
 *
 * <p><b>Main responsibilities:</b></p>
 * <ul>
 *   <li>Load new words for each level</li>
 *   <li>Start and update the timer</li>
 *   <li>Validate player input against the target word</li>
 *   <li>Handle success/failure feedback messages</li>
 *   <li>Restart the game when required</li>
 * </ul>
 *
 * @author Lina Vanessa Cosme Arce - 2436459
 * @version 1.8
 */
public class GameController {

    @FXML private Label lblWord;     // Displays the current word/phrase
    @FXML private Label lblLevel;    // Displays the current level
    @FXML private Label lblTimer;    // Displays remaining time
    @FXML private Label lblMessage;  // Displays success or error messages
    @FXML private TextField txtInput; // Input field for the player
    @FXML private Button btnValidate; // Button to validate input
    @FXML private Button btnRestart;  // Button to restart the game
    @FXML private ProgressBar progress; // Shows remaining time visually
    @FXML private VBox centerBox;      // Container for binding word label width

    private Game game;                // Model that tracks game state
    private Timeline timeline;        // Timer for countdown
    private int timeLeft;             // Remaining time in seconds
    private WordGenerator generator;  // Word/phrase generator

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Sets up the game model, prepares the word generator, binds UI events
     * (like pressing Enter to validate), and loads the first word of the game.
     * </p>
     */
    @FXML
    public void initialize() {
        game = new Game();
        generator = new WordGenerator();

        // Enter key validates input
        txtInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onValidateAction();
            }
        });

        // Bind word label width to container width for proper text wrapping
        Platform.runLater(() -> {
            if (centerBox != null) {
                lblWord.maxWidthProperty().bind(centerBox.widthProperty().subtract(40));
                lblWord.setWrapText(true);
            }
        });

        loadNewWord();
    }

    /**
     * Handles the action of the "Validate" button from FXML.
     * <p>
     * Delegates the logic to {@link #validateInput()}.
     * </p>
     */
    @FXML
    public void onValidateAction() {
        validateInput();
    }

    /**
     * Handles the action of the "Restart" button from FXML.
     * <p>
     * Delegates the logic to {@link #resetGame()}.
     * </p>
     */
    @FXML
    public void onRestartAction() {
        resetGame();
    }

    /**
     * Loads a new word/phrase into the UI and resets the timer.
     * <p>
     * Clears the input field, displays a new random word,
     * updates the level label, and starts the countdown timer.
     * </p>
     */
    private void loadNewWord() {
        txtInput.clear();
        txtInput.setDisable(false);
        lblWord.setText(generator.getRandomWord());
        lblLevel.setText("Level: " + game.getLevel());
        startTimer();
    }

    /**
     * Starts the countdown timer for the current level.
     * <p>
     * Updates the timer label and progress bar every second.
     * When time runs out, triggers automatic validation
     * via {@link #autoValidateOnTimeout()}.
     * </p>
     */
    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        timeLeft = game.getTimeForLevel();
        lblTimer.setText(String.valueOf(timeLeft));
        progress.setProgress(1.0);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            lblTimer.setText(String.valueOf(timeLeft));
            progress.setProgress((double) Math.max(timeLeft, 0) / Math.max(game.getTimeForLevel(), 1));

            if (timeLeft <= 0) {
                timeline.stop();
                autoValidateOnTimeout();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Validates the player's input against the displayed word.
     * <p>
     * If correct, shows a success message, increases the level,
     * and loads a new word. If incorrect, resets consecutive streak,
     * shows an error message, and loads another word at the same level.
     * </p>
     */
    private void validateInput() {
        if (timeline != null) timeline.stop();

        String userText = txtInput.getText() == null ? "" : txtInput.getText().trim();
        String targetWord = lblWord.getText();

        if (userText.equals(targetWord)) {
            showMessage("Correct! Level up.", "success");
            game.nextLevel();
            loadNewWord();
        } else {
            game.resetConsecutive();
            showMessage("Incorrect. You stay at the same level, try again.", "error");
            loadNewWord();
        }
    }

    /**
     * Validates automatically when the timer reaches zero.
     * <p>
     * If the input matches the target word, the player levels up.
     * Otherwise, the level remains the same and a new word is shown.
     * </p>
     */
    private void autoValidateOnTimeout() {
        String userText = txtInput.getText() == null ? "" : txtInput.getText().trim();
        String targetWord = lblWord.getText();

        if (userText.equals(targetWord)) {
            showMessage("Correct at the last second! Level up.", "success");
            game.nextLevel();
        } else {
            game.resetConsecutive();
            showMessage("Time is up. You stay at the same level.", "error");
        }
        loadNewWord();
    }

    /**
     * Displays a visual message to the player.
     *
     * @param msg  the message text to display
     * @param type the type of message ("success" or "error"),
     *             used to determine styling
     */
    private void showMessage(String msg, String type) {
        lblMessage.setText(msg);
        if ("success".equals(type)) {
            lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    /**
     * Resets the game to level 1.
     * <p>
     * Stops the timer, clears messages, resets the game model,
     * and loads a new word for the first level.
     * </p>
     */
    private void resetGame() {
        if (timeline != null) timeline.stop();
        game.reset();
        lblMessage.setText("");
        loadNewWord();
    }
}
