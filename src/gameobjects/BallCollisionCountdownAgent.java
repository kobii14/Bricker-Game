package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * a game object that counts the number of collisions when asked to
 */
public class BallCollisionCountdownAgent extends GameObject {

    private Ball ball;
    private ChangeCameraStrategy owner;
    private int countDownValue;
    private int ball_counter;
    private static final int WINDOW_DIM = 500;

    /**
     * constructor to the agent
     * @param ball the ball to follow after
     * @param owner the strategy that owns the agent
     * @param countDownValue int for the asked times of collisions
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue)
    {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.countDownValue = countDownValue;
        this.owner = owner;
        this.ball_counter = ball.getCollisionCount();
    }

    /**
     * updates the collisions during the game
     * @param deltaTime the current time
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.ball.getCollisionCount() - this.ball_counter >= this.countDownValue + 1 ||
                this.ball.getCenter().y() > WINDOW_DIM)
        {
//            System.out.println("ball collisions " + (this.ball.getCollisionCount() - this.ball_counter));
            this.owner.turnOffCameraChange();
            this.owner.getGameObjectCollection().removeGameObject(this);

        }
    }
}
