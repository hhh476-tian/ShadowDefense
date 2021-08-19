import bagel.Image;
import bagel.Input;
import bagel.MouseButtons;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * regulate the creation and the updates of active towers
 */
public class ActiveTowerManager extends SpriteManager {
    public static List<String> allActiveTowerNames = Arrays.asList(Tank.NAME, SuperTank.NAME);
    private List<ActiveTower> activeTowers;

    private static ActiveTowerManager activeTowerManager = null;
    private ActiveTowerManager() {
        activeTowers = new ArrayList<>();
    }

    /**
     * get the manager
     * @return the manager
     */
    public static ActiveTowerManager getManager() {
        if (activeTowerManager == null) {
            activeTowerManager = new ActiveTowerManager();
        }
        return activeTowerManager;
    }

    /**
     * create a new active tower
     * @param point the location
     * @param SpriteName the name of the active tower
     */
    @Override
    public void createSprite(Point point, String SpriteName) {
        if (SpriteName.equals(Tank.NAME)) {
            activeTowers.add(new Tank(point));
        }

        if (SpriteName.equals(SuperTank.NAME)) {
            activeTowers.add(new SuperTank(point));
        }
    }

    /**
     * update the active tower
     * @param input the input from the user
     */
    @Override
    public void update(Input input) {
        placing(input);
        for (ActiveTower tower : activeTowers) {
            tower.update(input);
        }
    }

    /**
     * placing and purchasing of the towers
     * @param input the input from the user
     */
    private void placing(Input input) {
        if (GameStatus.isPlacing()) {

            if (!allActiveTowerNames.stream().anyMatch(s -> s.equals(GameStatus.getPlacingTower()))) {
                return;
            }

            // draw the placing image
            if (checkInBounds(input.getMousePosition(), GameStatus.getPlacingTower())) {
                Image image = new Image(towerImageName(GameStatus.getPlacingTower()));
                image.draw(input.getMouseX(), input.getMouseY());
            }

            // place the tower
            if (input.wasPressed((MouseButtons.LEFT))
                    && checkInBounds(input.getMousePosition(), GameStatus.getPlacingTower())) {
                this.createSprite(input.getMousePosition(), GameStatus.getPlacingTower());
                GameStatus.decreaseMoney(towerPrice(GameStatus.getPlacingTower()));
                GameStatus.setIsPlacing(false);
            }

            // cancel the placing
            if (input.wasPressed(MouseButtons.RIGHT)) {
                GameStatus.setIsPlacing(false);
            }

        }

        // start placing
        if (input.wasPressed(MouseButtons.LEFT) && !GameStatus.isPlacing()) {
            if (BuyPanel.getTankBoundingBox().intersects(input.getMousePosition())
                && GameStatus.getMoney() >= Tank.PRICE) {
                GameStatus.setIsPlacing(true);
                GameStatus.setPlacingTower(Tank.NAME);
            }

            if (BuyPanel.getSuperTankBoundingBox().intersects(input.getMousePosition())
                && GameStatus.getMoney() >= SuperTank.PRICE) {
                GameStatus.setIsPlacing(true);
                GameStatus.setPlacingTower(SuperTank.NAME);
            }
        }
    }

    /**
     * check if the placing tower can be drawn or not
     * @param point the center of the placing tower
     * @return true if in a legal area, false otherwise
     */
    private boolean checkInBounds(Point point, String towerName) {

        // check if in the window
        if (point.x <= 0 || point.x >= Window.getWidth() || point.y <= 0 || point.y >= Window.getHeight()) {
            return false;
        }

        // check if in the blocked area in the map
        if (GameStatus.getCurrentLevel()
                .getMap().getPropertyBoolean((int) point.x, (int) point.y, "blocked", false)) {
            return false;
        }

        // check whether overlap with other towers
        for (ActiveTower tower : activeTowers) {
            if (tower.getRect().intersects(new Image(towerImageName(towerName)).getBoundingBoxAt(point))) {
                return false;
            }
        }

        // check if in the point intersects with a panel
        if (BuyPanel.getBuyPanelBoundingBox().intersects(point)
                || StatusPanel.getStatusPanelBoundingBox().intersects(point)) {
            return false;
        }

        return true;
    }

    /**
     * choose the image file name according to the tower name
     * @param name
     * @return the image file name
     */
    public static String towerImageName(String name) {
        if (name.equals(Tank.NAME)) {
            return Tank.IMAGE_FILENAME;
        }

        if (name.equals(SuperTank.NAME)) {
            return SuperTank.IMAGE_FILENAME;
        }

        return null;
    }

    /**
     * get the price of the tower according to the tower name
     * @param name
     * @return the price of the tower
     */
    public static int towerPrice(String name) {
        if (name.equals(Tank.NAME)) {
            return Tank.PRICE;
        }

        if (name.equals(SuperTank.NAME)) {
            return SuperTank.PRICE;
        }

        return 0;
    }

    /**
     * remove all towers
     */
    public static void removeAllTowers() {
        getManager().activeTowers.clear();
    }
}
