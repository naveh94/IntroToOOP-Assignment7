package readers;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Loader library for static classes for loading files.
 * @author Naveh Marchoom
 *
 */
public class Loader {

    /**
     * Load an image from filepath.
     * @param filepath String
     * @return Image.
     */
    public static Image loadImage(String filepath) {
        InputStream is = null;
        Image img = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);
            if (is == null) {
                System.out.println("Couldn't read image '" + filepath + "'.");
                return null;
            }
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Error reading image '" + filepath + "'.");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the image '" + filepath + "'.");
                    e.printStackTrace();
                }
            }
        }
        return img;
    }
}
