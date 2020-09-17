package animations;
import biuoop.DrawSurface;
/**
 * Animation Interface.
 * @author Naveh Marchoom
 *
 */
public interface Animation {

    /**
     * Run one frame of the animation.
     * @param board DrawSurface
     * @param dt double
     */
    void doOneFrame(DrawSurface board, double dt);

    /**
     * Returns the stopping condition of the animation.
     * @return boolean
     */
    boolean shouldStop();
}
