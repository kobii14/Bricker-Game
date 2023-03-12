package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * a class that represents a clock object to speed up \ slow down the speed of the game
 */
public class ClockObject extends GameObject {

    private Vector2 dimensions;
    private Vector2 topLeftCorner;
    private Renderable renderable;
    //0 for quick, 1 for slow
    private int quickOrSlow;
    private WindowController windowController;
    private GameObjectCollection gameObjectCollection;
    private static final float HIGH_SPEED = 1.1f;
    private static final float LOW_SPEED = 0.9f;

    /**
     * a constructor to the clock object
     * @param topLeftCorner the location of the clock
     * @param dimensions dimensions of the clock object
     * @param renderable the image of a clock
     * @param quickOrSlow 0 for speeding clock, 1 for slowing clock
     * @param windowController object that has a control on the game
     * @param gameObjectCollection object of the game
     */
    public ClockObject(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       int quickOrSlow, WindowController windowController,
                       GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.topLeftCorner = topLeftCorner;
        this.quickOrSlow = quickOrSlow;
        this.windowController = windowController;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * a methode that determines what to do when there is a collision with the clock object
     * @param other the object that collied with
     * @param collision information about the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.quickOrSlow == 0)
        {
            windowController.setTimeScale(HIGH_SPEED);
        }
        else if (this.quickOrSlow == 1)
        {
            windowController.setTimeScale(LOW_SPEED);
        }
        gameObjectCollection.removeGameObject(this);
    }

    /**
     * a methode that determines which objects the clock can be collied with
     * @param other a game object to allow collision with
     * @return true if possible, false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }
}
