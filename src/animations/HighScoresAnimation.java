package animations;

import java.awt.Color;

import biuoop.DrawSurface;
import gameobjects.HighScoresTable;
import gameobjects.ScoreInfo;

/**
 * HighScoresAnimation object.
 * @author Naveh Marchoom
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;

    /**
     * Creats a new HighScoreAnimation with given HighScoreTable.
     * @param hst HighScoresTable
     */
    public HighScoresAnimation(HighScoresTable hst) {
        this.scores = hst;
    }

    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        board.setColor(Color.BLACK);
        board.fillRectangle(0, 0, board.getWidth(), board.getHeight());
        board.setColor(Color.gray);
        board.fillRectangle(200, 70, 400, 467);
        board.setColor(Color.gray.darker().darker());
        board.fillRectangle(205, 75, 395, 462);
        board.setColor(Color.gray.darker());
        board.fillRectangle(205, 75, 390, 457);
        Effects.lineWithShadowR(board, 205, 105, 594, 105, Color.gray);
        Effects.textWithShadow(board, board.getWidth() / 2 - 60, 98, "Score Board", 20, 2);
        Effects.textWithShadow(board, board.getWidth() / 2 - 65, 150, "Click \'Space' to exit.", 15, 1);
        int i = 200;
        for (ScoreInfo s : this.scores.getHighScores()) {
            Effects.lineWithShadowR(board, 205, i - 19, 594, i - 19, Color.gray);
            Effects.textWithShadow(board, board.getWidth() / 2 + 50, i, s.getName(), 15, 1);
            Effects.textWithShadow(board, board.getWidth() / 2 - 50 - (int) Math.log10(s.getScore()) * 8,
                    i, "" + s.getScore(), 15, 1);
            i += 25;
        }
        if (i - 19 < 530) {
            Effects.lineWithShadowR(board, 205, i - 19, 594, i - 19, Color.gray);
        }
        Effects.lineWithShadowRY(board, board.getWidth() / 2, 182, board.getWidth() / 2, i - 19, Color.gray);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

}
