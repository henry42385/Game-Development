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
        camera.setToOrtho(false, 1080, 720);
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < Gdx.graphics.getWidth(); i += 128) {
            for (int j = 0; j < Gdx.graphics.getHeight(); j += 128) {
                batch.draw(img, i, j, 128, 128);
            }
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
        shapeRenderer.rect(510, 330, 130, 50);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Click to start", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();

        if (Gdx.input.isTouched() &&
                Gdx.input.getX() < 640 &&
                Gdx.input.getX() > 510 &&
                Gdx.input.getY() < 380 &&
                Gdx.input.getY() > 330) {
            Main.setCurrentScreen("game");
        }
    }

    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
