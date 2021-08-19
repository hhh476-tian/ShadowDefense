import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * regulate the creation and updates of airplanes
 */
public class AirplaneManager extends SpriteManager {
    private static List<Airplane> airplanes;
    private static AirplaneManager airplaneManager = null;

    private AirplaneManager() {
        airplanes = new ArrayList<>();
    }

    public static AirplaneManager getManager() {
        if (airplaneManager == null) {
            airplaneManager = new AirplaneManager();
        }
        return airplaneManager;
    }

    @Override
    public void createSprite(Point point, String SpriteName) {
        airplanes.add(new Airplane(point));
    }

    /**
     * the placing and the updates of airplanes
     * @param input the input form the user
     */
    @Override
    public void update(Input input) {
        placing(input);
        for (Airplane airplane : airplanes) {
            airplane.update(input);
        }
        airplanes = airplanes.stream().filter(a -> !a.isFinished()).collect(Collectors.toList());
    }

    /**
     * placing and the purchasing of airplanes
     * @param input the input from the user
     */
    private void placing(Input input) {
        if (GameStatus.isPlacing()) {

            // return if not placing airplanes
            if (!GameStatus.getPlacingTower().equals(Airplane.NAME)) {
                return;
            }

            // draw the placing image
            Image image = new Image(Airplane.IMAGE_FILENAME);
            if (isValidArea(input.getMousePosition())) {
                image.draw(input.getMouseX(), input.getMouseY(),
                        new DrawOptions().setRotation(Airplane.getNextAngle()));
            }

            // actually place the image
            if (input.wasPressed(MouseButtons.LEFT) && isValidArea(input.getMousePosition())) {
                this.createSprite(input.getMousePosition(), Airplane.NAME);
                GameStatus.decreaseMoney(Airplane.PRICE);
                GameStatus.setIsPlacing(false);
            }

            // cancel the placing
            if (input.wasPressed(MouseButtons.RIGHT)) {
                GameStatus.setIsPlacing(false);
            }
        }

        // start placing the airplane
        if (input.wasPressed(MouseButtons.LEFT) && !GameStatus.isPlacing()) {
            if (BuyPanel.getAirplaneBoundingBox().intersects(input.getMousePosition())
                    && GameStatus.getMoney() >= Airplane.PRICE){
                GameStatus.setIsPlacing(true);
                GameStatus.setPlacingTower(Airplane.NAME);
            }
        }
    }

    /**
     * check if the airplane hovering in valid area when placing
     * @param point the center point of the placing airplane
     * @return true if it is in a valid area, false otherwise
     */
    private boolean isValidArea(Point point) {
        // check if in the window
        if (point.x <= 0 || point.x >= Window.getWidth() || point.y <= 0 || point.y >= Window.getHeight()) {
            return false;
        }

        // check if in the point intersects with a panel
        if (BuyPanel.getBuyPanelBoundingBox().intersects(point)
                || StatusPanel.getStatusPanelBoundingBox().intersects(point)) {
            return false;
        }

        return true;
    }

    /**
     * remove all airplanes
     */
    public static void removeAllAirplanes() {
        airplanes.clear();
    }
}
