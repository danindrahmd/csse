package game.core;

public abstract class PowerUp extends ObjectWithPosition {
    protected PowerUpEffect effect;

    public PowerUp(int x, int y, PowerUpEffect effect) {
        super(x, y);
        this.effect = effect;
    }

    public void applyEffect(Ship ship) {
        effect.applyEffect(ship);
    }

    @Override
    public void tick(int tick) {
        y++;
    }
}