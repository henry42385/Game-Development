package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUI {
    private Texture replayButton;

    public GUI() {
        create();
    }

    private void create() {
        replayButton = new Texture("sprites/guiSprites/replay_button.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(replayButton, 656, 50, 128, 128, 0, 1, 0.5f, 0);
    }

    public void dispose() {
        replayButton.dispose();
    }
}
