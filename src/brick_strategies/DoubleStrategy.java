package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * a class of double methode strategy
 */
public class DoubleStrategy implements CollisionStrategy{
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;
    private CollisionStrategy strategy3;

    /**
     * constructor to the double strategy
     * @param strategy1 a collision strategy
     * @param strategy2 a collision strategy
     * @param strategy3 a collision strategy
     */
    public DoubleStrategy(CollisionStrategy strategy1, CollisionStrategy strategy2,
                          CollisionStrategy strategy3) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
        this.strategy3 = strategy3;
    }

    /**
     * a methode that helps to get the game object
     * @return game object
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return null;
    }

    /**
     * a methode that runs when there is a collision - runs 2 or 3 strategies at a time
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (strategy1 != null && strategy2 != null && strategy3 != null)
        {
            counter.increment();
            counter.increment();
        }
        else
        {
            counter.increment();
        }

        if (strategy1 != null)
        {
            strategy1.onCollision(thisObj, otherObj, counter);
        }
        if (strategy2 != null)
        {
            strategy2.onCollision(thisObj, otherObj, counter);
        }
        if (strategy3 != null)
        {
            strategy3.onCollision(thisObj, otherObj, counter);
        }
    }
}
