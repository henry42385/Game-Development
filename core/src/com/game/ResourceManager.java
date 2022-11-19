package com.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResourceManager {
    public BitmapFont font;
    public SpriteBatch batch;

    public ResourceManager() {
        create();
    }

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
    }

    public void dispose() {

    }
}
