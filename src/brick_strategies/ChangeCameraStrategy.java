package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * a strategy that changes the camera focus to the main ball
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{

    private static final int NUM_OF_COLLISIONS = 4;
    private GameManager gameManager;
    private WindowController windowController;

    /**
     * constructor to the strategy
     * @param toBeDecorated collision strategy that is being sent to the decorator
     * @param windowController object that has a control on the game
     * @param gameManager initializes a game and controls it
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                 WindowController windowController,
                                 BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * a methode to shut down the camera focus
     */
    public void turnOffCameraChange()
    {
        this.gameManager.setCamera(null);
    }

    /**
     * a methode that runs when the brick is being collied
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        if (gameManager.getCamera() == null && !(otherObj instanceof Puck))
        {
            gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
            BallCollisionCountdownAgent ballCollisionCountdownAgent =
                    new BallCollisionCountdownAgent((Ball) otherObj, this, NUM_OF_COLLISIONS);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);
        }
    }
}
