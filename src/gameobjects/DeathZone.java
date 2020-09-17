package gameobjects;

import geometry.Point;
import geometry.Velocity;

/**
 * DeathZone for the ball.
 * @author Naveh Marchoom
 *
 */
public class DeathZone extends Block implements Collidable, Sprite, HitNotifier {

    /**
     * Create a new Deathzone area with given parameters.
     * @param topLeftCorner Point
     * @param width double
     * @param height double
     */
    public DeathZone(Point topLeftCorner, double width, double height) {
        super(topLeftCorner, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        super.notifyHit(hitter);
        return currentVelocity;
    }
}
