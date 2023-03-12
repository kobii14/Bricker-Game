package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * an interface that contains methods to the collision strategy
 */

public interface CollisionStrategy {
    /**
     * a methode that helps to get the game object
     * @return game object
     */
    public GameObjectCollection getGameObjectCollection();

    /**
     * a methode that runs when there is a collision
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}


