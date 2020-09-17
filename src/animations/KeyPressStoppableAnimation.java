package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation.
 * An animation with keypress stopping method.
 * @author Naveh Marchoom
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboard;
    private String stopKey;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed = false;

    /**
     * Creates a new KeyPressStoppableAnimation with given key, and animation.
     * @param ks KeyboardSensor
     * @param key String
     * @param ani Animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor ks, String key, Animation ani) {
        this.keyboard = ks;
        this.stopKey = key;
        this.animation = ani;
        this.stop = false;
        if (this.keyboard.isPressed(stopKey)) {
            this.isAlreadyPressed = true;
        }
    }

    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        this.animation.doOneFrame(board, dt);
        if (this.keyboard.isPressed(stopKey)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
