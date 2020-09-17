 package gameobjects;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;

/**
 * @author Naveh Marchoom
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color fill;
    private Color stroke = Color.BLACK;
    private Velocity movement = new Velocity(0, 0);
    private GameEnvironment environment = null;
    private boolean enemy = false;

    /**
     * Construct a new ball using an existing point, the ball's radius, and the ball's color.
     * @param center The position of the ball.
     * @param r The size of the ball.
     * @param color The color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.fill = color;
    }

    /**
     * Construct a new ball using an existing point, the ball's radius, and the ball's color.
     * @param center The position of the ball.
     * @param r The size of the ball.
     * @param color The color of the ball.
     * @param isEnemy boolean
     */
    public Ball(Point center, int r, java.awt.Color color, boolean isEnemy) {
        this.center = center;
        this.radius = r;
        this.fill = color;
        this.enemy = isEnemy;
    }

    /**
     * Construct a new ball using a point parameters, the ball's radius, and the ball's color.
     * @param x The position of the ball on the 'x' axis.
     * @param y The position of the ball on the 'y' axis.
     * @param r The size of the ball.
     * @param color The color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.fill = color;
    }

    /**
     * Get the position of the ball on the 'x' axis.
     * @return the 'x' value of the ball's center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Get the position of the ball on the 'y' axis.
     * @return the 'y' value of the ball's center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Get the size of the ball.
     * @return the value of the ball's radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Get the color of the ball.
     * @return the color value of the ball.
     */
    public Color getColor() {
        return this.fill;
    }

    /**
     * Draw the ball on a given surface.
     * @param surface the surface the ball should be drawn at.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.fill);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(this.stroke);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        java.util.Random rand = new java.util.Random();
        int n = this.radius + 1;
        for (int i = 0; i < 15; i++) {
            surface.drawLine(rand.nextInt(10) - 5 + (int) this.center.getX(),
                    rand.nextInt(2 * n) - n + (int) this.center.getY(),
                    rand.nextInt(2 * n) - n + (int) this.center.getX(),
                    rand.nextInt(2 * n) - n + (int) this.center.getY());
        }
        surface.setColor(this.fill);
        for (int i = 0; i < 5; i++) {
            surface.drawLine(rand.nextInt(10) - 5 + (int) this.center.getX(),
                    rand.nextInt(2 * n) - n + (int) this.center.getY(),
                    rand.nextInt(2 * n) - n + (int) this.center.getX(),
                    rand.nextInt(2 * n) - n + (int) this.center.getY());
        }
    }

    /**
     * Set the ball's velocity using an existing Velocity object.
     * @param v the ball's new velocity.
     */
    public void setVelocity(Velocity v) {
        this.movement = v;
    }

    /**
     * Set the ball's velocity using dx and dy values.
     * @param dx the ball's new 'x' value's velocity.
     * @param dy the ball's new 'y' value's velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.movement = new Velocity(dx, dy);
    }

    /**
     * Get the ball's velocity.
     * @return the ball's Velocity value.
     */
    public Velocity getVelocity() {
        return movement;
    }

    /**
     * Move the ball one step forward using the ball's velocity values.
     * @param dt double
     */
    public void moveOneStep(double dt) {

        if (environment != null) {
            if (this.environment.getClosestCollision(this.getTrajectory(dt)) != null) {
                Point colP = new Point(this.environment.getClosestCollision(this.getTrajectory(dt)).collisionPoint());
                Collidable colOb = this.environment.getClosestCollision(getTrajectory(dt)).collisionObject();
                this.setVelocity(colOb.hit(colP, this.getVelocity(), this));
                if (this.getVelocity() == null) {
                    System.out.println("1");
                }
            } else {
                /* I've tried so many things for preventing the blocks from entering blocks, but nothing
                 * seem to work. I'm sorry for using this, but that the only thing that seem to work. */
                Point cx, cy, cyx;
                Collidable c;
                cx = new Point(this.center.getX() + this.getVelocity().getDx() * dt, this.center.getY());
                cy = new Point(this.center.getX(), this.center.getY() + this.getVelocity().getDy() * dt);
                cyx = new Point(this.center.getX() + this.getVelocity().getDx() * dt,
                        this.center.getY() + this.getVelocity().getDy() * dt);
                c = environment.isInBlock(cyx);
                if (c != null) {
                    this.setVelocity(new Velocity(-this.getVelocity().getDx(), -this.getVelocity().getDy()));
                    c.hit(cy, this.getVelocity(), this);
                } else {
                    c = environment.isInBlock(cx);
                    if (c != null) {
                        this.setVelocity(new Velocity(-this.getVelocity().getDx(), this.getVelocity().getDy()));
                        c.hit(cx, this.getVelocity(), this);
                    }
                    c = environment.isInBlock(cy);
                    if (c != null) {
                        this.setVelocity(new Velocity(this.getVelocity().getDx(), -this.getVelocity().getDy()));
                        c.hit(cy, this.getVelocity(), this);
                    }
                }
            }
        }
        /**/
        // Move the ball one step forward:
        this.center = this.getVelocity().applyToPoint(this.center, dt);
        this.center = new Point(this.center);
    }

    /**
     * Set the ball's environment to a specific game environment.
     * @param game The environment.
     */
    public void setEnvironment(GameEnvironment game) {
        this.environment = game;
    }

    /**
     * Get the current trajectory line of the ball.
     * @param dt double
     * @return a trajectory line.
     */
    public Line getTrajectory(double dt) {
        Point end = new Point(this.center);
        // Calculate using current ball's velocity where the ball will change direction:
        end = this.getVelocity().applyToPoint(end, dt);
        // Return a line that start at current ball's location and end where it will change velocity;
        return new Line(this.center, end);
    }

    /**
     * TIme has passed. Move the ball ones.
     * @param dt double
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Add this ball to the game.
     * @param level the game.
     */
    public void addToGame(GameLevel level) {
        level.addToSprites(this);
    }

    /**
     * Remove the ball from given GameLevel.
     * @param level GameLevel
     */
    public void removeFromGame(GameLevel level) {
        level.removeSprite(this);
    }

    /**
     * Set the fill color for the ball.
     * @param c Color
     */
    public void setFill(Color c) {
        this.fill = c;
    }

    /**
     * Set the stroke color for the ball.
     * @param c Color
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }

    /**
     * Return if the ball is an enemy shot.
     * @return boolean
     */
    public boolean isEnemy() {
        return this.enemy;
    }
}