package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * a decorator that helps to add a default behavior to the strategies
 */
public class RemoveBrickStrategyDecorator implements CollisionStrategy{
    private CollisionStrategy collisionStrategy;

    /**
     * a constructor of the decorator
     * @param toBeDecorated a collision strategy
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated)
    {
        this.collisionStrategy = toBeDecorated;
    }
    /**
     * a methode that helps to get the game object
     * @return game object
     */
    public GameObjectCollection getGameObjectCollection()
    {
        return collisionStrategy.getGameObjectCollection();
    }
    /**
     * a methode that runs when there is a collision
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        collisionStrategy.getGameObjectCollection().removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }
}
