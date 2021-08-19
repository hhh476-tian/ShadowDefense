import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * a singleton class regulates the creation and updates of  slicers
 */
public class SlicerManager extends SpriteManager {
    private List<Slicer> slicers;

    private static SlicerManager SlicerManager = null;
    private SlicerManager() {
        slicers = new ArrayList<>();
    }

    /**
     *
     * @return the  slicer manager
     */
    public static SlicerManager getManager() {
        if (SlicerManager == null) {
            SlicerManager = new SlicerManager();
        }
        return SlicerManager;
    }

    /**
     * create a  slicer at a given point
     * @param point the starting point of the slicer
     */
    @Override
    public void createSprite(Point point, String SpriteName) {
        if (SpriteName.equals(RegularSlicer.SLICER_TYPE)) {
            slicers.add(new RegularSlicer(GameStatus.getCurrentLevel().getPolyline(), point));
        } else if (SpriteName.equals(SuperSlicer.SLICER_TYPE)) {
            slicers.add(new SuperSlicer(GameStatus.getCurrentLevel().getPolyline(), point));
        } else if (SpriteName.equals(MegaSlicer.SLICER_TYPE)) {
            slicers.add(new MegaSlicer(GameStatus.getCurrentLevel().getPolyline(), point));
        } else if (SpriteName.equals(ApexSlicer.SLICER_TYPE)) {
            slicers.add(new ApexSlicer(GameStatus.getCurrentLevel().getPolyline(), point));
        }
    }

    /**
     * update each  slicer
     * @param input
     */
    @Override
    public void update(Input input) {
        List<Slicer> childrenSpawned = new ArrayList<>();
        for (Slicer slicer : slicers) {
            slicer.update(input);
            if (slicer.isDead()) {
                if (slicer.spawnChildren() != null) {
                    childrenSpawned.addAll(slicer.spawnChildren());
                }
                GameStatus.increaseMoney(slicer.getReward());
            } else if (slicer.isFinished()) {
                GameStatus.decreaseLives(slicer.getPenalty());
            }
        }
        slicers = slicers.stream().filter(p -> !p.isFinished() && !p.isDead()).collect(Collectors.toList());
        slicers.addAll(childrenSpawned);
    }

    /**
     * remove a slicer from the game
     * @param slicer the slicer to be removed
     */
    public void removeSlicer(Slicer slicer) {
        slicers.remove(slicer);
    }

    /**
     * determine whether there is any slicer left in the game
     * @return true if no slicers remaining, false otherwise
     */
    public boolean isAnySlicerRemaining() {
        return slicers.isEmpty();
    }

    public static List<Slicer> getSlicers() {
        return getManager().slicers;
    }
}

