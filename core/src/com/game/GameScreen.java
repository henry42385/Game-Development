package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends Screen {
    private Texture spriteSheet;
    private Texture cloud;
    private SpriteBatch batchStatic;
    private SpriteBatch batchDynamic;

    public void create() {
        spriteSheet = new Texture("sprites/TerrainSpritesheetOld.png");
        cloud = new Texture("sprites/Cloud.png");
        batchStatic = new SpriteBatch();
        batchDynamic = new SpriteBatch();
    }

    public void render() {
        DynamicCamera.update();
        batchDynamic.setProjectionMatrix(DynamicCamera.get().combined);
        batchDynamic.begin();

        Vector3 mouseLocation = StaticCamera.get().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        System.out.println("camera: " + mouseLocation.y);
        System.out.println("world: " + Gdx.input.getY());

        batchDynamic.draw(spriteSheet, 100, 100, 200, 200, 0.5f, 0.5f, 1, 0);
        batchDynamic.draw(spriteSheet, 300, 300, 200, 200, 0, 1, 0.5f, 0.5f);
        batchDynamic.draw(spriteSheet, 500, 500, 200, 200, 0.5f, 1, 1, 0.5f);
        batchDynamic.draw(spriteSheet, 700, 300, 200, 200, 0, 0.5f, 0.5f, 0);
        batchDynamic.end();

        StaticCamera.update();
        batchStatic.begin();
        batchStatic.draw(cloud, 450, 450, 200, 200);
        batchStatic.end();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            DynamicCamera.moveLeft();
        } if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            DynamicCamera.moveRight();
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            DynamicCamera.moveUp();
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            DynamicCamera.moveDown();
        } if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            DynamicCamera.zoomOut();
        } if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            DynamicCamera.zoomIn();
        } if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            StaticCamera.reset();
        }
    }

    public void dispose() {
        spriteSheet.dispose();
        batchDynamic.dispose();
        batchStatic.dispose();
    }
}
