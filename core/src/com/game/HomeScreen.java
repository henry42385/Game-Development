package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class HomeScreen extends Screen {
    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public void create() {
        img = new Texture("sprites/Water.png");
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        batch.begin();
        for (int i = 0; i < Gdx.graphics.getWidth(); i += 128) {
            for (int j = 0; j < Gdx.graphics.getHeight(); j += 128) {
                batch.draw(img, i, j, 128, 128);
            }
        }
        batch.end();

        shapeRenderer.setColor(0, 0, 0.4f, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect((StaticCamera.get().viewportWidth - 50) / 2, (StaticCamera.get().viewportHeight - 80) / 2, 130, 50);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Click to start", StaticCamera.get().viewportWidth / 2, StaticCamera.get().viewportHeight / 2);
        batch.end();

        Vector3 mouseLocation = StaticCamera.get().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (Gdx.input.isTouched() &&
                mouseLocation.x < StaticCamera.get().viewportWidth / 2 + 200 &&
                mouseLocation.x > StaticCamera.get().viewportWidth / 2 - 200 &&
                mouseLocation.y < StaticCamera.get().viewportHeight / 2 + 200 &&
                mouseLocation.y > StaticCamera.get().viewportHeight / 2 - 200) {
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
