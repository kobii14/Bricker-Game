package src.gameobjects;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import danogl.GameObject;
import danogl.collisions.Collision;

/**
 * a class that creates and counts the game bricks
 */
public class Brick extends GameObject {
    private CollisionStrategy collisionStrategy;
    private Counter counter;
    private boolean broken;

    /**
     * constructor to build the bricks
     * @param topLeftCorner location of a brick
     * @param dimensions dimension of the brick
     * @param renderable the renderer object
     * @param collisionStrategy on objects that decides what to do in a collision
     * @param counter counts the bricks
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter)
    {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
        this.broken = false;
    }

    /**
     * function that determine what to do when there is a collision
     * @param other the game object
     * @param collision object that represents the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!this.broken)
        {
            collisionStrategy.onCollision(this, other, counter);
            this.broken = true;
        }
        this.broken = true;
    }
}
