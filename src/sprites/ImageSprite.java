package sprites;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import gameobjects.Sprite;
/**
 * Image Sprite object.
 * @author Naveh Marchoom
 */
public class ImageSprite implements Sprite {

    private Image image;
    private int x;
    private int y;

    /**
     * Create a new image sprite using an Image and x,y paramaters.
     * @param x int
     * @param y int
     * @param img Image
     */
    public ImageSprite(int x, int y, Image img) {
        this.image = img;
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board) {
        board.drawImage(x, y, image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        // Not a dynamic Sprite.
    }

    /**
     * Return an image sprite with the coordinates (0, 0).
     * @param s String.
     * @return ImageSprite.
     */
    public static Sprite fromString(String s) {
        return fromString(s, 0, 0);
    }

    /**
     * Return an image sprite with the given coordinates.
     * @param s String
     * @param x int
     * @param y int
     * @return ImageSprite
     */
    public static Sprite fromString(String s, int x, int y) {
        Image i = imageFromString(s);
        if (i != null) {
            return new ImageSprite(x, y, i);
        }
        return null;
    }

    /**
     * Load an image from given string.
     * @param s String
     * @return Image
     */
    public static Image imageFromString(String s) {
        Image i = null;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(s);
            i = ImageIO.read(is);
            return i;
        } catch (IOException e) {
            System.out.println("Error reading image.");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed closing image '" + s + "'.");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}