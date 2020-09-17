package gameobjects;

import java.io.Serializable;
/**
 * ScoreInfo object.
 * @author Naveh Marchoom
 *
 */
public class ScoreInfo implements Serializable {

    private String name;
    private int score;

    /**
     * Creates a new scoreinfo.
     * @param name String
     * @param score int
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Get the name attached to the score.
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score.
     * @return int
     */
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ScoreInfo{name='" + name + "', score=" + score + "}";
    }
}
