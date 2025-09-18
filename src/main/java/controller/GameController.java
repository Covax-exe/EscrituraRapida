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
 * Controlador principal del juego Escritura Rápida
 * Se encarga de manejar la interfaz (FXML), el temporizador y la lógica de validación
 *
 * Cambios realizados:
 * - Al fallar, el jugador permanece en el mismo nivel y se le genera una nueva palabra
 * - Los botones en FXML llaman a los métodos onValidateAction y onRestartAction
 * - Se vincula el ancho del lblWord al ancho del contenedor central (centerBox)
 *   para que texto largo haga wrap correctamente
 * - Se usa .trim() al validar para ignorar espacios accidentales al inicio/fin
 */
public class GameController {

    @FXML private Label lblWord; // Muestra la palabra/frase actual
    @FXML private Label lblLevel; // Muestra el nivel actual
    @FXML private Label lblTimer; // Muestra el tiempo restante
    @FXML private Label lblMessage; // Muestra mensajes de éxito o error
    @FXML private TextField txtInput; // Campo donde escribe el jugador
    @FXML private Button btnValidate; // Botón de validación
    @FXML private Button btnRestart; // Botón de reinicio
    @FXML private ProgressBar progress; // Barra de progreso del tiempo
    @FXML private VBox centerBox; // contenedor central, usado para binding de ancho

    private Game game; // Modelo que guarda el estado del juego
    private Timeline timeline; // Temporizador
    private int timeLeft; // Tiempo restante en segundos
    private WordGenerator generator; // Generador de palabras/frases

    @FXML
    public void initialize() {
        // Inicializamos modelo y generador
        game = new Game();
        generator = new WordGenerator();

        // Aseguramos que Enter valide
        txtInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onValidateAction();
            }
        });

        // Vinculaciones visuales que deben ejecutarse cuando la escena esté lista
        Platform.runLater(() -> {
            // Bind: lblWord usa el ancho del centerBox menos margen para realizar wrap
            if (centerBox != null) {
                lblWord.maxWidthProperty().bind(centerBox.widthProperty().subtract(40));
                lblWord.setWrapText(true);
            }
        });

        // Arrancamos el primer nivel
        loadNewWord();
    }

    /**
     * Método llamado desde FXML (onAction) para el botón Validar
     */
    @FXML
    public void onValidateAction() {
        validateInput();
    }

    /**
     * Método llamado desde FXML (onAction) para el botón Reiniciar
     */
    @FXML
    public void onRestartAction() {
        resetGame();
    }

    /**
     * Carga una nueva palabra/frase y reinicia el temporizador
     */
    private void loadNewWord() {
        txtInput.clear();
        txtInput.setDisable(false); // habilitar campo en caso de que estuviera deshabilitado
        lblWord.setText(generator.getRandomWord());
        lblLevel.setText("Nivel: " + game.getLevel());
        startTimer();
    }

    /**
     * Inicia el temporizador para el nivel actual
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
            // evitar division por cero
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
     * Valida la entrada del jugador (botón o ENTER).
     * Si es correcto -> subir nivel.
     * Si es incorrecto -> se mantiene en el mismo nivel (no termina la partida),
     *                     se resetea racha consecutiva y se genera otra palabra.
     */
    private void validateInput() {
        if (timeline != null) timeline.stop();

        // Ignoramos espacios al inicio/fin con trim()
        String userText = txtInput.getText() == null ? "" : txtInput.getText().trim();
        String targetWord = lblWord.getText();

        if (userText.equals(targetWord)) {
            showMessage("¡Correcto! Nivel superado.", "success");
            game.nextLevel(); // avanza nivel y potencia dificultad cuando corresponde
            loadNewWord();
        } else {
            // fallo: mantener nivel, resetear racha de aciertos consecutivos
            game.resetConsecutive();
            showMessage("Incorrecto. Mantienes el mismo nivel, inténtalo de nuevo.", "error");
            // Cargar otra palabra en el mismo nivel
            loadNewWord();
        }
    }

    /**
     * Valida automáticamente cuando el tiempo llega a 0.
     * Si es correcto -> subir nivel.
     * Si es incorrecto o vacío -> mantener nivel y otra palabra.
     */
    private void autoValidateOnTimeout() {
        String userText = txtInput.getText() == null ? "" : txtInput.getText().trim();
        String targetWord = lblWord.getText();

        if (userText.equals(targetWord)) {
            showMessage("¡Correcto en el último segundo! Nivel superado.", "success");
            game.nextLevel();
        } else {
            game.resetConsecutive();
            showMessage("Tiempo agotado. Mantienes el mismo nivel.", "error");
        }
        loadNewWord();
    }

    /**
     * Muestra un mensaje visual al jugador.
     *
     * @param msg  Texto a mostrar
     * @param type Tipo de mensaje ("success" o "error")
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
     * Reinicia el juego desde nivel 1.
     */
    private void resetGame() {
        if (timeline != null) timeline.stop();
        game.reset();
        lblMessage.setText("");
        loadNewWord();
    }
}
