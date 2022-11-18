package com.game;

import com.badlogic.gdx.math.Vector2;

public class MoveReplay {
    private Vector2 start;
    private Vector2 end;
    private int direction;
    private int team;

    public MoveReplay(Vector2 start, Vector2 end, int direction, int team) {
        this.start = start;
        this.end = end;
        this.direction = direction;
        this.team = team;
    }
}
