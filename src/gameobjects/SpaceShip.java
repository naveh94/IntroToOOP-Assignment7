package gameobjects;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import animations.GameLevel;
import animations.SpaceLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import blockcreator.DrawColor;
import blockcreator.DrawImage;
import blockcreator.Drawer;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import hitlisteners.HitListener;
import readers.Loader;
/**
 * Spaceship object the replace paddle.
 * @author Naveh Marchoom
 */
public class SpaceShip implements Collidable, Sprite, HitNotifier {

    private List<HitListener> hitListeners;
    private Point topLeftCorner;
    private final int width = 70;
    private final int height = 20;
    private final int speed = 440;
    private Drawer fill;
    private KeyboardSensor keyboard;
    private final int leftBorder = 0;
    private final int rightBorder = 800;

    /**
     * Creates a new spaceship with given KeyboardSensor and position point.
     * @param ks KeyboardSensor
     * @param topLeftCorner Point
     */
    public SpaceShip(KeyboardSensor ks, Point topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
        this.keyboard = ks;
        this.hitListeners = new ArrayList<>();
        Image img = Loader.loadImage("block_images/paddle.gif");
        if (img != null) {
            this.fill = new DrawImage(img);
        } else {
            this.fill = new DrawColor(Color.blue);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board) {
        this.fill.drawOn(board, getCollisionRectangle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }

    /**
     * Move the paddle right.
     * @param dt double
     */
    public void moveRight(double dt) {
        if (this.topLeftCorner.getX() + this.width < this.rightBorder - this.speed * dt) {
            Point p = new Point(this.topLeftCorner.getX() + (this.speed * dt), this.topLeftCorner.getY());
            this.topLeftCorner = p;
        } else {
            Point p = new Point(this.rightBorder - this.width + 1, this.topLeftCorner.getY());
            this.topLeftCorner = p;
        }
    }

    /**
     * Move the paddle left.
     * @param dt double
     */
    public void moveLeft(double dt) {
        if (this.topLeftCorner.getX() > this.leftBorder + this.speed * dt) {
            Point p = new Point(this.topLeftCorner.getX() - (this.speed * dt), this.topLeftCorner.getY());
            this.topLeftCorner = p;
        } else {
            Point p = new Point(this.leftBorder - 1, this.topLeftCorner.getY());
            this.topLeftCorner = p;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.topLeftCorner, this.width, this.height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        if (hitter.isEnemy()) {
            for (HitListener hl : this.hitListeners) {
                hl.hitEvent(this, hitter);
            }
        }
        return currentVelocity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collidable isInBlock(Point p) {
        return null;
    }


    /**
     * Shoot a new ball with velocity v from the middle of the paddle.
     * @param v Velocity
     * @return Return the ball
     */
    public Ball shoot(Velocity v) {
        Ball b = new Ball(this.getCollisionRectangle().getUpperBorder().middle(), 3, Color.blue);
        b.setVelocity(v);
        b.setStroke(Color.decode("#7bfdff"));
        return b;
    }

    /**
     * Add the paddle to an existing game.
     * @param game the game it should be added to.
     */
    public void addToGame(GameLevel game) {
        game.addToCollidables(this);
        game.addToSprites(this);
    }

    /**
     * Remove the paddle from given game.
     * @param spaceLevel SpaceLevel
     */
    public void removeFromGame(SpaceLevel spaceLevel) {
        spaceLevel.removeCollidable(this);
        spaceLevel.removeSprite(this);
    }
}
