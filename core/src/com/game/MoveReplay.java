package com.game;

import com.badlogic.gdx.math.Vector2;

public class MoveReplay {
    public Vector2 start;
    public Vector2 end;
    public int startDirection;
    public int endDirection;
    public int team;
    public int hp;
    public int spriteID;

    public MoveReplay(Vector2 start, Vector2 end, int startDirection, int endDirection, int team, int hp, int spriteID) {
        this.start = start;
        this.end = end;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
        this.team = team;
        this.hp = hp;
        this.spriteID = spriteID;
    }
}
