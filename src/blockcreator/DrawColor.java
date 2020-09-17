package blockcreator;

import java.awt.Color;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * DrawColor object.
 * @author Naveh Marchoom
 */
public class DrawColor implements Drawer {

    private Color color;

    /**
     * Creates a new DrawColor object.
     * @param c Color
     */
    public DrawColor(Color c) {
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
        board.fillRectangle(x, y, width, height);
    }

}
