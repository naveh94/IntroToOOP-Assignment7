package enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Counter;
import geometry.Point;
import geometry.Velocity;
import hitlisteners.HitListener;
/**
 * A class representing an array of aliens.
 * @author Naveh Marchoom
 *
 */
public class AlienGroup {

    private List<List<Alien>> aliens;
    private double startingSpeed;
    private double currSpeed;
    private double startingLeftX;
    private double startingTopY;
    private double leftX;
    private double rightX;
    private double topY;
    private double bottomY;
    private final double rightBorder = 800.0;
    private final double leftBorder = 0.0;
    private final int rows = 10;
    private final int lines = 5;
    private final Velocity verticalMovement = new Velocity(0, 15);
    private Velocity horizontalMovement;
    private boolean movingRight;
    private Counter aliensNum;

    /**
     * Creates a new AlienGroup with given top y parameter, leftmost x parameter
     * and initial speed.
     * @param top double
     * @param left double
     * @param speed double
     */
    public AlienGroup(double top, double left, double speed) {
        this.startingSpeed = speed;
        this.currSpeed = speed;
        this.startingLeftX = left;
        this.leftX = left;
        this.startingTopY = top;
        this.topY = top;
        this.movingRight = true;
        this.aliensNum = new Counter(0);
        updateVelocity();
        createAliens();
    }

    /**
     * Update the horizontalMovement Velocity according the the group current speed.
     */
    private void updateVelocity() {
        this.horizontalMovement = new Velocity(this.currSpeed, 0);
    }

    /**
     * Creates an array 10x5 of aliens.
     */
    private void createAliens() {
        this.aliens = new ArrayList<>();
        for (int rIndex = 0; rIndex < this.rows; rIndex++) {
            List<Alien> row = new ArrayList<>();
            for (int lIndex = 0; lIndex < this.lines; lIndex++) {
                double currX = leftX + 50 * rIndex, currY = topY + 40 * lIndex;
                Alien a = new Alien(new Point(currX, currY));
                this.aliensNum.increase(1);
                row.add(a);
            }
            this.aliens.add(row);
        }
    }

    /**
     * Remove all aliens with <=0 health from the array, and check the new boundaries.
     */
    public void buryDeadAliens() {
        List<Alien> deads = new ArrayList<>();
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                if (a.getHitpoints() == 0) {
                    deads.add(a);
                }
            }
            for (Alien a : deads) {
                this.aliensNum.decrease(1);
                row.remove(a);
            }
            deads.clear();
        }
        if (this.aliensNum.getValue() > 0) {
            this.checkBoundaries();
        }
    }

    /**
     * Update the array top, bottom, left and right parameters according the current state of the
     * aliens' group.
     */
    public void checkBoundaries() {
        List<List<Alien>> emptyRows = new ArrayList<>();
        for (List<Alien> row : this.aliens) {
            if (row.isEmpty()) {
                emptyRows.add(row);
            }
        }
        for (List<Alien> row : emptyRows) {
            this.aliens.remove(row);
        }
        double topValue = Double.POSITIVE_INFINITY, bottomValue = Double.NEGATIVE_INFINITY;
        for (List<Alien> row : aliens) {
            Alien topAlien = row.get(0), bottomAlien = row.get(row.size() - 1);
            if (topValue > topAlien.getTopY()) {
                topValue = topAlien.getTopY();
            }
            if (bottomValue < bottomAlien.getBottomY()) {
                bottomValue = bottomAlien.getBottomY();
            }
        }
        this.topY = topValue;
        this.bottomY = bottomValue;
        Alien leftAlien = aliens.get(0).get(0), rightAlien = aliens.get(aliens.size() - 1).get(0);
        this.leftX = leftAlien.getLeftX();
        this.rightX = rightAlien.getRightX();
    }

    /**
     * Reset the position of all the aliens to their starting area, reseting the velocity and the movement
     * direction.
     */
    public void resetPosition() {
        Velocity v = new Velocity(this.startingLeftX - this.leftX, this.startingTopY - this.topY);
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.moveOneStep(v);
            }
        }
        this.currSpeed = this.startingSpeed;
        this.updateVelocity();
        this.movingRight = true;
    }

    /**
     * Move all the aliens in the group one step.
     * If gets to a border, will go down one step, and swap the horizontal movement direction.
     */
    public void moveOneStep() {
        Velocity v = this.horizontalMovement;
        if (movingRight && this.rightX + this.currSpeed > this.rightBorder) {
            this.movingRight = false;
            this.currSpeed = this.currSpeed * -1.1;
            updateVelocity();
            v = this.verticalMovement;
        }
        if (!movingRight && this.leftX + this.currSpeed < this.leftBorder) {
            this.horizontalMovement.swapXDirection();
            this.movingRight = true;
            this.currSpeed = this.currSpeed * -1.1;
            updateVelocity();
            v = this.verticalMovement;
        }
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.moveOneStep(v);
            }
        }
        this.buryDeadAliens();
    }

    /**
     * Make the bottom alien of a random alien row shoot a new ball directly downward from him.
     * @return Ball
     */
    public Ball shoot() {
        Random rand = new Random();
        int i = rand.nextInt(aliens.size());
        return this.aliens.get(i).get(aliens.get(i).size() - 1).shoot();
    }

    /**
     * Add all the aliens in the list to the given game.
     * @param g GameLevel
     */
    public void addToGame(GameLevel g) {
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.addToGame(g);
            }
        }
    }

    /**
     * Remove all the aliens in the list to the given game.
     * @param g GameLevel
     */
    public void removeFromGame(GameLevel g) {
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.removeFromGame(g);
            }
        }
    }

    /**
     * Add a given HitListener to all the aliens in the alien group.
     * @param hl HitListener
     */
    public void addListener(HitListener hl) {
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.addHitListener(hl);
            }
        }
    }

    /**
     * Remove given HitListener from all the aliens in the alien group who has it.
     * @param hl HitListener
     */
    public void removeListener(HitListener hl) {
        for (List<Alien> row : aliens) {
            for (Alien a : row) {
                a.removeHitListener(hl);
            }
        }
    }

    /**
     * If given alien is in the alien group, will remove it from it.
     * If not, will do nothing.
     * @param a Alien
     */
    public void removeAlien(Alien a) {
        for (List<Alien> row : aliens) {
            if (row.contains(a)) {
                row.remove(a);
                return;
            }
        }
    }

    /**
     * Get the bottom y parameter of the alien grid.
     * @return double
     */
    public double getBottomY() {
        return this.bottomY;
    }
}
