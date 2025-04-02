package game.core;

import game.exceptions.BoundaryExceededException;
import game.utility.Direction;

public abstract class Controllable extends ObjectWithPosition {
    public Controllable(int x, int y) {
        super(x, y);
    }

    public abstract void move(Direction direction) throws BoundaryExceededException;
    public abstract void fireBullet();
}
