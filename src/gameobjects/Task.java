package gameobjects;
/**
 * Task interface.
 * @author Naveh Marchoom
 * @param <T> Task return's type.
 */
public interface Task<T> {
    /**
     * Run the task.
     * @return T
     */
    T run();
}
