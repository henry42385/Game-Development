package com.game;

public abstract class Ship {
    private int x;
    private int y;
    private int direction;
    private String status;

    public Ship(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.status = "move";
    }

    public void move() {

    }

    public abstract boolean attack();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }
}
