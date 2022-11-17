package com.game;

import com.badlogic.gdx.math.Vector2;

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

    public void move(Vector2 newLocation ,int directionIndex) {
        this.x = (int)newLocation.x;
        this.y = (int)newLocation.y;
        this.direction += directionIndex;
        if (this.direction < 0)
            this.direction += 8;
        if (this.direction >= 8)
            this.direction -= 8;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vector2[] generateMoves() {
        Vector2[] moves = new Vector2[3];
        switch (direction) {
            case 0:
                moves[0] = new Vector2(x - 1, y + 1);
                moves[1] = new Vector2(x, y + 1);
                moves[2] = new Vector2(x + 1, y + 1);
                break;
            case 1:
                moves[0] = new Vector2(x, y + 1);
                moves[1] = new Vector2(x + 1, y + 1);
                moves[2] = new Vector2(x + 1, y);
                break;
            case 2:
                moves[0] = new Vector2(x + 1, y + 1);
                moves[1] = new Vector2(x + 1, y);
                moves[2] = new Vector2(x + 1, y - 1);
                break;
            case 3:
                moves[0] = new Vector2(x + 1, y);
                moves[1] = new Vector2(x + 1, y - 1);
                moves[2] = new Vector2(x, y - 1);
                break;
            case 4:
                moves[0] = new Vector2(x + 1, y - 1);
                moves[1] = new Vector2(x, y - 1);
                moves[2] = new Vector2(x - 1, y - 1);
                break;
            case 5:
                moves[0] = new Vector2(x, y - 1);
                moves[1] = new Vector2(x - 1, y - 1);
                moves[2] = new Vector2(x - 1, y);
                break;
            case 6:
                moves[0] = new Vector2(x - 1, y - 1);
                moves[1] = new Vector2(x - 1, y);
                moves[2] = new Vector2(x - 1, y + 1);
                break;
            case 7:
                moves[0] = new Vector2(x - 1, y);
                moves[1] = new Vector2(x - 1, y + 1);
                moves[2] = new Vector2(x, y + 1);
        } return moves;
    }
}
