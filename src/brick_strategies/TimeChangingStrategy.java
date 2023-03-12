package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.ClockObject;

/**
 * a class of time changing strategy
 */
public class TimeChangingStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy {
    private ImageReader imageReader;
    private static final int CLOCK_SIZE = 40;
    private static final int CLOCK_SPEED = 80;
    private static final float HIGH_SPEED = 1.1f;
    private static final float LOW_SPEED = 0.9f;


    private WindowController windowController;

    /**
     * a constructor of the strategy
     * @param toBeDecorated collision strategy that is being sent to the decorator
     * @param imageReader object to read an image
     * @param windowController object that has a control on the game
     */
    public TimeChangingStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                                WindowController windowController) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowController = windowController;
    }

    /**
     * a methode that runs when there is a collision with a brick of this strategy
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable quickClock = imageReader.readImage("assets/quicken.png", true);
        Renderable slowClock = imageReader.readImage("assets/slow.png", true);
        ClockObject clockObject = null;
        // 0 for quick, 1 for slow
        if (windowController.getTimeScale() != HIGH_SPEED)
        {
            clockObject = new ClockObject(thisObj.getCenter(), new Vector2(CLOCK_SIZE, CLOCK_SIZE),
                    quickClock, 0, windowController, getGameObjectCollection());
        }
        else if (windowController.getTimeScale() != LOW_SPEED)
        {
            clockObject = new ClockObject(thisObj.getCenter(), new Vector2(CLOCK_SIZE, CLOCK_SIZE),
                    slowClock, 1, windowController, getGameObjectCollection());
        }
        if (clockObject != null)
        {
            clockObject.setVelocity(new Vector2(0, CLOCK_SPEED));
        }
        getGameObjectCollection().addGameObject(clockObject);
    }
}
