package gameobjects;

import biuoop.DrawSurface;
/**
 * A collection of all the sprites in the current game.
 * @author Naveh Marchoom
 *
 */
public class SpriteCollection {

    private java.util.ArrayList<Sprite> sprites = new java.util.ArrayList<Sprite>();

    /**
     * Add a new sprite to the sprite list.
     * @param s The sprite added.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Notify all sprites in the collection that time has passed.
     * @param dt double
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).timePassed(dt);
        }
    }

    /**
     * Draw all sprites in the collection on a drawsurface.
     * @param d Drawsurface
     */
    public void drawOnAll(DrawSurface d) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).drawOn(d);
        }
    }

    /**
     * Remove Sprite s from the sprites list.
     * @param s Sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
