package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	private static Screen screen;
	
	@Override
	public void create () {
		screen = new HomeScreen();
		screen.create();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		screen.render();
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}

	public static void setCurrentScreen(String newScreen) {
		screen.dispose();
		switch (newScreen) {
			case "home":
				screen = new HomeScreen();
				break;
			case "game":
				screen = new GameScreen();
		}
		screen.create();
	}
}
