package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * a class that runs multiple number of pucks where breaking a brick
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{
    private static final float BALL_SPEED = 50;
    private static final int BALL_NUMBER = 3;
    private GameObjectCollection gameObjectCollection;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * constructor to the strategy
     * @param toBeDecorated collision strategy that is being sent to the decorator
     * @param imageReader object to read an image
     * @param soundReader object to read sound files
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.gameObjectCollection = toBeDecorated.getGameObjectCollection();
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * a methode that runs when there is a collision
     * @param thisObj the object that were collied
     * @param otherObj the object that caused the collision
     * @param counter collisions counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable ballImage = imageReader.readImage("assets/mockBall.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        for (int i = 0; i < BALL_NUMBER; i++)
        {
            Puck ball = new Puck(thisObj.getCenter(), new Vector2((thisObj.getDimensions().x()) / 3,
                    (thisObj.getDimensions().x()) / 3),ballImage, collisionSound);
            if (rand.nextBoolean())
                ballVelX *= -1;
                ballVelX += (i + 10);
            if (rand.nextBoolean())
                ballVelY *= -1;
                ballVelY += (i + 10);
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
            gameObjectCollection.addGameObject(ball);
        }
    }
}
