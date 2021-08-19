import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class contains the information ans the status of the game
 * it uses some parts of the provided solution
 * from https://gitlab.eng.unimelb.edu.au/swen20003-S1-2020/rohylj/rohylj-project-1/tree/master
 */
public class GameStatus {
    public static final int INITIAL_TIMESCALE = 1;
    public static final int INITIAL_LIVES = 25;
    public static final int INITIAL_MONEY = 500;
    private static int money = INITIAL_MONEY;
    private static int lives = INITIAL_LIVES;
    private static int timescale = INITIAL_TIMESCALE;
    private static int waveNumber = 1;
    private static int currentLevelNumber = 1;
    public static final int TOTAL_LEVEL_NUMBER = 2;
    private static final String LEVEL_ONE_MAP_FILENAME = "res/levels/1.tmx";
    private static final String LEVEL_TWO_MAP_FILENAME = "res/levels/2.tmx";
    private static final List<Level> levels = new ArrayList<>(Arrays.asList(new Level(LEVEL_ONE_MAP_FILENAME),
            new Level(LEVEL_TWO_MAP_FILENAME)));

    public static boolean isWin() {
        return isWin;
    }

    public static boolean isPlacing() {
        return isPlacing;
    }

    private static boolean waveStarted = false;
    private static boolean isWin = false;
    private static boolean isPlacing = false;
    private static String PlacingTower = null;

    public static void setIsPlacing(boolean placing) {
        isPlacing = placing;
    }

    public static void setPlacingTower(String placingTower) {
        PlacingTower = placingTower;
    }

    public static String getPlacingTower() {
        return PlacingTower;
    }

    public static void decreaseMoney(int amount) {
        money -= amount;
    }

    public static void increaseMoney(int amount) {
        money += amount;
    }

    public static int getMoney() {
        return money;
    }

    public static int getLives() {
        return lives;
    }

    public static int getWaveNumber() {
        return waveNumber;
    }

    public static void increaseWaveNumber() {
        waveNumber++;
    };

    public static void setWaveStarted(boolean isStarted) {
        waveStarted = isStarted;
    }

    public static boolean isWaveStarted() {
        return waveStarted;
    }

    public static void decreaseLives(int amount) {
        lives -= amount;
    }

    public static void update(Input input) {
        if (input.wasPressed(Keys.S)) {
            waveStarted = true;
        }

        if (waveStarted && input.wasPressed(Keys.L)) {
            GameStatus.increaseTimescale();
        }

        if (waveStarted && input.wasPressed(Keys.K)) {
            GameStatus.decreaseTimescale();
        }

        // move to a new level if current level is finished
        if (GameStatus.getCurrentLevel().isFinished()) {
            GameStatus.moveToNextLevel();
        }
    }

    public static int getTimescale() {
        return timescale;
    }

    /**
     * Increases the timescale
     */
    private static void increaseTimescale() {
        timescale++;
    }

    /**
     * Decreases the timescale but doesn't go below the base timescale
     */
    private static void decreaseTimescale() {
        if (timescale > INITIAL_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * get the current level
     * @return the current level
     */
    public static Level getCurrentLevel() {
        return levels.get(currentLevelNumber - 1);
    }

    /**
     * move to the next level and reset the game status
     */
    public static void moveToNextLevel() {
        // if pass all levels, we win!
        if (currentLevelNumber == TOTAL_LEVEL_NUMBER) {
            isWin = true;
            return;
        }
        // increase current level number
        currentLevelNumber++;
        // reset game status
        money = INITIAL_MONEY;
        lives = INITIAL_LIVES;
        timescale = INITIAL_TIMESCALE;
        waveNumber = 1;
        waveStarted = false;
        isWin = false;
        isPlacing = false;
        PlacingTower = null;
        ActiveTowerManager.removeAllTowers();
        AirplaneManager.removeAllAirplanes();
    }
}
