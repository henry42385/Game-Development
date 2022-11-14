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


	private String currentScreen = "home";
	private Screen screen;
	
	@Override
	public void create () {
		screen = new HomeScreen();
		screen.create();


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		screen.render();
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			setCurrentScreen("game");
	}
	
	@Override
	public void dispose () {

	}

	private void setCurrentScreen(String newScreen) {
		screen.dispose();
		switch (newScreen) {
			case "home":
				screen = new HomeScreen();
				break;
			case "game":
				screen = new GameScreen();
				break;
		}
		screen.create();
		currentScreen = newScreen;
	}
}
