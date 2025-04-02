package game.ui.gui;

import game.core.SpaceObject;
import game.GameModel;
import game.ui.KeyHandler;
import game.ui.Tickable;
import game.ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GUI implementation of the UI interface.
 */
public class GUI implements UI {
    private static final int TICK_SPEED = 100;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 650;

    // GAME_HEIGHT + 1 required for bottom row (row 19) to render correctly
    private Canvas canvas = new Canvas(GameModel.GAME_WIDTH, GameModel.GAME_HEIGHT + 1);
    private final Log log = new Log();
    private final Stats stats = new Stats();

    private final List<Tickable> tickables = new ArrayList<>();
    private final List<KeyHandler> handlers = new ArrayList<>();

    private Timer gameTimer;
    private int tick = 0;

    /**
     * Starts the GUI.
     */
    @Override
    public void start() {
        JFrame frame = new JFrame();
        frame.setTitle("Space Shooter");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Sets default window size


        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(canvas)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(log)
                                        .addComponent(stats))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(canvas)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(log)
                                .addComponent(stats))
        );
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                int width = (int) Math.floor(size.getWidth());
                int height = (int) Math.floor(size.getHeight());
                canvas.setGraphicalDimensions((int) (width * 0.5), height);
                log.setGraphicalDimensions((int) (width * 0.5), (int) (height * 0.5));
                stats.setGraphicalDimensions((int) (width * 0.5), (int) (height * 0.5));
            }
        });
        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                for (KeyHandler handler : handlers) {
                    handler.onPress(e.getKeyChar() + "");
                }
                canvas.updateUI();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        gameTimer = new Timer(TICK_SPEED, (e) -> {
            for (Tickable tickable : tickables) {
                tickable.tick(tick++);
                canvas.updateUI();
            }
        });
        gameTimer.start();
    }

    /**
     * Alternates between paused and unpaused state.
     */
    @Override
    public void pause() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
        } else {
            gameTimer.start();
        }
    }

    /**
     * Pauses the game
     */
    @Override
    public void stop() {
        gameTimer.start();
    }

    /**
     * Stores the provided tickable.
     * @param tickable the provided tickable
     */
    @Override
    public void onStep(Tickable tickable) {
        tickables.add(tickable);
    }

    /**
     * Stores the provided KeyHandler.
     * @param key the provided KeyHandler
     */
    @Override
    public void onKey(KeyHandler key) {
        handlers.add(key);
    }

    /**
     * Renders the provided objects, and refreshes the UI.
     *
     * @param objects the list of SpaceObjects to be rendered.
     */
    @Override
    public void render(List<SpaceObject> objects) {
        canvas.updateSpaceObjects(objects);
        canvas.updateUI();
    }

    /**
     * Log the provided message.
     *
     * @param message the provided message
     */
    @Override
    public void log(String message) {
        log.log(message);
        log.updateUI();
    }

    /**
     * Sets the stats to the provided label and value.
     *
     * @param label the provided label
     * @param value the provided value
     */
    @Override
    public void setStat(String label, String value) {
        if (!stats.isRegistered(label)) {
            stats.registerStat(label);
        }
        stats.setStat(label, value);
        stats.updateUI();
    }
}

