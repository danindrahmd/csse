package game;

import game.ui.gui.GUI;
import game.ui.UI;

/**
 * Entry point for the Space Shooter game.
 * @provided
 */
public class Main {
    /**
     * The game entry point.
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        UI ui = new GUI();
        ui.start();

        ui.log("=====================================");
        ui.log("       Welcome to Space Shooter!     ");
        ui.log("=====================================");
        ui.log("Controls: ");
        ui.log("  W - Move Up");
        ui.log("  A - Move Left");
        ui.log("  S - Move Down");
        ui.log("  D - Move Right");
        ui.log("  F - Fire Bullets");
        ui.log("  P - Pause Game");
        ui.log("\nPress Enter to start the game...");

        // Start the game
        GameController gameController = new GameController(ui);
        gameController.startGame();
    }
}
