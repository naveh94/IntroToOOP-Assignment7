package indicators;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import animations.Effects;
import biuoop.DrawSurface;
import gameobjects.Counter;
import gameobjects.Sprite;

/**
 * Lives Indicator sprite.
 * @author Naveh Marchoom
 *
 */
public class LivesIndicator implements Sprite {

    private Counter lives;
    private Image icon;

    /**
     * Creates a new LivesIndicator object given the livesCouner given.
     * @param livesLeft Counter
     */
    public LivesIndicator(Counter livesLeft) {
        this.lives = livesLeft;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("icon_images/heart.png");
            this.icon = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Error reading image.");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the image 'icon_images/heart.png'.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board) {
        board.setColor(Color.DARK_GRAY);
        board.fillRectangle(0, 0, board.getWidth(), 19);
        board.drawImage(40, 4, this.icon);
        Effects.textWithShadow(board, 60, 14, "" + this.lives.getValue() + "", 12, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        // Nothing to change.
    }

}
