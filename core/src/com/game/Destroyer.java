package com.game;

public class Destroyer extends Ship{
    public Destroyer(int x, int y, int direction) {
        super(x, y, direction);
    }

    @Override
    public boolean attack() {
        return false;
    }
}
