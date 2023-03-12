package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import java.util.Random;
import src.BrickerGameManager;

/**
 * a factory of different kinds of strategies
 */
public class BrickStrategyFactory {
    GameObjectCollection gameObjectCollection;
    ImageReader imageReader;
    SoundReader soundReader;
    BrickerGameManager brickerGameManager;
    UserInputListener userInputListener;
    WindowController windowController;
    Vector2 windowDimensions;

    /**
     * a constructor to the strategies
     * @param gameObjectCollection object of the game
     * @param gameManager initializes a game and controls it
     * @param soundReader object to read sound files
     * @param windowController object that has a control on the game
     * @param imageReader object to read an image
     * @param inputListener object to read the user inputs keys
     * @param windowDimensions Vector of the game dimensions
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions)
    {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowDimensions;
        this.brickerGameManager = gameManager;
        this.userInputListener = inputListener;
        this.windowController = windowController;
    }

    /**
     * a methode that chooses a strategy in an equal probability
     * @return the chosen strategy
     */
    public CollisionStrategy getStrategy()
    {
        RemoveBrickStrategy removeBrickStrategy = new RemoveBrickStrategy(this.gameObjectCollection);
        PuckStrategy puckStrategy = new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
        AddPaddleStrategy addPaddleStrategy = new AddPaddleStrategy(removeBrickStrategy, imageReader,
                userInputListener, windowDimensions);
        ChangeCameraStrategy changeCameraStrategy = new ChangeCameraStrategy(removeBrickStrategy,
                windowController, brickerGameManager);
        TimeChangingStrategy timeChangingStrategy = new TimeChangingStrategy(removeBrickStrategy,
                imageReader, windowController);
        Random random = new Random();
        int randInt = random.nextInt(6);
        //        System.out.println("random is " + randInt);
        CollisionStrategy [] collisionArr = {removeBrickStrategy, puckStrategy, addPaddleStrategy,
                changeCameraStrategy, timeChangingStrategy};
        if (randInt < 5)
        {
            return collisionArr[randInt];
        }
        else // (randInt == 5)
        {
            int randInt2 = random.nextInt(6);
            int randInt3 = random.nextInt(6);
            if (randInt2 < 5 && randInt3 < 5)
            {
                return new DoubleStrategy(collisionArr[randInt2], collisionArr[randInt3], null);
            }
            else if (randInt2 < 5 && randInt3 == 5)
            {
                int randInt4 = random.nextInt(5);
                int randInt5 = random.nextInt(5);
                return new DoubleStrategy(collisionArr[randInt2], collisionArr[randInt4],
                        collisionArr[randInt5]);
            }
            else if (randInt2 == 5 && randInt3 < 5)
            {
                int randInt4 = random.nextInt(5);
                int randInt5 = random.nextInt(5);
                return new DoubleStrategy(collisionArr[randInt3], collisionArr[randInt4],
                        collisionArr[randInt5]);
            }
            else //if (randInt2 == 5 && randInt3 == 5)
            {
                int randInt4 = random.nextInt(5);
                int randInt5 = random.nextInt(5);
                return new DoubleStrategy(collisionArr[randInt4], collisionArr[randInt5], null);
            }
        }
    }
}
