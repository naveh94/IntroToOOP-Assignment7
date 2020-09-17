package indicators;

import animations.Effects;
import biuoop.DrawSurface;
import gameobjects.Sprite;

/**
 * Level's Name indicator sprite.
 * @author Naveh Marchoom
 *
 */
public class NameIndicator implements Sprite {

    private String name;

    /**
     * Creates a new NameIndicator Object that show given String.
     * @param n String
     */
    public NameIndicator(String n) {
        this.name = n;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void drawOn(DrawSurface board) {
        board.setColor(java.awt.Color.white);
        Effects.textWithShadow(board, board.getWidth() - 180,
                14, this.name, 12, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub

    }

}
