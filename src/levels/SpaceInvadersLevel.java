package levels;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import gameobjects.Block;
import gameobjects.Sprite;
import geometry.Velocity;
import readers.Loader;
import sprites.ImageSprite;

/**
 * A LevelInformation containing the information of a spaceInvaders level.
 * @author Naveh Marchoom
 *
 */
public class SpaceInvadersLevel implements LevelInformation {

    private Image bg;
    private List<Block> aliens;
    private List<Velocity> balls;

    /**
     * Creats a new SpaceInvadersLevel.
     */
    public SpaceInvadersLevel() {
        this.bg = Loader.loadImage("background_images/space.jpg");
        this.aliens = new ArrayList<>();
        this.balls = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfBalls() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return balls;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int paddleSpeed() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int paddleWidth() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String levelName() {
        return "Space Invaders";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sprite getBackground() {
        Sprite s = new ImageSprite(0, 0, this.bg);
        return s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Block> blocks() {
        return this.aliens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 50;
    }
}
