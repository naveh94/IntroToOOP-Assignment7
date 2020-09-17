package gameobjects;

import animations.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * AnimationRunner Object.
 * @author Naveh Marchoom
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    /**
     * Creates a new AnimationRunner using a given GUI and frames per second.
     * @param gui GUI
     * @param fps int
     */
    public AnimationRunner(GUI gui, int fps) {
        this.gui = gui;
        this.framesPerSecond = fps;
    }

    /**
     * Run the given animation.
     * @param animation Animation
     */
    public void run(Animation animation) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int milliSecondsPerFrame = 1000 / framesPerSecond;

        double dt = (1.0 / this.framesPerSecond);
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // Timing
            DrawSurface board = this.gui.getDrawSurface();

            animation.doOneFrame(board, dt);

            gui.show(board);
            // Timing:
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondsLeftToSleep = milliSecondsPerFrame - usedTime;
            if (milliSecondsLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondsLeftToSleep);
            }
        }
    }

    /**
     * Return the runner's fps.
     * @return int
     */
    public int getFps() {
        return this.framesPerSecond;
    }
}
