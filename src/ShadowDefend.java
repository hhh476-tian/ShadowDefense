import bagel.AbstractGame;
import bagel.Input;
import bagel.Window;


/**
 * ShadowDefend, a tower defence game.
 * This class is adapted from the provided solution
 * from https://gitlab.eng.unimelb.edu.au/swen20003-S1-2020/rohylj/rohylj-project-1/tree/master
 */
public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;

    // Change to suit system specifications. This could be
    // dynamically determined but that is out of scope.
    public static final double FPS = 60;

    private final StatusPanel statusPanel = StatusPanel.getStatusPanel();
    private final BuyPanel buyPanel = BuyPanel.getBuyPanel();

    private final SlicerManager slicerManager = SlicerManager.getManager();
    private final ActiveTowerManager activeTowerManager = ActiveTowerManager.getManager();
    private final AirplaneManager airplaneManager = AirplaneManager.getManager();

    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }


    /**
     * Update the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {

        // Draw map from the top left of the window
        GameStatus.getCurrentLevel().getMap().draw(0, 0, 0, 0, WIDTH, HEIGHT);

        // update the game status
        GameStatus.update(input);

        // update the game objects
        GameStatus.getCurrentLevel().update();
        slicerManager.update(input);
        activeTowerManager.update(input);
        airplaneManager.update(input);

        // Draw the panels
        statusPanel.update();
        buyPanel.update();

        // if no lives remaining, game is lost and will exit
        if (GameStatus.getLives() <= 0) {
            Window.close();
        }
    }

}