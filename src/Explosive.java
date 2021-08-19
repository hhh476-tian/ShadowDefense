import bagel.Input;
import bagel.util.Point;

/**
 * represent an explosive dropped by the airplan
 */
public class Explosive extends Sprite {
    public static final String IMAGE_FILENAME = "res/images/explosive.png";
    public static final int DAMAGE = 500;
    public static final int RADIUS = 200;
    public static final int DENOTATION_DELAY = 2;
    private boolean exploded = false;
    private double framecount = 0;

    /**
     * create a new explosive
     * @param point the location
     */
    public Explosive(Point point) {
        super(point, IMAGE_FILENAME);
    }

    @Override
    public void update(Input input) {
        framecount += GameStatus.getTimescale();

        // explode
        if (framecount / ShadowDefend.FPS > DENOTATION_DELAY) {
            exploded = true;
            for (Slicer slicer : SlicerManager.getSlicers()) {
                if (slicer.getCenter().asVector().sub(this.getCenter().asVector()).length() < RADIUS) {
                    slicer.getDamage(DAMAGE);
                }
            }
            return;
        }

        super.update(input);
    }

    public boolean isExploded() {
        return exploded;
    }
}
