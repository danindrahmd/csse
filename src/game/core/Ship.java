package game.core;

import game.exceptions.BoundaryExceededException;
import game.GameModel;
import game.ui.ObjectGraphic;
import game.utility.Direction;

public class Ship extends Controllable {
    private int health;
    private boolean shielded = false;
    private int shieldDuration = 0;

    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    @Override
    public void move(Direction direction) throws BoundaryExceededException {
        switch (direction) {
            case UP -> { if (y == 0) throw new BoundaryExceededException("Top"); y--; }
            case DOWN -> { if (y == GameModel.GAME_HEIGHT - 1) throw new BoundaryExceededException("Bottom"); y++; }
            case LEFT -> { if (x == 0) throw new BoundaryExceededException("Left"); x--; }
            case RIGHT -> { if (x == GameModel.GAME_WIDTH - 1) throw new BoundaryExceededException("Right"); x++; }
        }
    }
    

    @Override
    public void tick(int tick) {
        if (shielded) {
            shieldDuration--;
            if (shieldDuration <= 0) {
                shielded = false;
            }
        }
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("S", "assets/ship.png");
    }

    public void heal(int amount) {
        health = Math.min(5, health + amount);
    }

    public void takeDamage(int amount) {
        if (!shielded) health -= amount;
    }

    public boolean isShielded() {
        return shielded;
    }

    public int getHealth() {
        return health;
    }

    public static void enableShield(Ship ship) {
        ship.shielded = true;
        ship.shieldDuration = 10;
    }

    public boolean isAlive() {
        return health > 0;
    }
}