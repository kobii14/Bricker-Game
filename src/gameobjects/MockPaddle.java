package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * a class that represents a mock paddle
 */
public class MockPaddle extends Paddle{

    private int numCollisionsToDisappear;
    private int collisionsCounter;
    public static boolean isInstantiated = false;
    private GameObjectCollection gameObjectCollection;

    /**
     * a constructor of the mock paddle
     * @param topLeftCorner the paddle location
     * @param dimensions paddle dimensions
     * @param renderable image of the paddle
     * @param inputListener object to read the user inputs keys
     * @param windowDimensions Vector of the game dimensions
     * @param gameObjectCollection object of the game
     * @param minDistanceFromEdge num of minimal distance between the paddle and the borders
     * @param numCollisionsToDisappear a num that determines after how many collisions the paddle disappears
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions, GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.collisionsCounter = 0;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * what will the paddle do when it is being collied with
     * @param other the collision object
     * @param collision information about the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionsCounter ++;
//        System.out.println("paddle counter " + collisionsCounter);
        if (this.collisionsCounter >= this.numCollisionsToDisappear)
        {
            this.gameObjectCollection.removeGameObject(this);
            isInstantiated = false;
        }
    }
}
