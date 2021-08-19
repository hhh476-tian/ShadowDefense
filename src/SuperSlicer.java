import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * represent a super slicer
 */
public class SuperSlicer extends Slicer {
    public static final String SLICER_TYPE = "superslicer";
    public static final int HEALTH = 1;
    public static final int REWARD = 15;
    public static final double SPEED = 1.5;
    public static final int PENALTY = 2;
    public static final String IMAGE_FILENAME = "res/images/superslicer.png";
    // distance to spawn children
    private static final int OFFSET = 10;

    /**
     * Creates a super ApexSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     */
    public SuperSlicer(List<Point> polyline, Point startPoint) {
        super(polyline, startPoint, IMAGE_FILENAME);
        super.setCurrentHealth(HEALTH);
        super.setReward(REWARD);
        super.setSpeed(SPEED);
        super.setPenalty(PENALTY);
    }

    /**
     * Creates a new SuperSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     * @param targetPointIndex the next index in the polyline to move to
     */
    public SuperSlicer(List<Point> polyline, Point startPoint, int targetPointIndex) {
        super(polyline, startPoint, IMAGE_FILENAME, targetPointIndex);
        super.setCurrentHealth(HEALTH);
        super.setReward(REWARD);
        super.setSpeed(SPEED);
        super.setPenalty(PENALTY);
    }

    /**
     * spawn children when dead
     * @return a list of spawned slicers
     */
    @Override
    public List<Slicer> spawnChildren() {
        List<Slicer> children = new ArrayList<>();
        Vector2 offset = getPolyline().get(getTargetPointIndex()).asVector()
                .sub(getPolyline().get(getTargetPointIndex()-1).asVector()).normalised().mul(OFFSET);
        children.add(new RegularSlicer(this.getPolyline(),
                this.getCenter().asVector().add(offset).asPoint(), getTargetPointIndex()));
        children.add(new RegularSlicer(this.getPolyline(),
                this.getCenter().asVector().sub(offset).asPoint(), getTargetPointIndex()));
        return children;
    }
}


