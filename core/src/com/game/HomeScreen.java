package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HomeScreen extends Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public void create() {
        img = new Texture("sprites/Water.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        camera.update();
//				camera.translate(-0.08f, 0, 0);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < Gdx.graphics.getWidth(); i += 128) {
            for (int j = 0; j < Gdx.graphics.getHeight(); j += 128) {
                batch.draw(img, i, j, 128, 128);
            }
        }

        if (Gdx.input.isTouched() &&
                Gdx.input.getX() < 600 &&
        Gdx.input.getX() > 460 &&
        Gdx.input.getY() < 390 &&
        Gdx.input.getY() > 340) {
            Main.setCurrentScreen("game");
        }

//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            bucket.x += 200 * Gdx.graphics.getDeltaTime();
//
//        if (bucket.x < 0) bucket.x = 0;
//        if (bucket.x > 800 - 64) bucket.x = 800 - 64;



        batch.end();

        shapeRenderer.setColor(0, 0, 0.4f, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(460, 340, 140, 50);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Click to start", 350, 250);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
