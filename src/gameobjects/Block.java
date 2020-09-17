package gameobjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import animations.Effects;
import animations.GameLevel;
import biuoop.DrawSurface;
import blockcreator.Drawer;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import hitlisteners.HitListener;
/**
 * Block object.
 * @author Naveh Marchoom
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Point topLeftCorner;
    private double width;
    private double height;
    private int hitpoints = -1;
    private Drawer fill;
    private Map<Integer, Drawer> fillK;
    private Drawer stroke;
    private List<HitListener> hitListeners;
    private boolean showHP = false;

    /**
     * Create a new block using the top left corner, the width and the height of the block.
     * @param topLeftCorner point.
     * @param width double.
     * @param height double.
     */
    public Block(Point topLeftCorner, double width, double height) {
        this.topLeftCorner = topLeftCorner;
        this.width = width;
        this.height = height;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Set the block fill Drawer.
     * @param d Drawer
     */
    public void setFill(Drawer d) {
        this.fill = d;
    }

    /**
     * Set the block's fill-K drawer.
     * @param fk Drawer
     */
    public void setFillK(Map<Integer, Drawer> fk) {
        this.fillK = fk;
    }

    /**
     * Set the block's Stroke Drawer.
     * @param d Drawer
     */
    public void setStroke(Drawer d) {
        this.stroke = d;
    }

    /**
     * Get the collision rectangle of the block.
     * @return the collision rectangle of the block.
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.topLeftCorner, this.width, this.height);
    }

    /**
     * Notify the block it has been hit, and return the new velocity should be set to the ball.
     * @param collisionPoint the point of collision.
     * @param currentVelocity the velocity it's been hit by.
     * @param hitter Ball
     * @return the new velocity should be set to the ball.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        Velocity newVelocity = currentVelocity;

        if (this.hitpoints > 0) {
            this.hitpoints--;
        }

        if ((int) collisionPoint.getX() == this.topLeftCorner.getX()
                || (int) collisionPoint.getX() == this.topLeftCorner.getX() + width) {
            newVelocity.swapXDirection();
        }
        if ((int) collisionPoint.getY() == this.topLeftCorner.getY()
                || (int) collisionPoint.getY() == this.topLeftCorner.getY() + height) {
            newVelocity.swapYDirection();
        }
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * Draw the block on the surface.
     * @param surface the Draw Surface.
     */
    public void drawOn(DrawSurface surface) {
        if (fill != null) {
            this.fill.drawOn(surface, getCollisionRectangle());
        }
        if (fillK != null && fillK.containsKey(this.hitpoints)) {
            this.fillK.get(this.hitpoints).drawOn(surface, getCollisionRectangle());
        }
        if (stroke != null) {
            this.stroke.drawOn(surface, getCollisionRectangle());
        }
        if (this.showHP) {
            Effects.textStroke(surface, (int) this.topLeftCorner.getX() + (int) this.width / 2 - 2,
                    (int) (this.topLeftCorner.getY() + this.height / 2 + 3), "" + this.hitpoints, 11);
        }
    }

    /**
     * Time has passed.
     * @param dt double
     */
    public void timePassed(double dt) {
        // Does nothing; It's a block! :D
    }

    /**
     * Add this block to the game.
     * @param level the game
     */
    public void addToGame(GameLevel level) {
        level.addToCollidables(this);
        level.addToSprites(this);
    }

    /**
     * Add this block to the game.
     * @param level the game
     */
    public void addToGameAllies(GameLevel level) {
        level.addToCollidables(this);
        level.addToSprites(this);
    }

    /**
     * Set the hit points of the block. By default 0;
     * @param hp the hit points should be set for the block.
     */
    public void setHitPoints(int hp) {
        this.hitpoints = hp;
    }

    /**
     * Remove this block from Game game.
     * @param level Game
     */
    public void removeFromGame(GameLevel level) {
        level.removeCollidable(this);
        level.removeCollidable(this);
        level.removeSprite(this);
        level.removeSprite(this);
    }

    /**
     * Notify all HitListeners in listeners list, that given hitter Ball hit this block.
     * @param hitter Ball
     */
    protected void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
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
     * Get the block current Hitpoints.
     * @return int
     */
    public int getHitpoints() {
        return this.hitpoints;
    }

    /**
     * {@inheritDoc}
     */
    public Block isInBlock(Point p) {
        Point bottomRightBorder = new Point(this.topLeftCorner.getX() + this.width,
                this.topLeftCorner.getY() + this.height);
        if (p.getX() > this.topLeftCorner.getX() && p.getX() < bottomRightBorder.getX()
            && p.getY() > this.topLeftCorner.getY() && p.getY() < bottomRightBorder.getY()) {
                return this;
            }
        return null;
    }

    /**
     * Set if to show the blocks HP or not.
     * @param b boolean
     */
    public void showHp(boolean b) {
        this.showHP = b;
    }

    /**
     * Move the block one step according the given velocity.
     * Can be called only on inheriting objects.
     * @param v Velocity
     */
    protected void moveOneStep(Velocity v) {
        this.topLeftCorner = v.applyToPoint(this.topLeftCorner);
    }
}
