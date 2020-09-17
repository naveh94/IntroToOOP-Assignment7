package animations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import blockcreator.DrawImage;
import enemies.AlienGroup;
import gameobjects.AnimationRunner;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Collidable;
import gameobjects.Counter;
import gameobjects.DeathZone;
import gameobjects.GameEnvironment;
import gameobjects.SpaceShip;
import gameobjects.Sprite;
import gameobjects.SpriteCollection;
import gameobjects.StopCondition;
import geometry.Point;
import geometry.Velocity;
import hitlisteners.BallRemover;
import hitlisteners.BlockRemover;
import hitlisteners.PaddleHealthListener;
import hitlisteners.ScoreTrackingListener;
import indicators.BlocksLeftIndicator;
import indicators.LivesIndicator;
import indicators.NameIndicator;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import readers.Loader;

/**
 * A collection of all the sprites and collidable on the current game.
 * @author Naveh Marchoom
 */
public class SpaceLevel implements GameLevel {

    // Game Objects Collection:
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment collidables = new GameEnvironment();
    private Set<Ball> shots = new HashSet<>();
    private LevelInformation level;
    private AlienGroup swarm;
    private List<Block> shields;

    // Mechanics:
    private GUI gui;
    private AnimationRunner runner;
    private KeyboardSensor ks;
    private StopCondition stop;
    private SpaceShip paddle;

    // Counters:
    private Counter remainingBlocks = new Counter(0);
    private Counter remainingBalls = new Counter(0);
    private Counter score;
    private Counter lives;
    private int levelNum = 0;
    private int gunCooldown = 0;
    private int aliensCooldown = 0;

    /**
     * Create a new GameLevel object and set the AnimationRunner,
     * the GUI and the keyboard sensor.
     * @param runner AnimationRunner
     * @param gui GUI
     * @param ks KeyboardSensor
     */
    public SpaceLevel(AnimationRunner runner, GUI gui, KeyboardSensor ks) {
        this.runner = runner;
        this.gui = gui;
        this.ks = ks;
    }

    /**
     * Initialize a new level, setting the score and the lives counters, and creating the level
     * according the the LevelInfo given.
     * @param levelInfo LevelInformation
     * @param scoreCounter Counter
     * @param livesCounter Counter
     */
    public void initialize(LevelInformation levelInfo, Counter scoreCounter, Counter livesCounter) {

        DrawSurface board = this.gui.getDrawSurface();

        this.levelNum++;

        // Set the level:
        this.level = levelInfo;

        // Add the background to the sprites:
        this.sprites.addSprite(this.level.getBackground());

        // Set the lives and score Counters:
        this.lives = livesCounter;
        this.score = scoreCounter;

        // Set the Block counter:
        this.remainingBlocks.increase(this.level.numberOfBlocksToRemove());

        // Create the Death Zone:
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls, this.shots);
        DeathZone dz = new DeathZone(new Point(-1, 0), board.getWidth() + 2, 22); // Top Death Zone
        dz.addHitListener(ballRemover);
        dz.addToGame(this);
        dz = new DeathZone(new Point(-1, board.getHeight()), board.getWidth() + 2, 22); // Bottom Death Zone
        dz.addHitListener(ballRemover);
        dz.addToGameAllies(this);

        // Set the block remover and score tracker:
        BlockRemover alienRemover = new BlockRemover(this, this.remainingBlocks);
        BlockRemover shieldRemover = new BlockRemover(this, new Counter(0));
        ScoreTrackingListener sTL = new ScoreTrackingListener(this.score);

        // Add the Shields:
        this.shields = new ArrayList<>();
        Random rand = new Random();
        for (int j = 100; j < 600; j += 230) {
            for (int i = 0; i < 28; i++) {
                for (int k = 0; k < 3; k++) {
                    Block b = new Block(new Point(j + i * 5, 500 + k * 5), 5, 5);
                    b.setHitPoints(1);
                    String filepath = "block_images/shield/" + (rand.nextInt(20) + 1) + ".gif";
                    b.setFill(new DrawImage(Loader.loadImage(filepath)));
                    b.addHitListener(shieldRemover);
                    b.addHitListener(ballRemover);
                    b.addToGame(this);
                    b.addToGameAllies(this);
                    this.shields.add(b);
                }
            }
        }
        // Add the Aliens:
        this.swarm = new AlienGroup(36, 10, 1 + this.levelNum * 0.2);
        this.swarm.addListener(alienRemover);
        this.swarm.addListener(ballRemover);
        this.swarm.addListener(sTL);
        this.swarm.addToGame(this);

