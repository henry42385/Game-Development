package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends Screen {

    private SpriteBatch batchStatic;

    private ShapeRenderer shapeRenderer;
    private MapManager mapManager;
    private static ShipManager shipManager;
    private BitmapFont font;
    private int turn = 0;
    private long startTime;
    private long elapsedTime;
    /*  start
        player1
        player2
     */
    private static int gameStatus = 0;
    /*  start
        replay
        play
     */
    private String turnStatus = "start";


    public void create() {


        batchStatic = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        shipManager = new ShipManager();
        mapManager = new MapManager();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    switch (gameStatus) {
                        case 0:
                            gameStatus = 1;
                            break;
                        case 1:
                            switch (turnStatus) {
                                case "start":
                                    shipManager.setSelectedShip(null);
                                    if (turn == 0) {
                                        turnStatus = "play";
                                    } else {
                                        turnStatus = "replay";
                                        startTime = TimeUtils.millis();
                                    }
                                    break;
                                case "play":
                                    turnStatus = "start";
                                    gameStatus = 2;
                            }
                            break;
                        case 2:
                            switch (turnStatus) {
                                case "start":
                                    shipManager.setSelectedShip(null);
                                    if (turn == 0) {
                                        turnStatus = "play";
                                    } else {
                                        turnStatus = "replay";
                                        startTime = TimeUtils.millis();
                                    }
                                    break;
                                case "play":
                                    turnStatus = "start";
                                    gameStatus = 1;
                                    playTurn();
                                    turn++;
                            }
                            break;
                    }
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
        switch (gameStatus) {
            case 0:
                mapManager.renderMap();
                mapManager.renderGrid();
                shipManager.render();
                updateView();
                break;
            case 1:
            case 2:
                switch (turnStatus) {
                    case "start":
                        mapManager.renderMap();
                        mapManager.renderFog(0);
                        mapManager.renderGrid();
                        renderStart();
                        break;
                    case "replay":
                         elapsedTime = TimeUtils.timeSinceMillis(startTime);
                         if (elapsedTime >= 5000)
                             turnStatus = "play";
//                        replayManager.replay(gameStatus);
                        break;
                    case "play":
                        mapManager.renderMap();
                        shipManager.render();
                        mapManager.renderFog(gameStatus);
                        mapManager.renderGrid();
                        renderHUD();
                }
                updateView();
                break;
        }
    }

    private void renderHUD() {
        StaticCamera.update();
        batchStatic.setProjectionMatrix(StaticCamera.get().combined);
        batchStatic.begin();
        font.getData().setScale(2);
        if (gameStatus == 1) {
            font.draw(batchStatic, "Player 2",1300, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 1",100, 750);
        } else if (gameStatus == 2) {
            font.draw(batchStatic, "Player 1",100, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 2",1300, 750);
        } font.setColor(1, 1, 1, 1);
        batchStatic.end();
    }

    private void renderStart() {
        StaticCamera.update();
        batchStatic.setProjectionMatrix(StaticCamera.get().combined);
        batchStatic.begin();
        font.getData().setScale(7);
        font.setColor(1, 0, 0, 1);
        font.draw(batchStatic, "Player " + gameStatus, StaticCamera.get().viewportWidth/2 - 200, StaticCamera.get().viewportHeight/2 + 50);
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

    public static ShipManager getShipManager() {
        return shipManager;
    }

    public static int getGameStatus() {
        return gameStatus;
    }
}
