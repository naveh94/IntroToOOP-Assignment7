package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * Collidable interface.
 * @author Naveh Marchoom
 */
public interface Collidable {

   /**
    * Get the collision rectangle of the collidable object.
    * @return rectangle.
    */
    Rectangle getCollisionRectangle();

   /**
    * Notify the object it got hit, and get a new velocity for the ball movement.
    * @param collisionPoint The point of the collision.
    * @param currentVelocity The collision velocity.
    * @param hitter Ball
    * @return a new velocity for the ball.
    */
    Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter);
    /**
     * Check if given Point p is inside the collidable collision rectangle.
     * @param p Point
     * @return boolean
     */
    Collidable isInBlock(Point p);
}