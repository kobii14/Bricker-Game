package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.rendering.TextRenderable;

/**
 * a class that adds the number of lives left
 */
public class NumericLifeCounter extends GameObject {
    private Counter counter;
    private GameObject textRend;
    private TextRenderable textRenderable;

    /**
     * a constructor to build a numerical representation of the lives that left
     * @param livesCounter lives that left
     * @param topLeftCorner location of the number
     * @param dimensions dimensions of the number
     * @param gameObjectCollection the game object
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner,
                              Vector2 dimensions, GameObjectCollection gameObjectCollection)
    {
        super(topLeftCorner, dimensions, null);
        this.textRenderable = new TextRenderable(String.valueOf(livesCounter.value()));
        this.textRend = new GameObject(topLeftCorner, dimensions, textRenderable);
        this.counter = livesCounter;
        gameObjectCollection.addGameObject(textRend, Layer.FOREGROUND);
    }

    /**
     * updates the lives in the game
     * @param deltaTime the current time
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(!(counter.toString().equals(((textRenderable.toString())))))
        {
            textRenderable.setString(String.valueOf(counter.value()));
        }

    }
}