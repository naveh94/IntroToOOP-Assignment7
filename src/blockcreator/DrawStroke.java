package blockcreator;

import java.awt.Color;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * DrawStroke object.
 * @author Naveh Marchoom
 */
public class DrawStroke implements Drawer {

    private Color color;

    /**
     * Creates a new DrawStroke object.
     * @param c Color
     */
    public DrawStroke(Color c) {
        this.color = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board, Rectangle q) {
        int x = (int) q.getUpperLeft().getX(), y = (int) q.getUpperLeft().getY(),
                width = (int) q.getWidth(), height = (int) q.getHeight();
        board.setColor(this.color);
        board.drawRectangle(x, y, width, height);
    }

}
