import bagel.Input;
import bagel.util.Point;

/**
 * regulate the creation and updates of game objects
 */
public abstract class SpriteManager {

    public abstract void createSprite(Point point, String SpriteName);
    public abstract void update(Input input);
}
