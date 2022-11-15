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
    private Map map;

    public void create() {
        spriteSheet = new Texture("sprites/TerrainSpritesheetOld.png");
        cloud = new Texture("sprites/MissileShip.png");
        batchStatic = new SpriteBatch();
        batchDynamic = new SpriteBatch();
        map = new Map("assets/maps/map1.txt");
    }

    public void render() {
        DynamicCamera.update();
        batchDynamic.setProjectionMatrix(DynamicCamera.get().combined);
        batchDynamic.begin();

        Vector3 mouseLocation = StaticCamera.get().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                System.out.println("draw");
                if (drawMap[y][x] == 'D')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 0.5f, 0.5f, 0);
                else if (drawMap[y][x] == 'S')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 0.5f, 1, 0);
                else if (drawMap[y][x] == 'L')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 1, 0.5f, 0.5f);
                else if (drawMap[y][x] == 'M')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 1, 1, 0.5f);
            }
        }

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
