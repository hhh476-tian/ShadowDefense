import bagel.util.Point;

/**
 * represents a tank
 */
public class Tank extends ActiveTower {
    public static final String NAME = "Tank";
    public static final int RADIUS = 100;
    public static final int DAMAGE = 1;
    public static final int COOLDOWN = 1000;
    public static final int PRICE = 250;
    public static final String IMAGE_FILENAME = "res/images/tank.png";
    public static final String PROJECTILE_IMAGE_FILENAME = "res/images/tank_projectile.png";

    /**
     * create a new tank
     * @param point the location of the tank
     */
    public Tank(Point point) {
        super(point, IMAGE_FILENAME);
        this.setProjectileImageFileName(PROJECTILE_IMAGE_FILENAME);
        this.setRadius(RADIUS);
        this.setDamage(DAMAGE);
        this.setCooldown(COOLDOWN);
        this.setPrice(PRICE);
    }
}
