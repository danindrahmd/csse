package game;

import game.core.*;
import game.GameModel;
import game.exceptions.BoundaryExceededException;
import game.ui.UI;
import game.utility.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Controller handling the game flow and interactions.
 *
 * Holds references to the UI and the Model, so it can pass information and references back and forth as necessary.
 * Manages changes to the game, which are stored in the Model, and displayed by the UI.
 */
public class GameController {
    private long startTime;
    private UI ui;
    private GameModel model;

    /**
     * Initializes the game controller with the given UI and Model.
     * Stores the ui, model and start time.
     * The start time System.currentTimeMillis() should be stored as a long.
     *
     * @param ui the UI used to draw the Game
     * @param model the model used to maintain game information
     * @provided
     */
    public GameController(UI ui, GameModel model) {
        this.ui = ui;
        this.model = model;
        this.startTime = System.currentTimeMillis(); // Start the timer
    }

    /**
     * Initializes the game controller with the given UI and a new GameModel (taking ui::log as the logger).
     * This constructor should call the other constructor using the "this()" keyword.
     *
     * @param ui the UI used to draw the Game
     * @provided
     */
    public GameController(UI ui) {
        this(ui, new GameModel(ui::log));
    }

    /**
     * Handles player key input.
     */
    public void handlePlayerInput(String key) {
        try {
            Ship ship = model.getShip();
            if (ship == null) return;

            switch (key.toUpperCase()) {
                case "W" -> {
                    ship.move(Direction.UP);
                }
                case "A" -> {
                    ship.move(Direction.LEFT);
                }
                case "S" -> {
                    ship.move(Direction.DOWN);
                }
                case "D" -> {
                    ship.move(Direction.RIGHT);
                }
                case "F" -> {
                    model.fireBullet();
                }
                case "P" -> pauseGame();
            }
        } catch (BoundaryExceededException e) {
            ui.log("Boundary exceeded: " + e.getMessage());
        }
    }

    /**
     * Logs game pause.
     */
    public void pauseGame() {
        ui.log("Game paused");
    }
    
    /**
     * Starts the main game loop.
     *
     * Passes onTick and handlePlayerInput to ui.onStep and ui.onKey respectively.
     * @provided
     */
    public void startGame() {
        // FOR STAGE 0 only, uncomment or remove after
//        model.addObject(new Bullet(2, 14));
        // END STAGE 0 only

        // FOR STAGE 1 only, uncomment or remove after
//        model.addObject(new Bullet(2, 14));
//        model.addObject(new Enemy(2, 0));
        // END STAGE 1 only
        model.addObject(new Ship(GameModel.GAME_WIDTH / 2, GameModel.GAME_HEIGHT - 1, 3));

        ui.onStep(this::onTick);
        // Uncomment in stage 2
        ui.onKey(this::handlePlayerInput); // Pass Callback to UI
    }

    /**
     * Returns the current game model.
     *
     * @return the model
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Renders the current state of the game.
     */
    public void renderGame() {
        ui.render(model.getSpaceObjects());
    }

    /**
     * Uses the provided tick to call and advance the following:
     *      - A call to renderGame() to draw the current state of the game.
     *      - A call to model.updateGame(tick) to advance the game by the given tick.
     *      - A call to model.checkCollisions() to handle game interactions.
     *      - A call to model.spawnObjects() to handle object creation.
     *      - A call to model.levelUp() to check and handle leveling.
     *
     * @param tick the provided tick
     * @provided
     */
    public void onTick(int tick) {
        renderGame(); // Update Visual
        model.updateGame(tick); // Update GameObjects
        model.checkCollisions(); // Check for Collisions
        model.spawnObjects(); // Handles new spawns
        model.levelUp(); // Level up when score threshold is met
    }
}

