package hitlisteners;

import java.util.Set;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Collidable;
import gameobjects.Counter;

/**
 * Remove Balls when reach the death zone.
 * @author Naveh Marchoom
 *
 */
public class BallRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBalls;
    private Set<Ball> balls = null;

    /**
     * Create a BallRemover object using given parameters.
     * @param level GameLevel
     * @param removedBalls Counter
     */
    public BallRemover(GameLevel level, Counter removedBalls) {
        this.game = level;
        this.remainingBalls = removedBalls;
    }

    /**
     * Creates a new BallRemover object using given parameters + a set of balls.
     * @param level GameLevel
     * @param removedBalls Counter
     * @param shots Set<Ball>
     */
    public BallRemover(GameLevel level, Counter removedBalls, Set<Ball> shots) {
        this(level, removedBalls);
        this.balls = shots;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        this.remainingBalls.decrease(1);
        if (this.balls != null) {
            this.balls.remove(hitter);
        }
    }
}
