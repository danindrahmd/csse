package game.core;

import game.ui.ObjectGraphic;

public interface SpaceObject {
    int getX();
    int getY();
    void tick(int tick);
    ObjectGraphic render();
}
