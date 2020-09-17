package indicators;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import animations.Effects;
import biuoop.DrawSurface;
import gameobjects.Counter;
import gameobjects.Sprite;

/**
 * An top screen indicator for showing how many blocks left to break.
 * @author Naveh Marchoom
 */
public class BlocksLeftIndicator implements Sprite {

    private Counter remainingBlocks;
    private Image icon;

    /**
     * Creates a new BlocksLeftIndicator using given counter.
     * @param blocks Counter
     */
    public BlocksLeftIndicator(Counter blocks) {
        this.remainingBlocks = blocks;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("icon_images/enemy.gif");
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
        board.drawImage(90, 4, this.icon);
        Effects.textWithShadow(board, 110, 14, "" + this.remainingBlocks.getValue(), 12, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        // Nothing to change.
    }
}
