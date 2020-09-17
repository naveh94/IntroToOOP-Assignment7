package hitlisteners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Collidable;
import gameobjects.Counter;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author Naveh Marchoom
 *
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Create a new BlockRemover object using given parameters.
     * @param level GameLevel
     * @param removedBlocks Counter
     */
    public BlockRemover(GameLevel level, Counter removedBlocks) {
        this.game = level;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        if (((Block) beingHit).getHitpoints() == 0) {
            ((Block) beingHit).removeFromGame(game);
            this.remainingBlocks.decrease(1);
        }
    }

}
