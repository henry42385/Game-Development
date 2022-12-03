package com.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public abstract class Ship {
    protected Vector2 location;
    private Vector2 afterimage;
    protected Vector2 attack;
    protected int direction;
    private int afterimageDirection;
    private String status;
    private int maxHp;
    private int currentHp;
    public int player;
    public int spriteID;

    public Ship(Vector2 location, int direction, int maxHp, int player, int spriteID) {
        this.location = location;
        this.direction = direction;
        this.status = "move";
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.player = player;
        this.spriteID = spriteID;
    }

    public void move(Vector2 newLocation, int moveDirection) {
        this.afterimage = new Vector2(this.location.x, this.location.y);
        this.afterimageDirection = this.direction;
        this.location = newLocation;
        this.direction += moveDirection;
        if (this.direction < 0)
            this.direction += 8;
        if (this.direction >= 8)
            this.direction -= 8;
    }

    public abstract void attack(Vector2 location);

    public abstract ArrayList<Vector2> generateAttacks();

    public Vector2 getLocation() {
        return location;
    }

    public Vector2 getAfterimage() {
        return afterimage;
    }

    public Vector2 getAttack() {
        return attack;
    }

    public int getDirection() {
        return direction;
    }

    public int getAfterimageDirection() {
        return afterimageDirection;
    }

    public int getHp() {
        return currentHp;
    }

    public void takeDamage() {
        this.currentHp--;
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
                moves[0] = new Vector2(location.x - 1, location.y + 1);
                moves[1] = new Vector2(location.x, location.y + 1);
                moves[2] = new Vector2(location.x + 1, location.y + 1);
                break;
            case 1:
                moves[0] = new Vector2(location.x, location.y + 1);
                moves[1] = new Vector2(location.x + 1, location.y + 1);
                moves[2] = new Vector2(location.x + 1, location.y);
                break;
            case 2:
                moves[0] = new Vector2(location.x + 1, location.y + 1);
                moves[1] = new Vector2(location.x + 1, location.y);
                moves[2] = new Vector2(location.x + 1, location.y - 1);
                break;
            case 3:
                moves[0] = new Vector2(location.x + 1, location.y);
                moves[1] = new Vector2(location.x + 1, location.y - 1);
                moves[2] = new Vector2(location.x, location.y - 1);
                break;
            case 4:
                moves[0] = new Vector2(location.x + 1, location.y - 1);
                moves[1] = new Vector2(location.x, location.y - 1);
                moves[2] = new Vector2(location.x - 1, location.y - 1);
                break;
            case 5:
                moves[0] = new Vector2(location.x, location.y - 1);
                moves[1] = new Vector2(location.x - 1, location.y - 1);
                moves[2] = new Vector2(location.x - 1, location.y);
                break;
            case 6:
                moves[0] = new Vector2(location.x - 1, location.y - 1);
                moves[1] = new Vector2(location.x - 1, location.y);
                moves[2] = new Vector2(location.x - 1, location.y + 1);
                break;
            case 7:
                moves[0] = new Vector2(location.x - 1, location.y);
                moves[1] = new Vector2(location.x - 1, location.y + 1);
                moves[2] = new Vector2(location.x, location.y + 1);
        } return moves;
    }

    public void play() {
//        createReplay();
        this.afterimage = null;
        this.attack = null;
        this.status = "move";
    }
}
