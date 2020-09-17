package blockcreator;

import java.awt.Image;

import biuoop.DrawSurface;
import geometry.Rectangle;
import readers.Loader;
/**
 * A image drawer with already set image and special coordinates.
 * @author Naveh Marchoom
 *
 */
public class DrawSpaceship implements Drawer {

    private Image image;

    /**
     * Creates a new Image drawer object.
     */
    public DrawSpaceship() {
        Image img = Loader.loadImage("block_images/paddle.gif");
        this.image = img;
    }

    /**
     * {@inheritDoc}
     */
    public void drawOn(DrawSurface board, Rectangle q) {
        int x = (int) q.getUpperBorder().middle().getX() - 35, y = (int) q.getUpperLeft().getY() - 20;
        board.drawImage(x, y, this.image);
    }


}
