package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends Screen {
    private Texture spriteSheet;
    private SpriteBatch batch;

    public void create() {
        spriteSheet = new Texture("sprites/TerrainSpritesheetOld.png");
        batch = new SpriteBatch();
    }

    public void render() {
        Camera.update();
        batch.setProjectionMatrix(Camera.get().combined);
        batch.begin();
        batch.draw(spriteSheet, 100, 100, 132, 132, 0.5f, 0.5f, 1, 0);
        batch.draw(spriteSheet, 300, 300, 132, 132, 0, 1, 0.5f, 0.5f);
        batch.draw(spriteSheet, 500, 500, 132, 132, 0.5f, 1, 1, 0.5f);
        batch.draw(spriteSheet, 700, 300, 132, 132, 0, 0.5f, 0.5f, 0);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Camera.moveLeft();
        } if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Camera.moveRight();
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            Camera.moveUp();
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            Camera.moveDown();
        } if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Camera.zoomOut();
        } if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            Camera.zoomIn();
        } if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Camera.reset();
        }
    }

    public void dispose() {
        spriteSheet.dispose();
        batch.dispose();
    }
}
