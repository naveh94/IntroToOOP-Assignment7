package animations;

import gameobjects.Collidable;
import gameobjects.Counter;
import gameobjects.Sprite;
import levels.LevelInformation;
/**
 * GameLevel object.
 * @author Naveh Marchoom
 *
 */
public interface GameLevel extends Animation {

     /**
     * Run the level until the player win, or until the player is out of lives.
     */
    void run();

    /**
     * Initialize a new level, setting the score and the lives counters, and creating the level
     * according the the LevelInfo given.
     * @param levelInfo LevelInformation
     * @param scoreCounter Counter
     * @param livesCounter Counter
     */
    void initialize(LevelInformation levelInfo, Counter scoreCounter,
            Counter livesCounter);

    /**
     * Run one turn of the level, until player win or dies.
     */
    void oneTurn();

    /**
     * Add a collidable to the game environment.
     * @param c collidable
     */
    void addToCollidables(Collidable c);

    /**
     * Add a sprite to the sprites collection.
     * @param s sprite.
     */
    void addToSprites(Sprite s);

    /**
     * Remove a collidable from the game evironment.
     * @param c Collidable
     */
    void removeCollidable(Collidable c);

    /**
     * Remove a sprite from the sprites collection.
     * @param s Sprite
     */
    void removeSprite(Sprite s);
}
