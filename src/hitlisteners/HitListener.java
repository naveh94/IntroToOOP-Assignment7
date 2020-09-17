package hitlisteners;

import gameobjects.Ball;
import gameobjects.Collidable;

/**
 * HitListener Interface.
 * @author Naveh Marchoom
 */
public interface HitListener {
   /**
    * This method is called whenever the beingHit object is hit.
    * The hitter parameter is the Ball that's doing the hitting.
    * @param beingHit The block that being hit.
    * @param hitter The ball that hit the block.
    */
   void hitEvent(Collidable beingHit, Ball hitter);
}