package game.core;

import game.ui.ObjectGraphic;

public class ShieldPowerUp extends PowerUp {
    public ShieldPowerUp(int x, int y) {
        super(x, y, Ship::enableShield);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("D", "assets/shield.png");
    }
}