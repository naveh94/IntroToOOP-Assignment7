package hitlisteners;

import gameobjects.Ball;
import gameobjects.Collidable;
import gameobjects.Counter;
import gameobjects.StopCondition;
/**
 * A HitListener for checking the paddle's hits.
 * @author Naveh Marchoom
 *
 */
public class PaddleHealthListener implements HitListener {

    private Counter lives;
    private StopCondition stopCondition;

    /**
     * Creats a new PaddleHealthListener with given lives counter and given StopCondition.
     * @param l Counter
     * @param stop StopCondition
     */
    public PaddleHealthListener(Counter l, StopCondition stop) {
        this.lives = l;
        this.stopCondition = stop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        this.lives.decrease(1);
        this.stopCondition.setTrue();
    }

}
