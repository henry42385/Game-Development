package com.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MissileShip extends Ship {

    public MissileShip(Vector2 location, int direction, int player) {
        super(location, direction, 3, player, 0);
    }

    @Override
    public void attack(Vector2 location) {

    }

    @Override
    public ArrayList<Vector2> generateAttacks() {
        ArrayList<Vector2> attacks = new ArrayList<>();
        if (direction == 0 || direction == 4) {
            attacks.add(new Vector2(location.x - 2, location.y));
            attacks.add(new Vector2(location.x - 1, location.y));
            attacks.add(new Vector2(location.x + 1, location.y));
            attacks.add(new Vector2(location.x + 2, location.y));
        } else if (direction == 1 || direction == 5) {
            attacks.add(new Vector2(location.x - 2, location.y + 2));
            attacks.add(new Vector2(location.x - 1, location.y + 1));
            attacks.add(new Vector2(location.x + 1, location.y - 1));
            attacks.add(new Vector2(location.x + 2, location.y - 2));
        } else if (direction == 2 || direction == 6) {
            attacks.add(new Vector2(location.x, location.y - 2));
            attacks.add(new Vector2(location.x, location.y - 1));
            attacks.add(new Vector2(location.x, location.y + 1));
            attacks.add(new Vector2(location.x, location.y + 2));
        } else if (direction == 3 || direction == 7) {
            attacks.add(new Vector2(location.x - 2, location.y - 2));
            attacks.add(new Vector2(location.x - 1, location.y - 1));
            attacks.add(new Vector2(location.x + 1, location.y + 1));
            attacks.add(new Vector2(location.x + 2, location.y + 2));
        }
        return attacks;
    }
}
