public class DelayEvent extends Event {
    public static final String DELAY_EVENT =  "delay";
    private double delay;

    /**
     * create a new delay event
     * @param delay the delay
     */
    public DelayEvent(int delay) {
        this.delay = delay * Event.DELAY_FACTOR;
    }

    @Override
    public void update() {
        super.increaseFrameCount(GameStatus.getTimescale());

        if (super.getFrameCount() / ShadowDefend.FPS > delay) {
            super.setEventFinished(true);
        }
    }
}
