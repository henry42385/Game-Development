package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends Screen {
    private Texture spriteSheet;
    private Texture cloud;
    private SpriteBatch batchStatic;
    private SpriteBatch batchDynamic;
    private ShapeRenderer shapeRenderer;
    private Map map;
    private BitmapFont font;
    private boolean playerTurn1 = true;

    public void create() {
        spriteSheet = new Texture("sprites/TerrainSpritesheetOld.png");
        cloud = new Texture("sprites/MissileShip.png");
        batchStatic = new SpriteBatch();
        batchDynamic = new SpriteBatch();
        map = new Map("assets/maps/map1.txt");
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    playerTurn1 = !playerTurn1;
                }
                return true;
            }
        });
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderWorld();
        renderHUD();
        updateView();
        System.out.println(playerTurn1);
    }

    private void renderWorld() {
        DynamicCamera.update();
        batchDynamic.setProjectionMatrix(DynamicCamera.get().combined);
        batchDynamic.begin();

//        Vector3 mouseLocation = StaticCamera.get().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (drawMap[y][x] == 'D')
                    batchDynamic.draw(spriteSheet, x * 128 - 256, (map.getHeight() - y - 1) * 128 - 256, 128, 128, 0, 0.5f, 0.5f, 0);
                else if (drawMap[y][x] == 'S')
                    batchDynamic.draw(spriteSheet, x * 128 - 256, (map.getHeight() - y - 1) * 128 - 256, 128, 128, 0.5f, 0.5f, 1, 0);
                else if (drawMap[y][x] == 'L')
                    batchDynamic.draw(spriteSheet, x * 128 - 256, (map.getHeight() - y - 1) * 128 - 256, 128, 128, 0, 1, 0.5f, 0.5f);
                else if (drawMap[y][x] == 'M')
                    batchDynamic.draw(spriteSheet, x * 128 - 256, (map.getHeight() - y - 1) * 128 - 256, 128, 128, 0.5f, 1, 1, 0.5f);
            }
        }

        batchDynamic.end();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.setProjectionMatrix(DynamicCamera.get().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        for (int i = 1; i < map.getHeight() - 2; i++) {
            shapeRenderer.line(0, -128 + i * 128, map.getWidth() * 128 - 512, -128 + i * 128);
        }
        for (int i = 1; i < map.getWidth() - 2; i++) {
            shapeRenderer.line(-128 + i * 128, 0, -128 + i * 128, map.getHeight() * 128 - 512);
        }
        shapeRenderer.end();
    }

    private void renderHUD() {
        StaticCamera.update();
        batchStatic.begin();
        batchStatic.draw(cloud, 450, 450, 200, 200);
        font.getData().setScale(2);
        if (playerTurn1) {
            font.draw(batchStatic, "Player 2",1300, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 1",100, 750);
        } else {
            font.draw(batchStatic, "Player 1",100, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 2",1300, 750);
        } font.setColor(1, 1, 1, 1);
        batchStatic.end();
    }

    private void updateView() {
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
        shapeRenderer.dispose();
        font.dispose();
    }
}
