package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * a class that creates graphic hearts to represent the lives
 */
public class GraphicLifeCounter extends GameObject {

    private static final int HEARTS_GAP = 10;
    private Counter counter;
    private GameObject heartArr [];
    private GameObjectCollection gameObjectCollection;
    private int numHearts;

    /**
     * constructor that builds graphic lives
     * @param widgetTopLeftCorner widget location
     * @param widgetDimensions widget dimensions
     * @param livesCounter lives that left
     * @param widgetRenderable render object
     * @param gameObjectsCollection the game object
     * @param numOfLives number of lives
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                              Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                              int numOfLives)
    {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.counter = livesCounter;
        this.heartArr = new GameObject[numOfLives];
        for (int i = 0; i < numOfLives; i++)
        {
            GameObject heart = new GameObject(new Vector2(widgetTopLeftCorner.x() + i *
                    (widgetDimensions.y() + HEARTS_GAP),
                    widgetTopLeftCorner.y()), widgetDimensions, widgetRenderable);
            heartArr[i] = heart;
            gameObjectsCollection.addGameObject(heart, Layer.FOREGROUND);
        }
        this.gameObjectCollection = gameObjectsCollection;
        this.numHearts = numOfLives;

    }

    /**
     * updates the lives during the game
     * @param deltaTime time
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (counter.value() != this.numHearts)
        {

            if (this.heartArr[counter.value()] != null)
            {
                gameObjectCollection.removeGameObject(heartArr[counter.value()], Layer.FOREGROUND);
                this.numHearts = counter.value();
            }

        }
    }
}
