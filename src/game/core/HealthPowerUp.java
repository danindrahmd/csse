package game.core;

import game.ui.ObjectGraphic;

public class HealthPowerUp extends PowerUp {
    public HealthPowerUp(int x, int y) {
        super(x, y, ship -> ship.heal(1));
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("H", "assets/health.png");
    }
}