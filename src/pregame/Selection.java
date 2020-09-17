package pregame;
/**
 * Menu's Selection object.
 * @author Naveh Marchoom
 * @param <T> The selection's return type.
 */
public class Selection<T> {

    private String key;
    private String message;
    private T returnVal;

    /**
     * Creates a new Selection with given key, message and return value.
     * @param key String
     * @param message String
     * @param returnVal T
     */
    public Selection(String key, String message, T returnVal) {
        this.setKey(key);
        this.setMessage(message);
        this.setReturnVal(returnVal);
    }

    /**
     * Get the selection's key.
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the selection's key.
     * @param k String
     */
    private void setKey(String k) {
        this.key = k;
    }

    /**
     * Get the selection's message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the selection's message.
     * @param m String
     */
    private void setMessage(String m) {
        this.message = m;
    }

    /**
     * Get the selection's Return value.
     * @return T
     */
    public T getReturnVal() {
        return returnVal;
    }

    /**
     * Set the selection's return value.
     * @param val T
     */
    private void setReturnVal(T val) {
        this.returnVal = val;
    }
}
