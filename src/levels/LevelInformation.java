package levels;
import java.util.List;

import gameobjects.Block;
import gameobjects.Sprite;
import geometry.Velocity;
/**
 * Level Information Interface.
 * @author Naveh Marchoom
 *
 */
public interface LevelInformation {

    /**
     * Return the number of balls in the start of the game.
     * @return int
     */
    int numberOfBalls();

    /**
     * Return a list of the initial velocity of each ball.
     * @return List
     */
    List<Velocity> initialBallVelocities();

    /**
     * Return the speed of the paddle.
     * @return int
     */
    int paddleSpeed();

    /**
     * Return the width of the paddle.
     * @return int
     */
    int paddleWidth();

    /**
     * Return the name of the level.
     * @return String
     */
    String levelName();

    /**
     * Return the background of the level.
     * @return Sprite
     */
    Sprite getBackground();

    /**
     * Return a list of the blocks in the level.
     * @return List
     */
       List<Block> blocks();

       /**
        * Return the amount of blocks should be destroyed in order to finish the level.
        * @return int
        */
       int numberOfBlocksToRemove();
}
