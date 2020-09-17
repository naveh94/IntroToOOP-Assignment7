import java.io.File;
import java.io.IOException;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gameobjects.AnimationRunner;
import gameobjects.GameFlow;
import gameobjects.HighScoresTable;
import gameobjects.Task;
import pregame.MenuAnimation;

/**
 * The main class running the game.
 * @author Naveh Marchoom
 *
 */
public class Ass7Game {

    /**
     * Run the game.
     * @param args unused.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Space Invaders", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui, 60);
        KeyboardSensor ks = gui.getKeyboardSensor();
        HighScoresTable score = initializeSaveFile("./score.ser");
        GameFlow game = new GameFlow(gui, runner, ks, score);
        runMenu(ks, runner, game, score);
    }

    /**
     * Initialize the save file, if exist load it up, else will create a new one.
     * @param filepath String
     * @return HighScoresTable
     */
    private static HighScoresTable initializeSaveFile(String filepath) {
        HighScoresTable hst = new HighScoresTable(14);
        File saveFile = new File(filepath);
        if (saveFile.exists()) {
            try {
                hst.load(saveFile);
            } catch (IOException e) {
                System.out.println("Error loading the save file.");
                e.printStackTrace();
            }
        } else {
            try {
                hst.save(saveFile);
            } catch (IOException e) {
                System.out.println("Error creating a new save file.");
                e.printStackTrace();
            }
        }
        return hst;
    }

    /**
     * Run the main menu for the game.
     * @param ks KeyboardSensor
     * @param runner AnimationRunner
     * @param game GameFlow
     * @param scores HighScoresTable
     */
    private static void runMenu(KeyboardSensor ks, AnimationRunner runner, GameFlow game,
            HighScoresTable scores) {
        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };

        Task<Void> highScores = new Task<Void>() {
            @Override
            public Void run() {
                runner.run(new KeyPressStoppableAnimation(ks, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(scores)));
                return null;
            }
        };

        Task<Void> spaceInvaders = new Task<Void>() {
            @Override
            public Void run() {
                game.runSpace();
                highScores.run();
                return null;
            }
        };

        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>("Main Menu", ks, runner);
        menu.addSelection("s", "tart Game", spaceInvaders);
        menu.addSelection("h", "igh Score", highScores);
        menu.addSelection("q", "uit Game", quit);

        while (true) {
            runner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.resetStop();
        }
    }
}
