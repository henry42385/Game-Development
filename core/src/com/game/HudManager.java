package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudManager {
    private Texture replayButton;
    private Texture endTurnButton;

    public HudManager() {
        create();
    }

    private void create() {
        replayButton = new Texture("sprites/guiSprites/replay_button.png");
        endTurnButton = new Texture("sprites/endTurn.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(replayButton, 656, 50, 128, 128, 0, 1, 0.5f, 0);
        batch.draw(endTurnButton, 856, 50, 128, 128);
    }

    public void dispose() {
        replayButton.dispose();
        endTurnButton.dispose();
    }
}
