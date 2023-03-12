package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.gameobjects.*;

import java.awt.*;
import java.util.Random;

/**
 * this class is responsible for managing the game
 */
public class BrickerGameManager extends GameManager
{
    public static final int BORDER_WIDTH = 20;
    private static final int ROW_NUM = 5;
    private static final int COL_NUM = 8;
    private static final int BRICK_BOARDER_GAP = 10;
    private static final int BRICK_WIDTH = 15;
    private static final int BRICKS_GAP = 10;
    private static final int NUM_OF_TURNS = 4;
    private static final int WIDGET_WIDTH = 30;
    private static final int PADDLE_DISTANCE_FROM_FLOOR = 30;
    private static final int BALL_SIZE = 30;
    private static final String WIN_MSG = "YOU WIN, PLAY AGAIN? ";
    private static final String LOSE_MSG = "YOU LOSE ";
    private static final String PLAY_AGAIN_MSG = " PLAY AGAIN? ";
    public void repositionBall(GameObject ball){}
    private Counter counter1;
    private Counter brickCounter;
    private static final float BALL_SPEED = 90;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 25;
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private BrickStrategyFactory brickStrategyFactory;
    /**
     * the constructor that builds a game
     * @param windowTitle string of the game title
     * @param windowDimensions the dimension of the game
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions)
    {
        super(windowTitle, windowDimensions);
    }

    /**
     * the main function to initialize the game
     * @param imageReader an object to assist reading images
     * @param soundReader an object to assist reading sound files
     * @param inputListener an object that will notice the user's moves
     * @param windowController an object that controls the window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        brickCounter = new Counter(0);

        //creating the ball
        createBall(imageReader, soundReader, windowController);


        //create a paddles
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                windowDimensions, MIN_DISTANCE_FROM_SCREEN_EDGE);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y()
                - PADDLE_DISTANCE_FROM_FLOOR));
        gameObjects().addGameObject(paddle);


        //create borders
        createBorders(windowDimensions);

        // creating the background
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", true));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //create the Bricks
        float brick_length = (windowDimensions.x() - (2 * BORDER_WIDTH) - (2 * BRICK_BOARDER_GAP)
                - ((COL_NUM - 1) * BRICKS_GAP))/ COL_NUM;
        GameObject brick = null;
        this.brickStrategyFactory = new BrickStrategyFactory(gameObjects(), this, imageReader,
                soundReader, inputListener, windowController, windowDimensions);
        for (int i = 0; i < COL_NUM; i++)
        {
            for (int j = 0; j < ROW_NUM; j++)
            {
                brick = new Brick(new Vector2(BORDER_WIDTH + BRICK_BOARDER_GAP + i *
                        (brick_length + BRICKS_GAP),
                        BORDER_WIDTH + BRICK_BOARDER_GAP + j * (BRICKS_GAP + BRICK_WIDTH)),
                        new Vector2(brick_length, BRICK_WIDTH),
                        imageReader.readImage("assets/brick.png", false),
                        brickStrategyFactory.getStrategy() ,brickCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                this.brickCounter.increment();
            }
        }

        //creates the numerical and graphical
        this.counter1 = new Counter(NUM_OF_TURNS);
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        GameObject numericLifeCounter = new NumericLifeCounter(counter1, new Vector2(windowDimensions.x()
                - BORDER_WIDTH - BRICKS_GAP - WIDGET_WIDTH , windowDimensions.y()
                - BORDER_WIDTH - BRICKS_GAP - WIDGET_WIDTH),
                new Vector2(WIDGET_WIDTH, WIDGET_WIDTH) , gameObjects());
        GameObject graphicLifeCounter = new GraphicLifeCounter(new Vector2(BORDER_WIDTH + BRICK_WIDTH,
                windowDimensions.y() - BORDER_WIDTH - BRICKS_GAP - WIDGET_WIDTH),
                new Vector2(WIDGET_WIDTH, WIDGET_WIDTH), counter1, heartImage, gameObjects(), NUM_OF_TURNS);
        gameObjects().addGameObject(numericLifeCounter, Layer.FOREGROUND);
        gameObjects().addGameObject(graphicLifeCounter, Layer.FOREGROUND);
    }

    /**
     * an inner function that helps to create the playing ball
     * @param soundReader an object to assist reading sound files
     * @param imageReader an object to assist reading images
     * @param windowController an object that controls the window
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader,
                            WindowController windowController) {

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        this.ball = ball;
    }

    /**
     * a function that overrides update function and makes sure to update all of the game's objects
     * @param deltaTime float of the time of change
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (brickCounter.value() < 1)
        {
            if (this.windowController.openYesNoDialog(WIN_MSG))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
        if (ballHeight > windowDimensions.y())
        {
            float ballVelX = BALL_SPEED;
            float ballVelY = BALL_SPEED;
            Random rand = new Random();
            if (rand.nextBoolean())
                ballVelX *= -1;
            if (rand.nextBoolean())
                ballVelY *= -1;
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
            ball.setCenter(windowDimensions.mult(0.5f));
            counter1.decrement();
//            System.out.println("counter " + counter1.value());
            prompt = LOSE_MSG;
            if (!prompt.isEmpty() && this.counter1.value() == 0)
            {
                prompt += PLAY_AGAIN_MSG;
                if (this.windowController.openYesNoDialog(prompt))
                    windowController.resetGame();
                else
                    windowController.closeWindow();
            }
        }
    }
    /**
     * a function that creates the game boarder
     * @param windowDimensions dimension of the window that the game runs on
     */
    private void createBorders(Vector2 windowDimensions){
        //left boarder
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()), BORDER_RENDERABLE));
        //right boarder
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()), BORDER_RENDERABLE));
        //upper boarder
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH), BORDER_RENDERABLE));
    }

    /**
     * main function to run a game
     * @param args no args
     */
    public static void main (String[] args)
    {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}

