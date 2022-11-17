package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MouseHandler {
    public static Vector3 screen = new Vector3();
    public static Vector3 world = new Vector3();
    public static Vector2 grid = new Vector2();

    public static void update() {
        screen.x = Gdx.input.getX();
        screen.y = Gdx.input.getY();
        System.out.println(grid.y);
        world = DynamicCamera.get().unproject(screen);


        grid.x = (int)(world.x / 128);
        grid.y = (int)(world.y / 128);
    }
}
