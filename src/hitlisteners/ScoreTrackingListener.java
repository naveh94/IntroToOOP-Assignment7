package hitlisteners;

import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Collidable;
import gameobjects.Counter;

/**
 * Score Tracker.
 * @author Naveh Marchoom
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * Create a new Score Tracking Listener.
     * @param scoreCounter Counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Collidable beingHit, Ball hitter) {
        if (((Block) beingHit).getHitpoints() == 0) {
            currentScore.increase(100);
        }
    }
}
