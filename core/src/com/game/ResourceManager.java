package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResourceManager {
    public BitmapFont font;
    public Texture topBar;
    public Texture settings;
    public SpriteBatch batch;

    public ResourceManager() {
        create();
    }

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        topBar = new Texture("sprites/TopBar2.png");
        settings = new Texture("sprites/guiSprites/settings.png");
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        topBar.dispose();
        settings.dispose();
    }
}
