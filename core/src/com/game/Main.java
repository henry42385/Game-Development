package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture img;
	private Rectangle bucket;
	private String currentScreen = "home";
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		img = new Texture("sprites/Water.png");

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		camera.update();
//				camera.translate(-0.08f, 0, 0);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		switch (currentScreen) {
			case "home":
				batch.draw(img, bucket.x, bucket.y, 400, 400);
				if (Gdx.input.isTouched()) {
					Vector3 touchPos = new Vector3();
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(touchPos);
					bucket.x = touchPos.x - 64 / 2;
				}

				if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
				if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

				if (bucket.x < 0) bucket.x = 0;
				if (bucket.x > 800 - 64) bucket.x = 800 - 64;
				break;
			case "game":
				break;
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
