package animations;

import biuoop.DrawSurface;
import gameobjects.Counter;
import readers.Loader;
import sprites.ImageSprite;
/**
 * A game over screen with image as the background.
 * @author Naveh Marchoom
 *
 */
public class GameOver implements Animation {

    private Counter currentScore;
    private ImageSprite background;

    /**
     * Creates a new GameOver screen with given scoe.
     * @param score Counter
     */
    public GameOver(Counter score) {
        this.currentScore = score;
        this.background = new ImageSprite(0, 0, Loader.loadImage("background_images/gameover.jpg"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        if (this.background != null) {
            this.background.drawOn(board);
        } else {
            String loseMessage = "Game Over. your score is";
            Effects.textWithShadow(board, board.getWidth() / 2 - loseMessage.length() * 12, 400, loseMessage, 50, 3);
        }
        Effects.textWithShadow(board,
                board.getWidth() / 2 - (28 * (int) Math.floor(Math.log10(this.currentScore.getValue()))),
                480, "" + this.currentScore.getValue(), 80, 3);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
