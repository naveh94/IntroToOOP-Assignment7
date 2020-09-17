package blockcreator;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * Drawer interface.
 * @author Naveh Marchoom
 *
 */
public interface Drawer {

    /**
     * Draw this specific drawer on the given Drawsurface.
     * @param board DrawSurface
     * @param q Rectangle
     */
    void drawOn(DrawSurface board, Rectangle q);

}
