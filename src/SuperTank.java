import bagel.util.Point;

/**
 * represents a super tank
 */
public class SuperTank extends ActiveTower {
    public static final String NAME = "superTank";
    public static final int RADIUS = 150;
    public static final int DAMAGE = 3;
    public static final int COOLDOWN = 500;
    public static final int PRICE = 600;
    public static final String IMAGE_FILENAME = "res/images/supertank.png";
    public static final String PROJECTILE_IMAGE_FILENAME = "res/images/supertank_projectile.png";

    /**
     * create a new tank
     * @param point the location
     */
    public SuperTank(Point point) {
        super(point, IMAGE_FILENAME);
        this.setProjectileImageFileName(PROJECTILE_IMAGE_FILENAME);
        this.setRadius(RADIUS);
        this.setDamage(DAMAGE);
        this.setCooldown(COOLDOWN);
        this.setPrice(PRICE);
    }
}
