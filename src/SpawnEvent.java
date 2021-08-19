/**
 * represent the spawn event
 * partly adapted from the provided solution
 * from https://gitlab.eng.unimelb.edu.au/swen20003-S1-2020/rohylj/rohylj-project-1/tree/master
 */
public class SpawnEvent extends Event {
    public static final String SPAWN_EVENT = "spawn";
    private String slicerType;
    private int spawnNumber;
    private double spawnDelay;
    private int spawnedNum;

    /**
     * create a new spawn event
     * @param spawnNumber the number of slicers to spawn
     * @param slicerType the type of slicers to spawn
     * @param spawnDelay the delay between each spawn
     */
    public SpawnEvent(int spawnNumber, String slicerType, int spawnDelay) {
        this.slicerType = slicerType;
        this.spawnDelay = spawnDelay * Event.DELAY_FACTOR;
        this.spawnNumber = spawnNumber;
        this.spawnedNum = 0;
        super.setFrameCount(Double.MAX_VALUE);
    }

    /**
     * to spawn corresponding slicers
     */
    @Override
    public void update() {
        // Increase the frame counter by the current timescale
        super.increaseFrameCount(GameStatus.getTimescale());

        if (super.isEventStarted() && spawnedNum != spawnNumber
                && (super.getFrameCount() / ShadowDefend.FPS) > spawnDelay) {
            SlicerManager.getManager().createSprite(GameStatus.getCurrentLevel().getPolyline().get(0), slicerType);
            spawnedNum++;
            super.setFrameCount(0);
        }

        if (spawnedNum == spawnNumber && super.getFrameCount() > spawnDelay) {
            super.setEventFinished(true);
        }
    }
}
