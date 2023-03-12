package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * a class of adding a paddle strategy
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy {
    private GameObjectCollection gameObjectCollection;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 30;
    private static final int PADDLE_DISTANCE_FROM_FLOOR = 90;
    private static final int NUM_COLLISIONS_TO_DISAPPEAR = 3;

    /**
     * constructor to the class
     * @param toBeDecorated collision strategy that is being sent to the decorator
     * @param imageReader object to read an image
     * @param inputListener object to read the user inputs keys
     * @param windowDimensions Vector of the game dimensions
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions) {
        super(toBeDecorated);
        this.gameObjectCollection = toBeDecorated.getGameObjectCollection();
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * a methode that runs when the paddle is being collied
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter)
    {
        super.onCollision(thisObj, otherObj, counter);
        Renderable paddleImage = this.imageReader.readImage("assets/paddle.png",
                true);
        MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(100, 15),
                paddleImage, this.inputListener,
                this.windowDimensions, this.gameObjectCollection, MIN_DISTANCE_FROM_SCREEN_EDGE,
                NUM_COLLISIONS_TO_DISAPPEAR);
        mockPaddle.setCenter(new Vector2(this.windowDimensions.x() / 2,
                (int) this.windowDimensions.y() - PADDLE_DISTANCE_FROM_FLOOR));
        if (!MockPaddle.isInstantiated)
        {
            this.gameObjectCollection.addGameObject(mockPaddle);
            MockPaddle.isInstantiated = true;
        }
    }
}
