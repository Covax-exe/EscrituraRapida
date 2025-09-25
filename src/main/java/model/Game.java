package model;

/**
 * Game class.
 * <p>
 * This class manages the state of the Fast Typing Game, including
 * the current level, the number of consecutive correct answers, and
 * the available time per level.
 * </p>
 *
 * <p><b>Main responsibilities:</b></p>
 * <ul>
 *   <li>Track the player's level progression</li>
 *   <li>Adjust the time per level as difficulty increases</li>
 *   <li>Reset the game state when needed</li>
 *   <li>Manage the streak of consecutive correct answers</li>
 * </ul>
 *
 * @author Lina Vanessa Cosme Arce - 2436459
 * @version 1.8
 */
public class Game {

    private int level;              // Current player level
    private int consecutiveCorrect; // Number of consecutive correct answers
    private int timePerLevel;       // Time allowed per level in seconds

    /**
     * Constructs a new game starting at level 1 with 20 seconds.
     * <p>
     * Calls {@link #reset()} to initialize values.
     * </p>
     */
    public Game() {
        reset();
    }

    /**
     * Resets the game to its initial values.
     * <p>
     * Sets the level to 1, consecutive streak to 0,
     * and time per level back to 20 seconds.
     * </p>
     */
    public void reset() {
        level = 1;
        consecutiveCorrect = 0;
        timePerLevel = 20;
    }

    /**
     * Advances to the next level after a correct answer.
     * <p>
     * Increases the level and consecutive correct counter.
     * Every 5 consecutive correct answers, reduces the time per level
     * by 2 seconds until a minimum of 2 seconds is reached.
     * </p>
     */
    public void nextLevel() {
        level++;
        consecutiveCorrect++;

        if (consecutiveCorrect % 5 == 0 && timePerLevel > 2) {
            timePerLevel -= 2;
            if (timePerLevel < 2) {
                timePerLevel = 2;
            }
        }
    }

    /**
     * Resets the consecutive correct answers counter.
     * <p>
     * Used when the player fails a word.
     * </p>
     */
    public void resetConsecutive() {
        consecutiveCorrect = 0;
    }

    /**
     * Gets the current player level.
     *
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the allowed time for the current level.
     *
     * @return the time per level in seconds
     */
    public int getTimeForLevel() {
        return timePerLevel;
    }
}
