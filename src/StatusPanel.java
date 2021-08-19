import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.Font;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * a singleton class represents the status panel
 */
public class StatusPanel {
    private static final String IMAGE_FILENAME = "res/images/statuspanel.png";
    private static final String FONT_FILENAME = "res/fonts/DejaVuSans-Bold.ttf";
    private static int FONT_SIZE = 18;

    private static final String STATUS_WIN = "Winner!";
    private static final String STATUS_PLACING = "Placing";
    private static final String STATUS_IN_PROGRESS = "Wave In Progress";
    private static final String STATUS_AWAITING_START = "Awaiting Start";

    private static final List<String> WORDS = Arrays.asList("Wave: ", "Time Scale: ", "Status: ", "Lives: ");
    private static Font font;
    private static Image image;

    private static StatusPanel statusPanel;

    private StatusPanel() {
        image = (new Image(IMAGE_FILENAME));
        font = new Font(FONT_FILENAME, FONT_SIZE);
    }

    /**
     * render the status panel
     */
    public void update() {
        image.drawFromTopLeft(0, Window.getHeight() - image.getHeight());
        double wordsHeight = Window.getHeight() - image.getHeight() / 3;

        // draw wave number
        font.drawString(WORDS.get(0), Window.getWidth() / WORDS.size() * 0, wordsHeight);
        font.drawString(Integer.toString(GameStatus.getWaveNumber()),
                Window.getWidth() / WORDS.size() * 0 + font.getWidth(WORDS.get(0)), wordsHeight);

        // draw timescale
        Colour timescaleColour =
                GameStatus.getTimescale() == GameStatus.INITIAL_TIMESCALE ? Colour.WHITE : Colour.GREEN;
        font.drawString(WORDS.get(1),
                Window.getWidth() / WORDS.size() * 1, wordsHeight,
                    new DrawOptions().setBlendColour(timescaleColour));
        font.drawString(Integer.toString(GameStatus.getTimescale()) + ".0",
                Window.getWidth() / WORDS.size() * 1 + font.getWidth(WORDS.get(1)),
                Window.getHeight() - image.getHeight() / 3, new DrawOptions().setBlendColour(timescaleColour));

        // draw status
        font.drawString(WORDS.get(2), Window.getWidth() / WORDS.size() * 2, wordsHeight);
        font.drawString(getStatus(),
                Window.getWidth() / WORDS.size() * 2 + font.getWidth(WORDS.get(2)),
                Window.getHeight() - image.getHeight() / 3);

        // draw lives
        font.drawString(WORDS.get(3),
                Window.getWidth()
                        - font.getWidth(WORDS.get(3))
                        - font.getWidth(Integer.toString(GameStatus.INITIAL_LIVES)),
                    wordsHeight);
        font.drawString(Integer.toString(GameStatus.getLives()),
                Window.getWidth() - font.getWidth(Integer.toString(GameStatus.INITIAL_LIVES)),
                Window.getHeight() - image.getHeight() / 3);
    }

    /**
     *
     * @return the status panel
     */
    public static StatusPanel getStatusPanel() {
        if (statusPanel == null) {
            statusPanel = new StatusPanel();
        }
        return statusPanel;
    }

    /**
     *
     * @return the status string shown on the panel
     */
    private String getStatus() {
        if (GameStatus.isWin()) {
            return STATUS_WIN;
        } else if (GameStatus.isPlacing()) {
            return STATUS_PLACING;
        } else if (GameStatus.isWaveStarted()) {
            return STATUS_IN_PROGRESS;
        } else {
            return STATUS_AWAITING_START;
        }
    }

    /**
     * the bounding box of the status panel
     * @return the bounding box of the status panel
     */
    public static Rectangle getStatusPanelBoundingBox() {
        Point point = new Point(Window.getWidth() / 2, Window.getHeight() - image.getHeight() / 2);
        return image.getBoundingBoxAt(point);
    }
}
