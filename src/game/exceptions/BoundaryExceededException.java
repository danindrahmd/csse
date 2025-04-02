package game.exceptions;

/**
 * Exception thrown when an object moves outside the game boundary.
 */
public class BoundaryExceededException extends Exception {
    public BoundaryExceededException(String direction) {
        super("Cannot move " + direction + ", boundary exceeded.");
    }
}
