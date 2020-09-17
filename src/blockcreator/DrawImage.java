package blockcreator;

import java.awt.Image;

import biuoop.DrawSurface;
import geometry.Rectangle;
/**
 * Image drawer.
 * @author Naveh Marchoom
 */
public class DrawImage implements Drawer {

    private Image image;

    /**
     * Creates a new Image drawer object.
     * @param img Image
     */
    public DrawImage(Image img) {
        this.image = img;
    }

    @Override
    public void drawOn(DrawSurface board, Rectangle q) {
        int x = (int) q.getUpperLeft().getX(), y = (int) q.getUpperLeft().getY();
        board.drawImage(x, y, this.image);
    }

}
