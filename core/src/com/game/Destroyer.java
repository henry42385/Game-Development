package com.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Destroyer extends Ship{

    public Destroyer(Vector2 location, int direction, int player) {
        super(location, direction, 3, player, 1);
    }

    @Override
    public void attack(Vector2 location) {
        this.attack = location;
    }

    @Override
    public ArrayList<Vector2> generateAttacks() {
        ArrayList<Vector2> attacks = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                attacks.add(new Vector2(this.location.x + i, this.location.y + j));
            }
        }
        return attacks;
    }
}
