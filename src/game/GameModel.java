package game;


import game.core.*;
import game.utility.Logger;
import game.core.SpaceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game information and state. Stores and manipulates the game state.
 */
public class GameModel {
    public static final int GAME_HEIGHT = 20;
    public static final int GAME_WIDTH = 10;
    public static final int START_SPAWN_RATE = 2;
    public static final int SPAWN_RATE_INCREASE = 5;
    public static final int START_LEVEL = 1;
    public static final int SCORE_THRESHOLD = 100;
    public static final int ASTEROID_DAMAGE = 10;
    public static final int ENEMY_DAMAGE = 20;
    public static final double ENEMY_SPAWN_RATE = 0.5;
    public static final double POWER_UP_SPAWN_RATE = 0.25;

    private final Random random = new Random();
    private final List<SpaceObject> spaceObjects;
    private final Logger logger;
    private int score = 0;
    private int level = START_LEVEL;
    private int spawnRate = START_SPAWN_RATE;
    private Ship ship;
    private boolean gameOver = false;

    /**
     * Constructs the game model.
     * @param logger logger instance to output game events
     */
    public GameModel(Logger logger) {
        this.spaceObjects = new ArrayList<>();
        this.logger = logger;
    }

    /**
     * @return logger instance
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return current player ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Adds a space object to the model.
     * @param object space object to add
     */
    public void addObject(SpaceObject object) {
        if (object instanceof Ship s) {
            ship = s;
        }
        spaceObjects.add(object);
        logger.log("Added object at (" + object.getX() + ", " + object.getY() + ")");
    }

    /**
     * @return list of current space objects
     */
    public List<SpaceObject> getSpaceObjects() {
        return new ArrayList<>(spaceObjects);
    }

    /**
     * Updates game state.
     * @param tick current tick number
     */
    public void updateGame(int tick) {
        if (gameOver || ship == null) {
            return;
        }

        List<SpaceObject> toRemove = new ArrayList<>();
        for (SpaceObject obj : spaceObjects) {
            obj.tick(tick);
            if (obj.getY() < 0 || obj.getY() >= GAME_HEIGHT) {
                toRemove.add(obj);
            }
        }
        spaceObjects.removeAll(toRemove);

        if (!ship.isAlive()) {
            gameOver = true;
            logger.log("Game Over!");
        }
    }

    /**
     * Checks for object collisions.
     */
    public void checkCollisions() {
        List<SpaceObject> toRemove = new ArrayList<>();
        for (SpaceObject obj1 : spaceObjects) {
            for (SpaceObject obj2 : spaceObjects) {
                if (obj1 == obj2) continue;
                if (obj1.getX() == obj2.getX() && obj1.getY() == obj2.getY()) {
                    boolean bulletEnemy = (obj1 instanceof Bullet && obj2 instanceof Enemy)
                            || (obj1 instanceof Enemy && obj2 instanceof Bullet);
                    if (bulletEnemy) {
                        toRemove.add(obj1);
                        toRemove.add(obj2);
                        score += 10;
                        logger.log("Enemy destroyed!");
                    }
                    boolean powerUpShip = (obj1 instanceof PowerUp && obj2 == ship)
                            || (obj2 instanceof PowerUp && obj1 == ship);
                    if (powerUpShip) {
                        PowerUp powerUp = (PowerUp) (obj1 instanceof PowerUp ? obj1 : obj2);
                        powerUp.applyEffect(ship);
                        toRemove.add(powerUp);
                        logger.log("Power-up applied!");
                    }
                    if ((obj1 instanceof Asteroid && obj2 == ship) || (obj2 instanceof Asteroid && obj1 == ship)) {
                        ship.takeDamage(ASTEROID_DAMAGE);
                        logger.log("Asteroid hit the ship!");
                        toRemove.add(obj1 instanceof Asteroid ? obj1 : obj2);
                    }
                    if ((obj1 instanceof Enemy && obj2 == ship) || (obj2 instanceof Enemy && obj1 == ship)) {
                        ship.takeDamage(ENEMY_DAMAGE);
                        logger.log("Enemy hit the ship!");
                        toRemove.add(obj1 instanceof Enemy ? obj1 : obj2);
                    }
                }
            }
        }
        spaceObjects.removeAll(toRemove);
    }

    /**
     * Spawns new objects randomly.
     */
    public void spawnObjects() {
        if (gameOver) {
            return;
        }
        if (random.nextInt(100) < spawnRate) {
            int x = random.nextInt(GAME_WIDTH);
            double chance = random.nextDouble();
            if (chance < 0.25) {
                if (chance < 0.125) {
                    addObject(new HealthPowerUp(x, 0));
                } else {
                    addObject(new ShieldPowerUp(x, 0));
                }
            } else if (chance < 0.75) {
                addObject(new Enemy(x, 0));
            } else {
                addObject(new Asteroid(x, 0));
            }
        }
    }

    /**
     * Advances level if score threshold is reached.
     */
    public void levelUp() {
        if (score >= SCORE_THRESHOLD * level) {
            level++;
            spawnRate = Math.min(100, spawnRate + SPAWN_RATE_INCREASE);
            logger.log("Level Up! Now at level " + level);
        }
    }

    /**
     * Fires a bullet from the ship.
     */
    public void fireBullet() {
        if (ship != null) {
            Bullet b = new Bullet(ship.getX(), ship.getY() - 1);
            addObject(b);
            logger.log("Bullet fired!");
        }
    }

    /**
     * @return whether the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @return current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the seed of the Random instance created in the constructor using .setSeed().
     *
     * This method should NEVER be called.
     *
     * @param seed to be set for the Random instance
     * @provided
     */
    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }
}
