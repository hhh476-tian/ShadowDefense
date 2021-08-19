public abstract class Event {
    public static final double DELAY_FACTOR = 0.001;
    private boolean eventStarted = false;
    private boolean eventFinished = false;
    private double frameCount;

    public void setEventStarted(boolean eventStarted) {
        this.eventStarted = eventStarted;
    }

    public void setEventFinished(boolean eventFinished) {
        this.eventFinished = eventFinished;
    }

    public double getFrameCount() {
        return frameCount;
    }

    public void increaseFrameCount(double amount) {
        frameCount += amount;
    }

    public void setFrameCount(double amount) {
        frameCount = amount;
    }

    public boolean isEventStarted() {
        return eventStarted;
    }

    public boolean isEventFinished() {
        return eventFinished;
    }

    public void startEvent() {
        this.eventStarted = true;
        frameCount = 0;
    }

    public abstract void update();
}
