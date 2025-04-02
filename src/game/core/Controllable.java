package game.core;

import game.exceptions.BoundaryExceededException;
import game.utility.Direction;

/**
 * Represents a controllable object in the game.
 */
public abstract class Controllable extends ObjectWithPosition {

    /**
     * Constructs a controllable object at the given position.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Controllable(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the controllable object in the specified direction.
     *
     * @param direction the direction to move
     * @throws BoundaryExceededException if movement is outside the game bounds
     */
    public abstract void move(Direction direction) throws BoundaryExceededException;
}
