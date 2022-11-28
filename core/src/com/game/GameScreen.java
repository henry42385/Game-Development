package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen extends Screen {
    private ShapeRenderer shapeRenderer;
    private MapManager mapManager;
    private ShipManager shipManager;
    private ReplayManager replayManager;
    private ResourceManager resourceManager;
    private GUI gui;

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
        shapeRenderer = new ShapeRenderer();
        shipManager = new ShipManager();
        mapManager = new MapManager();
        replayManager = new ReplayManager();
        resourceManager = new ResourceManager();
        gui = new GUI();

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
        replayManager.setReplay(shipManager.getPlayer1Ships(), shipManager.getPlayer2Ships());
        for (Ship ship : shipManager.getPlayer1Ships()) {
            if (ship.getAttack() != null) {
                for (Ship shipEnemy : shipManager.getPlayer2Ships()) {
                    if (shipEnemy.getLocation().equals(ship.getAttack()))
                        shipEnemy.takeDamage();
                }
            }
        } for (Ship ship : shipManager.getPlayer2Ships()) {
            if (ship.getAttack() != null) {
                for (Ship shipEnemy : shipManager.getPlayer1Ships()) {
                    if (shipEnemy.getLocation().equals(ship.getAttack()))
                        shipEnemy.takeDamage();
                }
            }
        }

        Iterator<Ship> itr = shipManager.getPlayer1Ships().iterator();
        while (itr.hasNext()) {
            Ship ship = itr.next();
            if (ship.getHp() == 0) {
                itr.remove();
            } else {
                ship.play();
            }
        }
        itr = shipManager.getPlayer2Ships().iterator();
        while (itr.hasNext()) {
            Ship ship = itr.next();
            if (ship.getHp() == 0) {
                itr.remove();
            } else {
                ship.play();
            }
        }
    }

    public void render() {
        updateView();
        if (gameStatus == 0) {
            mapManager.renderMap();
            mapManager.renderGrid();
            shipManager.render();
        } else {
            switch (turnStatus) {
                case "start":
                    mapManager.renderMap();
                    mapManager.renderFog(0, shipManager);
                    mapManager.renderGrid();
                    renderStart();
                    break;
                case "replay":
                    elapsedTime = TimeUtils.timeSinceMillis(startTime);
                    if (elapsedTime >= 2000) {
                        turnStatus = "play";
                    } else {
                        mapManager.renderMap();
                        replayManager.render(gameStatus, mapManager, elapsedTime);
                        mapManager.renderFog(gameStatus, shipManager);
                        mapManager.renderGrid();
                        break;
                    }
                case "play":
                    mapManager.renderMap();
                    shipManager.render();
                    mapManager.renderFog(gameStatus, shipManager);
                    mapManager.renderGrid();
            }
        }
        renderHUD();
    }

    private void renderHUD() {
        StaticCamera.update();
        ResourceManager rm = resourceManager;
        resourceManager.batch.setProjectionMatrix(StaticCamera.get().combined);
        resourceManager.batch.begin();
        resourceManager.font.getData().setScale(2);
        rm.batch.draw(rm.topBar, 0, DynamicCamera.get().viewportHeight - 200, 1440, 200);
        if (gameStatus == 0) {
            resourceManager.font.draw(resourceManager.batch, "Press anywhere to start", 500, 500);
        } else if (gameStatus == 1) {
            if (turnStatus.equals("replay")) {
                resourceManager.font.draw(resourceManager.batch, "Replaying last turn", 500, 500);
            } else if (turnStatus.equals("play")) {
                gui.render(resourceManager.batch);
                resourceManager.font.draw(resourceManager.batch, "Player 2", 1300, 750);
                resourceManager.font.setColor(1, 0, 0, 1);
                resourceManager.font.draw(resourceManager.batch, "Player 1", 100, 750);
            }
        } else if (gameStatus == 2) {
            if (turnStatus.equals("replay")) {
                resourceManager.font.draw(resourceManager.batch, "Replaying last turn", 500, 500);
            } else if (turnStatus.equals("play")) {
                gui.render(resourceManager.batch);
                resourceManager.font.draw(resourceManager.batch, "Player 1", 100, 750);
                resourceManager.font.setColor(1, 0, 0, 1);
                resourceManager.font.draw(resourceManager.batch, "Player 2", 1300, 750);
            }
        } resourceManager.font.setColor(1, 1, 1, 1);
        resourceManager.batch.end();
    }

    private void renderStart() {
        StaticCamera.update();
        resourceManager.batch.setProjectionMatrix(StaticCamera.get().combined);
        resourceManager.batch.begin();
        resourceManager.font.getData().setScale(7);
        resourceManager.font.setColor(1, 0, 0, 1);
        resourceManager.font.draw(resourceManager.batch, "Player " + gameStatus, StaticCamera.get().viewportWidth/2 - 200, StaticCamera.get().viewportHeight/2 + 50);
        resourceManager.batch.end();
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
        shapeRenderer.dispose();
        shipManager.dispose();
        mapManager.dispose();
        replayManager.dispose();
        resourceManager.dispose();
        gui.dispose();
    }

    public static int getGameStatus() {
        return gameStatus;
    }
}
