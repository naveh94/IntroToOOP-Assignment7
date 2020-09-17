package gameobjects;

/**
 * A stop condition class that can be sent and changed in other classes.
 * @author Naveh Marchoom
 */
public class StopCondition {
    private boolean stop;

    /**
     * Creates a new StopCondition object.
     */
    public StopCondition() {
        this.stop = false;
    }

    /**
     * Set the stop condition to true.
     */
    public void setTrue() {
        this.stop = true;
    }

    /**
     * Set the stop condition to false.
     */
    public void setFalse() {
        this.stop = false;
    }

    /**
     * Returns the current boolean state of the stop condition.
     * @return boolean
     */
    public boolean getValue() {
        return this.stop;
    }
}
