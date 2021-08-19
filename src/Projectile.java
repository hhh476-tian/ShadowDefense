import bagel.Image;
import bagel.Input;

/**
 * represents the projectile from the towers
 */
public class Projectile extends Sprite {
    public static final int SPEED = 10;
    private boolean hit = false;
    private final Slicer target;

    /**
     * create a new projectile
     * @param attacker the active tower where it is from
     * @param target the enemy its aiming
     * @param imageFileName the image source file name
     */
    public Projectile(ActiveTower attacker, Slicer target, String imageFileName) {
        super(attacker.getCenter(), imageFileName);
        this.target = target;
    }

    /**
     * update the projectile
     * @param input the input from the user
     */
    @Override
    public void update(Input input) {
        if (target.getRect().intersects(this.getRect())) {
            hit = true;
            return;
        }
        super.move(target.getCenter().asVector().sub(this.getCenter().asVector())
                .normalised().mul(SPEED * GameStatus.getTimescale()));
        super.update(input);
    }

    public boolean isHit() {
        return hit;
    }

    public Slicer getTarget() {
        return target;
    }
}
