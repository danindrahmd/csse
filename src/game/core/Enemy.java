package game.core;

import game.ui.ObjectGraphic;

public class Enemy extends DescendingEnemy {
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("E", "assets/enemy.png");
    }
}