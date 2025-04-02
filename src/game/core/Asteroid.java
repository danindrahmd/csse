package game.core;

import game.ui.ObjectGraphic;

public class Asteroid extends ObjectWithPosition {
    public Asteroid(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(int tick) {
        y++; // Move down
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("A", "assets/asteroid.png");
    }
}