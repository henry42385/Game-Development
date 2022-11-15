package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class StaticCamera {
    private static OrthographicCamera camera;
    private int width = 1080;
    private int height = 720;

    public static OrthographicCamera get() {
        if (StaticCamera.camera == null) {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } return camera;
    }

    public static void update() {
        get().update();
    }

    public static void reset() {
        camera = null;
    }
}
