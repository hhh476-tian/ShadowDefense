import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * a class represents the level of the game
 */
public class Level {
    private String mapFilename;
    private static final String WAVE_FILENAME = "res/levels/waves.txt";
    public static final int BASE_AWARD = 150;
    public static final int WAVE_NUMBER_AWARD = 100;
    private final TiledMap map;
    private final List<Point> polyline;
    private final List<Wave> waves;
    private boolean finished = false;

    public TiledMap getMap() {
        return map;
    }

    public List<Point> getPolyline() {
        return polyline;
    }

    public Level(String mapFilename) {
        this.mapFilename = mapFilename;
        this.map = new TiledMap(mapFilename);
        this.polyline = map.getAllPolylines().get(0);
        waves = new ArrayList<>();
        createWaves();
    }

    /**
     * read in the waves (assume valid input), adapted from the template code provided in Grok
     * https://groklearning.com/learn/unimelb-swen20003-2020-s1/files/3/
     */
    private void createWaves() {
        try (Scanner scanner = new Scanner(new FileReader(WAVE_FILENAME))) {
            while (scanner.hasNext()) {
                String nextWaveEvent = scanner.nextLine();
                // assume waveNumber a positive integer
                int waveNumber = Integer.parseInt(nextWaveEvent.split(",")[0]);
                if (waveNumber - 1 >= waves.size()) {
                    waves.add(new Wave(waveNumber));
                }
                waves.get(waveNumber-1).addEvent(nextWaveEvent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (!GameStatus.isWaveStarted() || finished) {
            return;
        }

        Wave currentWave = waves.get(GameStatus.getWaveNumber() - 1);
        if (currentWave.isWaveStarted() == false) {
            currentWave.startWave();
        }
        currentWave.update();

        if (currentWave.isWaveFinished()) {
            GameStatus.setWaveStarted(false);
            GameStatus.increaseMoney(BASE_AWARD + GameStatus.getWaveNumber()*WAVE_NUMBER_AWARD);
            GameStatus.increaseWaveNumber();
        }

        if (GameStatus.getWaveNumber() == waves.size()) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
