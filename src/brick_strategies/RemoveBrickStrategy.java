package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * the regular default option strategy when there is a collision with a brick
 */
public class RemoveBrickStrategy implements CollisionStrategy {

    private GameObjectCollection gameObjectCollection;

    /**
     * constructor to the strategy
     * @param gameObjectCollection object of the game
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection)
    {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * a methode that helps to get the game object
     * @return game object
     */
    public GameObjectCollection getGameObjectCollection()
    {
        return this.gameObjectCollection;
    }

    /**
     * a methode that runs when there is a collision
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }

}
