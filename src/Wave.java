import java.util.ArrayList;
import java.util.List;

/**
 * a class represents a wave
 */
public class Wave {
    public final int waveNumber;

    // indicate the position of information in the string representing wave event
    private static final int EVENT_TYPE_POS_IN_EVENT_STRING = 1;
    private static final int DELAY_POS_IN_EVENT_STRING = 2;
    private static final int SLICER_NUMBER_POS_IN_EVENT_STRING = 2;
    private static final int SLICER_TYPE_POS_IN_EVENT_STRING = 3;
    private static final int SPAWN_DELAY_IN_EVENT_STRING = 4;
    private final List<Event> events;
    private boolean waveStarted = false;
    private boolean waveFinished = false;
    private boolean allEventsFinished = false;
    private int currentEventNumber = 0;

    public boolean isWaveStarted() {
        return waveStarted;
    }

    public boolean isWaveFinished() {
        return waveFinished;
    }

    public void startWave() {
        this.waveStarted = true;
    }

    /**
     * create a new wave
     * @param waveNumber the wave number
     */
    public Wave(int waveNumber) {
        this.waveNumber = waveNumber;
        this.events = new ArrayList<>();
    }

    /**
     * add an event to the wave
     * @param eventString the string which represents the information of the event
     */
    public void addEvent(String eventString) {
        String eventInfo[] = eventString.split(",");
        if (eventInfo[EVENT_TYPE_POS_IN_EVENT_STRING].equals(DelayEvent.DELAY_EVENT)) {
            int delay = Integer.parseInt(eventInfo[DELAY_POS_IN_EVENT_STRING]);
            events.add(new DelayEvent(delay));
        } else {
            int spawnNumber = Integer.parseInt(eventInfo[SLICER_NUMBER_POS_IN_EVENT_STRING]);
            String slicerType = eventInfo[SLICER_TYPE_POS_IN_EVENT_STRING];
            int spawnDelay = Integer.parseInt(eventInfo[SPAWN_DELAY_IN_EVENT_STRING]);
            events.add(new SpawnEvent(spawnNumber, slicerType, spawnDelay));
        }
    }

    public void update() {
        if (!this.waveStarted || this.waveFinished) {
            return;
        }
        Event currentEvent = events.get(currentEventNumber);
        if (currentEvent.isEventStarted() == false) {
            currentEvent.startEvent();
        }
        currentEvent.update();

        if (currentEvent.isEventFinished()) {
            if (currentEventNumber < events.size() - 1) {
                currentEventNumber++;
            } else {
                allEventsFinished = true;
            }
        }

        if (allEventsFinished && SlicerManager.getManager().isAnySlicerRemaining()) {
            waveFinished = true;
        }
    }
}
