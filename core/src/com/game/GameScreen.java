package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends Screen {

    private SpriteBatch batchStatic;

    private ShapeRenderer shapeRenderer;
    private MapManager mapManager;
    private static ShipManager shipManager;
    private BitmapFont font;
    private static int playerTurn = 0;
    private int turn = 0;


    public void create() {


        batchStatic = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        shipManager = new ShipManager();
        mapManager = new MapManager();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    if (playerTurn == 0) {
                        playerTurn = 1;
                    } else {
                        playerTurn = 0;
                        turn += 1;
                        playTurn();
                    } shipManager.setSelectedShip(null);
                }
                return true;
            }
        });
    }

    public void playTurn() {
        for (Ship ship : shipManager.getPlayer1Ships()) {
            ship.play();
        }
        for (Ship ship : shipManager.getPlayer2Ships()) {
            ship.play();
        }
    }

    public void render() {

        renderWorld();
        renderHUD();
        updateView();
    }

    private void renderWorld() {
        mapManager.renderMap();

        shipManager.render();
        mapManager.renderFog();
        mapManager.renderGrid();
    }

    private void renderHUD() {
        StaticCamera.update();
        batchStatic.begin();
        font.getData().setScale(2);
        if (playerTurn == 0) {
            font.draw(batchStatic, "Player 2",1300, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 1",100, 750);
        } else {
            font.draw(batchStatic, "Player 1",100, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 2",1300, 750);
        } font.setColor(1, 1, 1, 1);
        batchStatic.end();
    }

    private void updateView() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            DynamicCamera.moveLeft();
        } if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            DynamicCamera.moveRight();
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            DynamicCamera.moveUp();
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            DynamicCamera.moveDown();
        } if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            DynamicCamera.zoomOut();
        } if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            DynamicCamera.zoomIn();
        }
    }

    public void dispose() {

        batchStatic.dispose();
        shapeRenderer.dispose();
        font.dispose();
        shipManager.dispose();
        mapManager.dispose();
    }

    public static int getPlayerTurn() {
        return playerTurn;
    }

    public static ShipManager getShipManager() {
        return shipManager;
    }
}
