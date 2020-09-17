package gameobjects;

import animations.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import blockcreator.Drawer;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
/**
 * The Paddle the user controls.
 * @author Naveh Marchoom
 *
 */
public class Paddle implements Collidable, Sprite {

    private Point topLeftCorner;
    private double width = 20;
    private double height = 20;
    private double speed = 5;
    private Drawer fill = null;
    private Drawer stroke = null;
    private biuoop.KeyboardSensor keyboard;
    private int leftBorder = Integer.MIN_VALUE, rightBorder = Integer.MAX_VALUE;

    /**
     * Create a paddle using corner, width and keyboard parameters.
     * @param topLeftCorner The top left corner of the paddle
     * @param width the width of the paddle.
     * @param keyboard the keyboard sensor sensing keyboard clicks.
     */
    public Paddle(Point topLeftCorner, double width, biuoop.KeyboardSensor keyboard) {
        this.topLeftCorner = topLeftCorner;
        this.width = width;
        this.keyboard = keyboard;
    }

    /**
     * Set the paddle's borders.
     * @param left left border.
     * @param right right border.
     */
    public void setBorders(int left, int right) {
        this.leftBorder = left;
        this.rightBorder = right;
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
     * Draw the paddle on the surface.
     * @param board The draw surface it would be drawn on.
     */
    public void drawOn(DrawSurface board) {
        this.fill.drawOn(board, this.getCollisionRectangle());
        if (this.stroke != null) {
            this.stroke.drawOn(board, getCollisionRectangle());
        }
    }

    /**
     * Notify the paddle time has passed.
     * @param dt double
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }

    /**
     * Get the paddle's collision rectangle.
     * @return The paddle's collision rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.topLeftCorner, this.width, this.height);
    }

    /**
     * Notify the paddle it been hit by the ball, and get new velocity to the ball according to the
     * collision point.
     * @param collisionPoint The collision Point.
     * @param currentVelocity The collision velocity.
     * @param hitter Ball
     * @return new velocity for the ball.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        double region = (collisionPoint.getX() - this.topLeftCorner.getX()) / width;
        // In case it hit the top border, it will get new velocity according to the spot it hit.
        if (this.getCollisionRectangle().getUpperBorder().isOnLine(collisionPoint)) {
            Velocity v = Velocity.fromAngleAndSpeed(160 * region + 190, currentVelocity.getSpeed());
            if (region > 0.49 && region < 0.51) {
                v = Velocity.fromAngleAndSpeed(Velocity.straightUpAngle(), currentVelocity.getSpeed());
            }
            return v;
        }
        // In case it hit one of the side borders, it will change only its X direction
        if (this.getCollisionRectangle().getLeftBorder().isOnLine(collisionPoint)
                || this.getCollisionRectangle().getRightBorder().isOnLine(collisionPoint)) {
            currentVelocity.swapXDirection();
            return currentVelocity;
        }
        return currentVelocity;
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
     * Set the paddle's speed.
     * @param s double
     */
    public void setSpeed(double s) {
        this.speed = s;
    }

    @Override
    public Collidable isInBlock(Point p) {
        // If ball get in paddle, I want him to go down, and not get stuck.
        return null;
    }

    /**
     * Remove the paddle of the GameLevel.
     * @param level GameLevel
     */
    public void removeFromGame(GameLevel level) {
        level.removeCollidable(this);
        level.removeSprite(this);
    }

    /**
     * Set the paddle's fill.
     * @param d Drawer
     */
    public void setFill(Drawer d) {
        this.fill = d;
    }

    /**
     * Set the paddle's stroke.
     * @param d Drawer
     */
    public void setStroke(Drawer d) {
        this.stroke = d;
    }
}
