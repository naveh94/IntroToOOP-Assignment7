package animations;
import biuoop.DrawSurface;
import gameobjects.SpriteCollection;
/**
 * Countdown Animations. Used before level start.
 * @author Naveh Marchoom
 *
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private double framesLeft;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int framesPerSecond;

    /**
     * Create a new Countdown animation. Will count from countFrom to 0, in numOfSeconds seconds.
     * Will show gameScreen sprites behind.
     * @param numOfSeconds double
     * @param countFrom int
     * @param gameScreen SpriteCollection
     * @param fps int
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen, int fps) {
        this.numOfSeconds = numOfSeconds;
        this.framesLeft = fps * numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.framesPerSecond = fps;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        // Paint the background:
        board.setColor(java.awt.Color.BLUE.darker().darker().darker().darker());
        board.fillRectangle(0, 0, board.getWidth(), board.getHeight());

        // Draw the game Screen:
        this.gameScreen.drawOnAll(board);

        // Write the countdown text:
        int count = (int) (framesLeft / (numOfSeconds * framesPerSecond / (countFrom + 1)));
        board.setColor(java.awt.Color.gray.darker().darker());
        board.drawText(board.getWidth() / 2 - 80 + 8, board.getHeight() / 2 + 8, "" + count + "", 300);
        board.setColor(java.awt.Color.BLACK);
        board.drawText(board.getWidth() / 2 - 82, board.getHeight() / 2, "" + count + "", 300);
        board.drawText(board.getWidth() / 2 - 78, board.getHeight() / 2, "" + count + "", 300);
        board.drawText(board.getWidth() / 2 - 80, board.getHeight() / 2 + 2, "" + count + "", 300);
        board.drawText(board.getWidth() / 2 - 80, board.getHeight() / 2 - 2, "" + count + "", 300);
        board.setColor(java.awt.Color.WHITE);
        board.drawText(board.getWidth() / 2 - 80, board.getHeight() / 2, "" + count + "", 300);

        framesLeft--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldStop() {
        return framesLeft == 0;
    }
}
