package pregame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animations.Effects;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameobjects.AnimationRunner;
/**
 * Creates a new MenuAnimation object that implements menu.
 * @author Naveh Marchoom
 * @param <T> The MenuAnimation selection's return type.
 */
public class MenuAnimation<T> implements Menu<T> {

    private String name;
    private T status = null;
    private KeyboardSensor ks;
    private AnimationRunner runner;
    private boolean stop;
    private List<Selection<T>> selections;
    private List<Selection<Menu<T>>> subMenus;
    private Map<String, Boolean> isClicked;

    /**
     * Creates a new Menu Animation.
     * @param keyboard KeyboardSensor
     * @param menuName String
     * @param ar AnimationRunner
     */
    public MenuAnimation(String menuName, KeyboardSensor keyboard, AnimationRunner ar) {
        this.ks = keyboard;
        this.name = menuName;
        this.runner = ar;
        this.selections = new ArrayList<Selection<T>>();
        this.subMenus = new ArrayList<Selection<Menu<T>>>();
        this.isClicked = new HashMap<>();
        this.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        board.setColor(Color.BLACK);
        board.fillRectangle(0, 0, board.getWidth(), board.getHeight());
        board.setColor(Color.GRAY);
        board.fillRectangle(50, 50, board.getWidth() - 95, board.getHeight() - 95);
        board.setColor(Color.GRAY.darker().darker());
        board.fillRectangle(55, 55, board.getWidth() - 100, board.getHeight() - 100);
        board.setColor(Color.GRAY.darker());
        board.fillRectangle(55, 55, board.getWidth() - 105, board.getHeight() - 105);
        Effects.lineWithShadowR(board, 55, 150, board.getWidth() - 51, 150, Color.GRAY);
        Effects.lineWithShadowRY(board, 180, 55, 180, board.getHeight() - 51, Color.GRAY);
        Effects.textWithShadow(board, 200, 120, this.name, 50, 3);
        int i = 200;
        for (Selection<Menu<T>> s : this.subMenus) {
            Effects.textWithShadow(board, 303, i, s.getMessage(), 30, 3);
            Effects.textWithShadow(board, 280, i, s.getKey().toUpperCase(), 30, Color.GREEN, 3);
            i += 60;
        }
        for (Selection<T> s : this.selections) {
            Effects.textWithShadow(board, 303, i, s.getMessage(), 30, 3);
            Effects.textWithShadow(board, 280, i, s.getKey().toUpperCase(), 30, Color.GREEN, 3);
            i += 60;
        }
        for (Selection<Menu<T>> s : this.subMenus) {
            if (this.ks.isPressed(s.getKey())) {
                if (!this.isClicked.get(s.getKey())) {
                    s.getReturnVal().clicked(s.getKey());
                    this.runner.run(s.getReturnVal());
                    this.status = s.getReturnVal().getStatus();
                    s.getReturnVal().resetStop();
                    this.stop = true;
                    return;
                }
            } else {
                this.isClicked.replace(s.getKey(), false);
            }
        }
        for (Selection<T> s : this.selections) {
            if (this.ks.isPressed(s.getKey())) {
                if (!this.isClicked.get(s.getKey())) {
                    this.status = s.getReturnVal();
                    this.stop = true;
                    return;
                }
            } else {
                this.isClicked.replace(s.getKey(), false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<T>(key, message, returnVal));
        this.isClicked.put(key, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getStatus() {
        return this.status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetStop() {
        this.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new Selection<Menu<T>>(key, message, subMenu));
        this.isClicked.put(key, false);
    }

    /**
     * {@inheritDoc}
     */
    public void clicked(String key) {
        if (this.isClicked.containsKey(key)) {
            this.isClicked.replace(key, true);
        }
    }
}
