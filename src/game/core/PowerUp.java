package game.core;

/**
 * Abstract class representing a power-up in the game.
 */
public abstract class PowerUp extends ObjectWithPosition {

    /**
     * Constructs a power-up at the specified position.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public PowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * Applies the power-up effect to the ship.
     *
     * @param ship the ship to apply the effect to
     */
    public abstract void applyEffect(Ship ship);

    /**
     * Moves the power-up down the screen each tick.
     *
     * @param tick the current game tick
     */
    @Override
    public void tick(int tick) {
        y++;
    }
}
