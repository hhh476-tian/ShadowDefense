import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * represents an active tower
 */
public abstract class ActiveTower extends Sprite {
    public static final double COOLDOWN_FACTOR = 0.001;
    private int radius;
    private int damage;
    private int cooldown;
    private int price;
    private String projectileImageFileName;
    private boolean readyToFire = true;
    private double framecount = Double.MAX_VALUE;
    private List<Projectile> projectiles;

    /**
     * create new active tower
     * @param point the location
     * @param imageSrc the image source file
     */
    public ActiveTower(Point point, String imageSrc) {
        super(point, imageSrc);
        projectiles = new ArrayList<>();
    }

    @Override
    public void update(Input input) {

        if (framecount / ShadowDefend.FPS > cooldown * COOLDOWN_FACTOR) {
            readyToFire = true;
        }

        // face enemy and shoot
        Slicer target = chooseEnemy();
        if (target != null) {
            this.setAngle(Math.PI / 2 + Math.atan2(target.getCenter().y - this.getCenter().y,
                    target.getCenter().x - this.getCenter().x));
            if (readyToFire) {
                projectiles.add(new Projectile(this, target, this.projectileImageFileName));
                readyToFire = false;
                framecount = 0;
            }
        }

        super.update(input);

        // update the projectiles
        for (Projectile projectile : projectiles) {
            projectile.update(input);
            if (projectile.isHit()) {
                projectile.getTarget().getDamage(this.damage);
            }
        }
        projectiles = projectiles.stream().filter(p -> !p.isHit()).collect(Collectors.toList());

        framecount += GameStatus.getTimescale();
    }

    /**
     * choose a slicer to attack
     * @return a slicer in the tower's range, null if nothing
     */
    private Slicer chooseEnemy() {
        for (Slicer slicer : SlicerManager.getSlicers()) {
            if (slicer.getCenter().asVector().sub(this.getCenter().asVector()).length() < this.radius) {
                return slicer;
            }
        }
        return null;
    }

    public void setProjectileImageFileName(String projectileImageFileName) {
        this.projectileImageFileName = projectileImageFileName;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
