package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class GameScreen extends Screen {
    private OrthographicCamera camera;
    private Texture spriteSheet;
    private SpriteBatch batch;

    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 720);
        spriteSheet = new Texture("sprites/TerrainSpritesheetOld.png");
        batch = new SpriteBatch();
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(spriteSheet, 100, 100, 132, 132, 0.5f, 0.5f, 1, 0);
        batch.draw(spriteSheet, 300, 300, 132, 132, 0, 1, 0.5f, 0.5f);
        batch.draw(spriteSheet, 500, 500, 132, 132, 0.5f, 1, 1, 0.5f);
        batch.draw(spriteSheet, 700, 300, 132, 132, 0, 0.5f, 0.5f, 0);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-50 * Gdx.graphics.getDeltaTime(), 0, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 50 * Gdx.graphics.getDeltaTime(), 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(50 * Gdx.graphics.getDeltaTime(), 0, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -50 * Gdx.graphics.getDeltaTime(), 0);
        }
    }

    public void dispose() {
        spriteSheet.dispose();
        batch.dispose();
    }
}
