package com.game;

import com.badlogic.gdx.math.Vector2;

public class Destroyer extends Ship{

    public Destroyer(Vector2 location, int direction, int player) {
        super(location, direction, 3, player, 1);
    }

    @Override
    public void attack(Vector2 location) {
        this.attack = location;
    }
}
