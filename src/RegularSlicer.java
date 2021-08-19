import bagel.Input;
import bagel.util.Point;
import java.util.List;

/**
 * represent a regular slicer
 */
public class RegularSlicer extends Slicer {
    public static final String SLICER_TYPE = "slicer";
    public static final int HEALTH = 1;
    public static final int REWARD = 2;
    public static final double SPEED = 2;
    public static final int PENALTY = 1;
    public static final String IMAGE_FILENAME = "res/images/slicer.png";

    /**
     * Creates a new RegularSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     */
    public RegularSlicer(List<Point> polyline, Point startPoint) {
        super(polyline, startPoint, IMAGE_FILENAME);
        super.setCurrentHealth(HEALTH);
        super.setReward(REWARD);
        super.setSpeed(SPEED);
        super.setPenalty(PENALTY);
    }

    /**
     * Creates a new RegularSlicer
     *
     * @param polyline   The polyline that the slicer must traverse (must have at least 1 point)
     * @param startPoint the spawning point of the slicer
     * @param targetPointIndex the next index in the polyline to move to
     */
    public RegularSlicer(List<Point> polyline, Point startPoint, int targetPointIndex) {
        super(polyline, startPoint, IMAGE_FILENAME, targetPointIndex);
        super.setCurrentHealth(HEALTH);
        super.setReward(REWARD);
        super.setSpeed(SPEED);
        super.setPenalty(PENALTY);
    }
}

