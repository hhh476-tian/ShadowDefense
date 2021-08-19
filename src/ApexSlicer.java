import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * represent an apex slicer
 */
public class ApexSlicer extends Slicer {
    public static final String SLICER_TYPE = "apexslicer";;
    public static final int HEALTH = 25;
    public static final int REWARD = 150;
    public static final double SPEED = 0.75;
    public static final int PENALTY = 16;
    public static final String IMAGE_FILENAME = "res/images/apexslicer.png";
    // distance to spawn children
    private static final double OFFSET = 10;

    /**
     * Creates a new ApexSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     */
    public ApexSlicer(List<Point> polyline, Point startPoint) {
        super(polyline, startPoint, IMAGE_FILENAME);
        super.setCurrentHealth(HEALTH);
        super.setReward(REWARD);
        super.setSpeed(SPEED);
        super.setPenalty(PENALTY);
    }

    /**
     * Creates a new ApexSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     * @param targetPointIndex the next index in the polyline to move to
     */
    public ApexSlicer(List<Point> polyline, Point startPoint, int targetPointIndex) {
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
        children.add(new MegaSlicer(this.getPolyline(),
                this.getCenter().asVector().add(offset).asPoint(), getTargetPointIndex()));
        children.add(new MegaSlicer(this.getPolyline(),
                this.getCenter().asVector().sub(offset).asPoint(), getTargetPointIndex()));
        children.add(new MegaSlicer(this.getPolyline(),
                this.getCenter().asVector().add(offset.mul(2)).asPoint(), getTargetPointIndex()));
        children.add(new MegaSlicer(this.getPolyline(),
                this.getCenter().asVector().sub(offset.mul(2)).asPoint(), getTargetPointIndex()));
        return children;
    }
}


