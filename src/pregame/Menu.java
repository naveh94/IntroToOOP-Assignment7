package pregame;

import animations.Animation;

/**
 * Menu<T> object.
 * @author Naveh Marchoom
 * @param <T> The return value of the menus selections.
 */
public interface Menu<T> extends Animation {

    /**
     * Add a selection option to the menu. Will show message, and will return
     * returnVal when key is pressed.
     * @param key String
     * @param message String
     * @param returnVal T
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Will return the returnedVal chosen.
     * @return T
     */
    T getStatus();

    /**
     * Add a sub-menu to the menu.
     * @param key String
     * @param message String
     * @param subMenu Menu<T>
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Resets the menu's stopping condition.
     */
    void resetStop();

    /**
     * Set given key in isClicked map to true.
     * @param key String
     */
    void clicked(String key);
}