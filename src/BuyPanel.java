import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * a singleton class represents the buy panel
 */
public class BuyPanel {
    private static final String IMAGE_FILENAME = "res/images/buypanel.png";
    private static final String FONT_FILENAME = "res/fonts/DejaVuSans-Bold.ttf";
    private static int MONEY_FONT_SIZE = 42;
    private static int PRICE_FONT_SIZE = 18;
    private static int KEYBIND_FONT_SIZE = 18;
    private static int FISRT_ITEM_OFFSET = 64;
    private static int GAP_BETWEEN_ITEMS = 120;
    private static int ITEM_ABOVE_CENTER = 10;
    private static int MONEY_LEFT_OFFSET = 200;
    private static int MONEY_UP_OFFSET = 65;

    private static int TANK_COST = 250;
    private static int SUPERTANK_COST = 600;
    private static int AIRPLANE_COST = 500;
    private static final String TANK_IMAGE_FILENAME = "res/images/tank.png";
    private static final String SUPERTANK_IMAGE_FILENAME = "res/images/supertank.png";
    private static final String AIRPLANE_IMAGE_FILENAME = "res/images/airsupport.png";


    private static Font moneyFont;
    private static Font priceFont;
    private static Font keyBindFont;
    private static Image image;
    private static Image tank;
    private static Image supertank;
    private static Image airplane;

    private static BuyPanel buyPanel = null;

    private BuyPanel() {
        moneyFont = new Font(FONT_FILENAME, MONEY_FONT_SIZE);
        priceFont = new Font(FONT_FILENAME, PRICE_FONT_SIZE);
        keyBindFont = new Font(FONT_FILENAME, KEYBIND_FONT_SIZE);
        image = new Image(IMAGE_FILENAME);
        tank = new Image(TANK_IMAGE_FILENAME);
        supertank = new Image(SUPERTANK_IMAGE_FILENAME);
        airplane = new Image(AIRPLANE_IMAGE_FILENAME);
    }

    /**
     *
     * @return the buy panel
     */
    public static BuyPanel getBuyPanel() {
        if (buyPanel == null) {
            buyPanel = new BuyPanel();
        }
        return buyPanel;
    }

    /**
     * draw the buy panel
     */
    public void update() {
        // draw panel background
        image.drawFromTopLeft(0, 0);

        // draw item images
        double itemHeight = image.getHeight() / 2 - ITEM_ABOVE_CENTER;
        tank.draw(FISRT_ITEM_OFFSET, itemHeight);
        supertank.draw(FISRT_ITEM_OFFSET + GAP_BETWEEN_ITEMS, itemHeight);
        airplane.draw(FISRT_ITEM_OFFSET + 2 * GAP_BETWEEN_ITEMS, itemHeight);

        // draw money
        moneyFont.drawString("$" + Integer.toString(GameStatus.getMoney()),
                Window.getWidth() - MONEY_LEFT_OFFSET, MONEY_UP_OFFSET);

        // draw binds
        double keyBindHeight = image.getHeight()/6;
        keyBindFont.drawString("Key binds:", image.getWidth()/2, keyBindHeight);
        keyBindFont.drawString("S - Start Wave", image.getWidth()/2, keyBindHeight*3);
        keyBindFont.drawString("L - Increase Timescale", image.getWidth()/2, keyBindHeight*4);
        keyBindFont.drawString("K - Decrease Timescale", image.getWidth()/2, keyBindHeight*5);

        // draw price
        double fontHeight = image.getHeight() - ITEM_ABOVE_CENTER;
        double firstPricePosition = FISRT_ITEM_OFFSET - tank.getWidth() / 3;
        Colour tankColour = GameStatus.getMoney() >= TANK_COST ? Colour.GREEN : Colour.RED;
        Colour supertankColour = GameStatus.getMoney() >= SUPERTANK_COST ? Colour.GREEN : Colour.RED;
        Colour airplaneColour = GameStatus.getMoney() >= AIRPLANE_COST ? Colour.GREEN : Colour.RED;
        priceFont.drawString("$" + Integer.toString(TANK_COST), firstPricePosition,
                fontHeight, new DrawOptions().setBlendColour(tankColour));
        priceFont.drawString("$" + Integer.toString(SUPERTANK_COST), firstPricePosition + GAP_BETWEEN_ITEMS,
                fontHeight, new DrawOptions().setBlendColour(supertankColour));
        priceFont.drawString("$" + Integer.toString(AIRPLANE_COST),
                firstPricePosition + 2 * GAP_BETWEEN_ITEMS,
                fontHeight, new DrawOptions().setBlendColour(airplaneColour));
    }

    /**
     * get the bounding box of the tank in the panel
     * @return the bounding box of the tank in the panel
     */
    public static Rectangle getTankBoundingBox() {
        Point point = new Point(FISRT_ITEM_OFFSET, image.getHeight() / 2 - ITEM_ABOVE_CENTER);
        return tank.getBoundingBoxAt(point);
    }

    /**
     * get the bounding box of the super tank in the panel
     * @return the bounding box of the super tank in the panel
     */
    public static Rectangle getSuperTankBoundingBox() {
        Point point = new Point(FISRT_ITEM_OFFSET + GAP_BETWEEN_ITEMS,
                image.getHeight() / 2 - ITEM_ABOVE_CENTER);
        return supertank.getBoundingBoxAt(point);
    }

    /**
     * get the bounding box of the airplane in the panel
     * @return the bounding box of the airplane in the panel
     */
    public static Rectangle getAirplaneBoundingBox() {
        Point point = new Point(FISRT_ITEM_OFFSET + 2 * GAP_BETWEEN_ITEMS,
                image.getHeight() / 2 - ITEM_ABOVE_CENTER);
        return airplane.getBoundingBoxAt(point);
    }

    /**
     * get the bounding box of the bu panel
     * @return the bounding box of the nuy panel
     */
    public static Rectangle getBuyPanelBoundingBox() {
        return image.getBoundingBox();
    }
}
