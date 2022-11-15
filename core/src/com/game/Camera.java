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
        get().translate(-100 * Gdx.graphics.getDeltaTime(), 0, 0);
    }

    public static void moveRight() {
        get().translate(100 * Gdx.graphics.getDeltaTime(), 0, 0);
    }

    public static void moveUp() {
        get().translate(0, 100 * Gdx.graphics.getDeltaTime(), 0);
    }

    public static void moveDown() {
        get().translate(0, -100 * Gdx.graphics.getDeltaTime(), 0);
    }

    public static void zoomOut() {
        get().zoom = get().zoom * 1.01f;
    }

    public static void zoomIn() {
        get().zoom = get().zoom * 0.99f;
    }

    public static void reset() {
        camera = null;
    }
}
