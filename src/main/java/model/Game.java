package model;

/**
 * Clase que maneja el estado del juego:
 * nivel actual, número de aciertos consecutivos y tiempo por nivel.
 */
public class Game {

    private int level;              // Nivel actual del jugador
    private int consecutiveCorrect; // Número de respuestas correctas consecutivas
    private int timePerLevel;       // Tiempo asignado a cada nivel en segundos

    /**
     * Constructor: arranca un nuevo juego en nivel 1 con 20 segundos.
     */
    public Game() {
        reset();
    }

    /**
     * Reinicia el juego a sus valores iniciales.
     */
    public void reset() {
        level = 1;
        consecutiveCorrect = 0;
        timePerLevel = 20; // Tiempo inicial
    }

    /**
     * Avanza al siguiente nivel después de un acierto.
     * Cada 5 niveles reduce el tiempo en 2 segundos (mínimo 2 segundos).
     */
    public void nextLevel() {
        level++;
        consecutiveCorrect++;

        if (consecutiveCorrect % 5 == 0 && timePerLevel > 2) {
            timePerLevel -= 2; // Aumenta la dificultad
            if (timePerLevel < 2) {
                timePerLevel = 2;
            }
        }
    }

    /**
     * Resetea la racha de aciertos consecutivos (se usa al fallar).
     */
    public void resetConsecutive() {
        consecutiveCorrect = 0;
    }

    /**
     * Devuelve el nivel actual.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Devuelve el tiempo por nivel en segundos.
     */
    public int getTimeForLevel() {
        return timePerLevel;
    }
}
