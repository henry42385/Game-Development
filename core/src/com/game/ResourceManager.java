package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResourceManager extends AssetManager {
    public BitmapFont font;
    public Texture topBar;
    public Texture start;
    public Texture endTurn;
    public Texture settings;
    public SpriteBatch batch;
    public Music music;

    public ResourceManager() {
        create();
    }

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        topBar = new Texture("sprites/TopBar2.png");
        settings = new Texture("sprites/settingsCog.png");
        start = new Texture("sprites/start.png");
        endTurn = new Texture("sprites/endTurn.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/song1.mp3"));

        music.setLooping(true);
        music.play();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        topBar.dispose();
        settings.dispose();
        music.dispose();
        start.dispose();
        endTurn.dispose();
    }
}
