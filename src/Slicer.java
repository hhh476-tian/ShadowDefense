import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * A regular slicer.
 * this class is from the provided solution
 * from https://gitlab.eng.unimelb.edu.au/swen20003-S1-2020/rohylj/rohylj-project-1/blob/master/src/lists/Slicer.java
 */
public abstract class Slicer extends Sprite {
    private List<Point> polyline;
    private int targetPointIndex;
    private boolean finished = false;
    private boolean dead = false;
    private int currentHealth;
    private double speed;
    private int reward;
    private int penalty;

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    /**
     * Creates a new Slicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     * @param srcImage   the file name of the image of the slicer
     */
    public Slicer(List<Point> polyline, Point startPoint, String srcImage) {
        super(startPoint, srcImage);
        this.polyline = polyline;
        this.targetPointIndex = 1;
    }

    /**
     * Creates a new Slicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     * @param srcImage   the file name of the image of the slicer
     * @param targetPointIndex the next target point index
     */
    public Slicer(List<Point> polyline, Point startPoint, String srcImage, int targetPointIndex) {
        super(startPoint, srcImage);
        this.polyline = polyline;
        this.targetPointIndex = targetPointIndex;
    }

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     */
    @Override
    public void update(Input input) {
        if (currentHealth <= 0) {
            dead = true;
        }
        if (finished || dead) {
            return;
        }
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < this.speed * GameStatus.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(this.speed * GameStatus.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
    }

    /**
     * when the slicer dies, it will spawn children.
     * Default behavior, spawn no children.
     */
    public List<Slicer> spawnChildren() {
        return null;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDead() {
            return dead;
    };

    public void getDamage(int amount) {
        this.currentHealth -= amount;
    }

    public int getReward() {
        return reward;
    }

    public int getPenalty() {
        return penalty;
    }

    public List<Point> getPolyline() {
        return polyline;
    }

    public int getTargetPointIndex() {
        return targetPointIndex;
    }
}
