package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
/**
 * a class that creates a paddle object
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistanceFromEdge;

    /**
     * constructor of the paddle
     * @param topLeftCorner the paddle location
     * @param dimensions paddle dimensions
     * @param renderable image of the paddle
     * @param inputListener object to read the user inputs keys
     * @param windowDimensions Vector of the game dimensions
     * @param minDistanceFromEdge num of minimal distance between the paddle and the borders
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * updates the paddle by movements
     * @param deltaTime current time
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                getTopLeftCorner().x() > minDistanceFromEdge) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        //paddle's border
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                getTopLeftCorner().x() < windowDimensions.x() -
                        minDistanceFromEdge - getDimensions().x()) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
