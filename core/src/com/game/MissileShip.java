package com.game;

import com.badlogic.gdx.math.Vector2;

public class MissileShip extends Ship {

    public MissileShip(Vector2 location, int direction, int player) {
        super(location, direction, 3, player, 0);
    }

    @Override
    public void attack(Vector2 location) {

    }
}