        // Create the indicators:
        LivesIndicator livesPanel = new LivesIndicator(this.lives);
        this.sprites.addSprite(livesPanel);
        ScoreIndicator scorePanel = new ScoreIndicator(this.score);
        this.sprites.addSprite(scorePanel);
        NameIndicator namePanel = new NameIndicator("Invasion number " + this.levelNum);
        this.sprites.addSprite(namePanel);
        BlocksLeftIndicator blocksPanel = new BlocksLeftIndicator(this.remainingBlocks);
        this.sprites.addSprite(blocksPanel);

        this.stop = new StopCondition();
    }

    /**
     * Run the level until the player win, or until the player is out of lives.
     */
    public void run() {
        while (this.lives.getValue() > 0 && this.remainingBlocks.getValue() > 0) {
            this.oneTurn();
        }
        for (Block b : this.shields) {
            b.removeFromGame(this);
        }
    }

    /**
     * Run one turn of the level, until player win or dies.
     */
    public void oneTurn() {
        PaddleHealthListener phl = new PaddleHealthListener(this.lives, this.stop);
        this.paddle = this.createPaddle();
        this.paddle.addHitListener(phl);
        this.swarm.resetPosition();
        this.swarm.checkBoundaries();
        this.stop.setFalse();

        // Run the countdown:
        this.runner.run(new CountdownAnimation(2.0, 3, this.sprites, this.runner.getFps()));

        // Run the turn:
        this.runner.run(this);

        for (Ball b : this.shots) {
            b.removeFromGame(this);
        }
        shots.clear();
        this.remainingBalls.decrease(this.remainingBalls.getValue());
        this.paddle.removeFromGame(this);
    }

    /**
     * Add a collidable to the game environment.
     * @param c collidable
     */
    public void addToCollidables(Collidable c) {
        this.collidables.addCollidable(c);
    }

    /**
     * Add a sprite to the sprites collection.
     * @param s sprite.
     */
    public void addToSprites(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Remove a collidable from the game evironment.
     * @param c Collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.removeCollidable(c);
    }

    /**
     * Remove a sprite from the sprites collection.
     * @param s Sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doOneFrame(DrawSurface board, double dt) {
        // Draw all the sprites and notify them time has passed:
        this.sprites.drawOnAll(board);
        this.sprites.notifyAllTimePassed(dt);

        // If all blocks were destroyed, add 100 points.
        if (remainingBlocks.getValue() == 0) {
            this.score.increase(1000);
            this.stop.setTrue();
        }

        if (this.swarm.getBottomY() > 490) {
            this.lives.decrease(1);
            this.stop.setTrue();
        }

        if (gunCooldown == 0) {
            if (ks.isPressed(KeyboardSensor.SPACE_KEY)) {
                Ball b = this.paddle.shoot(Velocity.fromAngleAndSpeed(Velocity.straightUpAngle(), 440));
                this.shots.add(b);
                b.setEnvironment(this.collidables);
                b.addToGame(this);
                gunCooldown++;
            }
        } else {
            if (gunCooldown >= 21) {
                gunCooldown = 0;
            } else {
                gunCooldown++;
            }
        }
        aliensCooldown++;
        if (aliensCooldown % 30 == 0) {
            Ball b = this.swarm.shoot();
            this.shots.add(b);
            b.setEnvironment(this.collidables);
            b.addToGame(this);
            aliensCooldown = 0;
        }
        this.swarm.moveOneStep();

        // If p is pushed, run the pause screen:
        if (ks.isPressed("p")) {
            Animation ani = new PauseScreen(this.sprites);
            this.runner.run(new KeyPressStoppableAnimation(this.ks, KeyboardSensor.SPACE_KEY, ani));
            this.runner.run(new CountdownAnimation(1, 2, this.sprites, this.runner.getFps()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldStop() {
        return this.stop.getValue();
    }

    /**
     * Create the paddle. Return the paddle so it can be removed when player dies.
     * @return Paddle
     */
    public SpaceShip createPaddle() {
        // Create the paddle:
        SpaceShip p = new SpaceShip(this.ks, new Point(365, 550));
        p.addToGame(this);
        return p;
    }
}
