import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * represents an airplane
 */
public class Airplane extends Sprite {
    public static final String NAME = "Airplane";
    public static final double HORIZONTAL_ANGLE = Math.PI / 2;
    public static final double VERTICAL_ANGLE = Math.PI;
    public static final int SPEED = 3;
    public static final int PRICE = 500;
    public static final int MIN_DROP_TIME = 1;
    public static final int MAX_DROP_TIME = 2;
    public static final String IMAGE_FILENAME = "res/images/airsupport.png";
    private static double nextAngle = HORIZONTAL_ANGLE;
    private static double numberOfAirPlanes = 0;

    private boolean reachEnd;
    private List<Explosive> explosives;
    private double framecount;
    private double nextDrop;

    /**
     * create a new airplane
     * @param point the location of the mouse when placing the airplane
     */
    public Airplane(Point point) {
        super(getStartingPoint(point), IMAGE_FILENAME);
        this.setAngle(nextAngle);

        this.reachEnd = false;
        explosives = new ArrayList<>();
        framecount = 0;
        nextDrop = Math.random() * (MAX_DROP_TIME - MIN_DROP_TIME) + MIN_DROP_TIME;

        // set next angle
        numberOfAirPlanes++;
        nextAngle = numberOfAirPlanes % 2 == 0 ? HORIZONTAL_ANGLE : VERTICAL_ANGLE;
    }

    /**
     * update the airplane
     * @param input the input from the user
     */
    @Override
    public void update(Input input) {
        if (reachEnd){
            return;
        }

        if (this.getCenter().x > Window.getWidth() || this.getCenter().y > Window.getHeight()) {
            this.reachEnd = true;
            return;
        }

        Vector2 direction = this.getAngle() == HORIZONTAL_ANGLE ? new Vector2(1, 0) : new Vector2(0, 1);
        super.move(direction.mul(SPEED * GameStatus.getTimescale()));
        super.update(input);

        framecount += GameStatus.getTimescale();

        if (framecount / ShadowDefend.FPS > nextDrop) {
            explosives.add(new Explosive(this.getCenter()));
            nextDrop = Math.random() * (MAX_DROP_TIME - MIN_DROP_TIME) + MIN_DROP_TIME;
            framecount = 0;
        }

        // update the explosives
        for (Explosive explosive : explosives) {
            explosive.update(input);
        }
        explosives = explosives.stream().filter(e -> !e.isExploded()).collect(Collectors.toList());
    }

    /**
     * determine the starting point of the airplane
     * @param point the point where the mouse hovers when creating the airplane
     * @return the starting point for the airplane
     */
    private static Point getStartingPoint(Point point) {
        if (numberOfAirPlanes % 2 == 0) {
            return new Point(0, point.y);
        } else {
            return new Point(point.x, 0);
        }
    }

    public static double getNextAngle() {
        return nextAngle;
    }

    public boolean isFinished() {
        return reachEnd && explosives.isEmpty();
    }
}
