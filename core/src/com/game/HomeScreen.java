package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HomeScreen extends Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;

    public void create() {
        img = new Texture("sprites/Water.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
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

        System.out.println(Gdx.graphics.getWidth()/2);
        System.out.println(Gdx.input.getX());
        font.draw(batch, "Click play to start", 540, 360);

//        if (Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//        }

//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            bucket.x += 200 * Gdx.graphics.getDeltaTime();
//
//        if (bucket.x < 0) bucket.x = 0;
//        if (bucket.x > 800 - 64) bucket.x = 800 - 64;



        batch.end();
    }

    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
    }
}
