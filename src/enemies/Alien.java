package enemies;

import java.awt.Color;
import java.awt.Image;

import blockcreator.DrawColor;
import blockcreator.DrawImage;
import gameobjects.Ball;
import gameobjects.Block;
import geometry.Point;
import geometry.Velocity;
import readers.Loader;
/**
 * A special block extending block that has access to his movement method.
 * @author Naveh Marchoom
 *
 */
public class Alien extends Block {

    private Image alienIcon;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 30;

    /**
     * Creates a new alien with static size and given top-left corner point.
     * @param topLeftCorner Point
     */
    public Alien(Point topLeftCorner) {
        super(topLeftCorner, WIDTH, HEIGHT);
        this.alienIcon = Loader.loadImage("block_images/enemy.gif");
        this.setHitPoints(1);
        if (this.alienIcon != null) {
            super.setFill(new DrawImage(this.alienIcon));
        } else {
            super.setFill(new DrawColor(Color.green));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveOneStep(Velocity v) {
        super.moveOneStep(v);
    }

    /**
     * Shoots a new ball directly below the alien.
     * @return Ball
     */
    public Ball shoot() {
        Point p = new Point(super.getCollisionRectangle().getLowerBorder().middle().getX(),
                super.getCollisionRectangle().getLowerBorder().middle().getY() + 2);
        Ball b = new Ball(p, 4, Color.RED, true);
        b.setFill(Color.ORANGE);
        b.setVelocity(new Velocity(0, 100));
        return b;
    }

    /**
     * Get the highest y parameter of the alien.
     * @return dobule
     */
    public double getTopY() {
        return this.getCollisionRectangle().getUpperLeft().getY();
    }

    /**
     * Get the lowest y parameter of the alien.
     * @return double
     */
    public double getBottomY() {
        return this.getCollisionRectangle().getLowerBorder().middle().getY();
    }

    /**
     * Get the rightmost x parameter of the alien.
     * @return double
     */
    public double getRightX() {
        return this.getCollisionRectangle().getRightBorder().middle().getX();
    }

    /**
     * Get the leftmost x parameter of the alien.
     * @return double
     */
    public double getLeftX() {
        return this.getCollisionRectangle().getUpperLeft().getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Block isInBlock(Point p) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        if (!hitter.isEnemy()) {
            return super.hit(collisionPoint, currentVelocity, hitter);
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }
}
