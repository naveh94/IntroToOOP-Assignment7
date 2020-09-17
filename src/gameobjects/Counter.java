package gameobjects;
/**
 * Counter object.
 * @author Naveh Marchoom
 */
public class Counter {

    private int count = 0;

    /**
     * Create a new Counter object and set the starting number to num.
     * @param num int.
     */
    public Counter(int num) {
        this.count = num;
    }

    /**
     * Create a new Counter object with starting number set to 0.
     */
    public Counter() {
    }

    /**
     * Increase the counter by given number.
     * @param number int
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease the counter by given number.
     * @param number int
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Return the counter current value.
     * @return int
     */
    public int getValue() {
        return this.count;
    }

    /**
     * Reset the counter to given number.
     * @param num int
     */
    public void reset(int num) {
        this.count = num;
    }
}
