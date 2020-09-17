package animations;

import java.awt.Color;

import biuoop.DrawSurface;

/**
 * A library for effects.
 * @author Naveh Marchoom
 *
 */
public class Effects {

    /**
     * Creates a text with shadow given fill color.
     * @param board DrawSurface
     * @param x int
     * @param y int
     * @param text String
     * @param size int
     * @param c Color
     * @param depth int
     */
    public static void textWithShadow(DrawSurface board, int x, int y, String text, int size, Color c, int depth) {
        board.setColor(Color.black);
        board.drawText(x + depth, y + depth, text, size);
        board.setColor(c);
        board.drawText(x, y, text, size);
    }

    /**
     * Creates a text with shadow with white fill color.
     * @param board DrawSurface
     * @param x int
     * @param y int
     * @param text String
     * @param size int
     * @param depth int
     */
    public static void textWithShadow(DrawSurface board, int x, int y, String text, int size, int depth) {
        textWithShadow(board, x, y, text, size, Color.white, depth);
    }

    /**
     * Draw a line with shadow.
     * @param board DrawSurface
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param c Color
     */
    public static void lineWithShadow(DrawSurface board, int x1, int y1, int x2, int y2, Color c) {
        board.setColor(Color.black);
        board.drawLine(x1, y1 + 1, x2, y2 + 1);
        board.setColor(c);
        board.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draw a line with top shadow.
     * @param board DrawSurface
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param c Color
     */
    public static void lineWithShadowR(DrawSurface board, int x1, int y1, int x2, int y2, Color c) {
        board.setColor(c);
        board.drawLine(x1, y1 + 1, x2, y2 + 1);
        board.setColor(Color.BLACK);
        board.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draw a line with left shadow.
     * @param board DrawSurface
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param c Color
     */
    public static void lineWithShadowRY(DrawSurface board, int x1, int y1, int x2, int y2, Color c) {
        board.setColor(c);
        board.drawLine(x1 + 1, y1, x2 + 1, y2);
        board.setColor(Color.BLACK);
        board.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draw text with stroke with given fill color and stroke color.
     * @param board DrawSurface
     * @param x int
     * @param y int
     * @param s String
     * @param size int
     * @param fill Color
     * @param stroke Color
     */
    public static void textStroke(DrawSurface board, int x, int y, String s, int size, Color fill, Color stroke) {
        board.setColor(stroke);
        board.drawText(x + 1, y, s, size);
        board.drawText(x - 1, y, s, size);
        board.drawText(x, y + 1, s, size);
        board.drawText(x, y - 1, s, size);
        board.setColor(fill);
        board.drawText(x, y, s, size);
    }

    /**
     * Draw white text with black stroke.
     * @param board DrawSurface
     * @param x int
     * @param y int
     * @param s String
     * @param size int
     */
    public static void textStroke(DrawSurface board, int x, int y, String s, int size) {
        textStroke(board, x, y, s, size, Color.WHITE, Color.black);
    }

    /**
     * Get a string and return the matching color.
     * @param s String
     * @return Color
     */
    public static Color colorFromString(String s) {
        s = s.toLowerCase();
        if (s.startsWith("black")) {
            return Color.BLACK;
        }
        if (s.startsWith("white")) {
            return Color.WHITE;
        }
        if (s.startsWith("yellow")) {
            return Color.YELLOW;
        }
        if (s.startsWith("red")) {
            return Color.RED;
        }
        if (s.startsWith("blue")) {
            return Color.BLUE;
        }
        if (s.startsWith("cyan")) {
            return Color.CYAN;
        }
        if (s.startsWith("green")) {
            return Color.GREEN;
        }
        if (s.startsWith("gray")) {
            return Color.GRAY;
        }
        if (s.startsWith("lightgray")) {
            return Color.LIGHT_GRAY;
        }
        if (s.startsWith("pink")) {
            return Color.PINK;
        }
        if (s.startsWith("orange")) {
            return Color.ORANGE;
        }
        return null;
    }
}
