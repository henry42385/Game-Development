package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
    private static OrthographicCamera camera;
    private int width = 1080;
    private int height = 720;

    public static OrthographicCamera get() {
        if (Camera.camera == null) {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 1080, 720);
        } return camera;
    }

    public static void update() {
        get().update();
    }

    public static void moveLeft() {
        get().translate(-50 * Gdx.graphics.getDeltaTime(), 0, 0);
    }

    public static void moveRight() {
        get().translate(50 * Gdx.graphics.getDeltaTime(), 0, 0);
    }

    public static void moveUp() {
        get().translate(0, 50 * Gdx.graphics.getDeltaTime(), 0);
    }

    public static void moveDown() {
        get().translate(0, -50 * Gdx.graphics.getDeltaTime(), 0);
    }

    public static void zoomOut() {
        get().setToOrtho(false, get().viewportWidth * 1.01f, get().viewportHeight * 1.01f);
    }

    public static void zoomIn() {
        get().setToOrtho(false, get().viewportWidth * 0.99f, get().viewportHeight * 0.99f);
    }

    public static void reset() {
        camera = null;
    }
}
