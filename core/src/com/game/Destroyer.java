package com.game;

import com.badlogic.gdx.math.Vector2;

public class Destroyer extends Ship{
    public Destroyer(Vector2 location, int direction) {
        super(location, direction);
    }

    @Override
    public void attack(Vector2 location) {
        this.attack = location;
    }
}
