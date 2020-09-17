package gameobjects;

import java.io.IOException;
import animations.Animation;
import animations.GameLevel;
import animations.KeyPressStoppableAnimation;
import animations.GameOver;
import animations.SpaceLevel;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import levels.SpaceInvadersLevel;
/**
 * GameFlow object that runs several levels.
 * @author Naveh Marchoom
 */
public class GameFlow {

    private GUI gui;
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private HighScoresTable scores;

    private Counter lives = new Counter(7);
    private Counter score = new Counter(0);

    /**
     * Construct a new GameFlow object with given variables.
     * @param ar AnimationRunner
     * @param ks KeyboardSensor
     * @param hst HighScoreTable
     * @param g GUI
     */
    public GameFlow(GUI g, AnimationRunner ar, KeyboardSensor ks, HighScoresTable hst) {
        this.gui = g;
        this.ar = ar;
        this.ks = ks;
        this.scores = hst;
    }

    /**
     * Construct a new GameFlow object without giving variables.
     */
    public GameFlow() {
        this.gui = new GUI("Arcanoid", 800, 600);
        this.ar = new AnimationRunner(this.gui, 60);
        this.ks = this.gui.getKeyboardSensor();
    }

    /**
     * Rune Space Invaders level.
     */
    public void runSpace() {
        GameLevel game = new SpaceLevel(this.ar, this.gui, this.ks);
        DialogManager dm = this.gui.getDialogManager();
        Animation ani = null;
        this.score.reset(0);
        this.lives.reset(3);
        LevelInformation level = new SpaceInvadersLevel();

        // Initilize the level and run it:

        while (lives.getValue() > 0) {
            game.initialize(level, score, lives);
            game.run();
        }
        ani = new GameOver(this.score);
        this.ar.run(new KeyPressStoppableAnimation(this.ks, KeyboardSensor.SPACE_KEY, ani));
        // Saves the player's score:
        String name = dm.showQuestionDialog("Name", "What is your name?", System.getProperty("user.name"));
        this.scores.add(new ScoreInfo(name, this.score.getValue()));
        try {
            scores.save(new java.io.File("./score.ser"));
        } catch (IOException e) {
            System.out.println("Error saving the score file.");
            e.printStackTrace();
        }
    }
}
