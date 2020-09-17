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
 * Game's Score Indicator sprite.
 * @author Naveh Marchoom
 */
public class ScoreIndicator implements Sprite {

    private Counter currentScore;
    private Image icon;

    /**
     * Creates a new ScoreIndicator object using the currentScore Counter given.
     * @param currentScore Counter
     */
    public ScoreIndicator(Counter currentScore) {
        this.currentScore = currentScore;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("icon_images/coin.png");
            this.icon = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Error reading image.");
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("Failed closing the image 'icon_images/coin.png'.");
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board) {
        board.drawImage(board.getWidth() / 2 - 30, 4, this.icon);
        Effects.textWithShadow(board, board.getWidth() / 2 - 10,
                14, "" + currentScore.getValue() + "", 12, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        // Nothing to change.
    }
}
