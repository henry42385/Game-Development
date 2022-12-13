package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen extends Screen {
    private ShapeRenderer shapeRenderer;
    private MapManager mapManager;
    private ShipManager shipManager;
    private ReplayManager replayManager;
    private ResourceManager rm;
    private HudManager hudManager;

    private int turn = 0;
    private long startTime;
    private long elapsedTime;
    /*  start
        player1
        player2
        end
     */
    private static int gameStatus = 1;
    /*  start
        replay
        play
     */
    private String turnStatus = "start";
    private String winner;


    public void create() {
        shapeRenderer = new ShapeRenderer();
        shipManager = new ShipManager();
        mapManager = new MapManager();
        replayManager = new ReplayManager();
        rm = new ResourceManager();
        hudManager = new HudManager();
    }

    public void playTurn() {
        // Create turn replay
        replayManager.setReplay(shipManager.getShips());

        // Update ship hp
        for (Ship ship : shipManager.getShips()) {
            if (ship.getAttack() != null) {
                // Prepare damage zones
                Vector2 extraTarget = new Vector2();
                if (ship instanceof MissileShip) {
                    if (Math.abs(ship.attack.x - ship.location.x) == 2 || Math.abs(ship.attack.y - ship.location.y) == 2) {
                        extraTarget = ship.attack.cpy().add(ship.location.cpy()).scl(0.5f);
                    } else if (Math.abs(ship.attack.x - ship.location.x) == 1 || Math.abs(ship.attack.y - ship.location.y) == 1) {
                        extraTarget.x = ship.attack.x - ship.location.x + ship.attack.x;
                        extraTarget.y = ship.attack.y - ship.location.y + ship.attack.y;
                    }
                } else {
                    extraTarget = ship.getAttack();
                }

                for (Ship targetShip : shipManager.getShips()) {
                    if (targetShip.getLocation().equals(ship.getAttack()) || targetShip.getLocation().equals(extraTarget))
                            targetShip.takeDamage();
                }
            }
            if (ship.getLocation().x < mapManager.dangerX1 || ship.getLocation().x > mapManager.dangerX2 || ship.getLocation().y < mapManager.dangerY1 || ship.getLocation().y > mapManager.dangerY2) {
                ship.takeDamage();
            }
        }

        // Remove 0 hp ships
        Iterator<Ship> itr = shipManager.getShips().iterator();
        while (itr.hasNext()) {
            Ship ship = itr.next();
            if (ship.getHp() == 0) {
                itr.remove();
            } else {
                ship.play();
            }
        }

        // Check winner
        int p1Count = 0;
        int p2Count = 0;
        for (Ship ship : shipManager.getShips()) {
            if (ship.player == 0)
                p1Count++;
            else
                p2Count++;
        }

        if (p1Count == 0 && p2Count == 0) {
            winner = "Draw";
            gameStatus = 3;
        } else if (p1Count == 0) {
            winner = "Player 1 wins";
            gameStatus = 3;
        } else if (p2Count == 0) {
            winner = "Player 2 wins";
            gameStatus = 3;
        }


        turn++;
        if (turn % 3 == 0) {
            mapManager.dangerX1++;
            mapManager.dangerX2--;
            mapManager.dangerY1++;
            mapManager.dangerY2--;
        }
    }

    public void render() {
        inputChecker();
        updateView();
        if (gameStatus == 0) {
            mapManager.renderMap();
            mapManager.renderGrid();
            shipManager.render();
        } else if (gameStatus == 1 || gameStatus == 2){
            switch (turnStatus) {
                case "start":
                    mapManager.renderMap();
                    mapManager.renderFog(0, shipManager);
                    mapManager.renderGrid();
                    mapManager.renderBorder();
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
                        mapManager.renderBorder();
                        break;
                    }
                case "play":
                    mapManager.renderMap();
                    shipManager.render();
                    mapManager.renderFog(gameStatus, shipManager);
                    mapManager.renderGrid();
                    mapManager.renderBorder();
            }
        } else if (gameStatus == 3) {
            mapManager.renderMap();
            mapManager.renderGrid();
            mapManager.renderBorder();
            shipManager.render();
        }
        renderHUD();
    }

    private void renderHUD() {
        StaticCamera.update();
        ResourceManager rm = this.rm;
        this.rm.batch.setProjectionMatrix(StaticCamera.get().combined);
        this.rm.batch.begin();
        this.rm.font.getData().setScale(2);
        rm.batch.draw(rm.topBar, 0, DynamicCamera.get().viewportHeight - 175, 1440, 200);
        rm.batch.draw(rm.settings, 1350, 700, 64, 64);
        if (gameStatus == 0) {
            this.rm.font.draw(this.rm.batch, "Press anywhere to start", 500, 500);
        } else if (gameStatus == 1) {
            if (turnStatus.equals("replay")) {
                this.rm.font.draw(this.rm.batch, "Replaying last turn", 500, 500);
            } else if (turnStatus.equals("play")) {
                hudManager.render(this.rm.batch);
                this.rm.font.draw(this.rm.batch, "Player 1", 100, 750);
            }
        } else if (gameStatus == 2) {
            if (turnStatus.equals("replay")) {
                this.rm.font.draw(this.rm.batch, "Replaying last turn", 500, 500);
            } else if (turnStatus.equals("play")) {
                hudManager.render(this.rm.batch);
                this.rm.font.draw(this.rm.batch, "Player 2", 100, 750);
            }
        } else if (gameStatus == 3) {
            this.rm.font.draw(rm.batch, winner, 500, 500);
        }

        this.rm.font.setColor(1, 1, 1, 1);
        this.rm.batch.end();
    }

    private void renderStart() {
        StaticCamera.update();
        rm.batch.setProjectionMatrix(StaticCamera.get().combined);
        rm.batch.begin();
        rm.font.getData().setScale(3);
        rm.font.setColor(1, 0, 0, 1);
        rm.font.draw(rm.batch, "Player " + gameStatus, 650, 450);
        rm.batch.draw(rm.start, 608, 350, 224, 34);
        rm.batch.end();
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

    private void inputChecker() {
        if (gameStatus == 0) {
            if (Gdx.input.isTouched())
                gameStatus = 1;
        }

        Vector3 coords = StaticCamera.get().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        //Replay button
        if (Gdx.input.isTouched() &&
                coords.x < 784 &&
                coords.x > 656 &&
                coords.y < 178 &&
                coords.y > 50 &&
                turn != 0 &&
                !turnStatus.equals("start")) {
            turnStatus = "replay";
            startTime = TimeUtils.millis();
        }

        // End turn button
        else if (Gdx.input.isTouched() &&
                    coords.x < 984 &&
                    coords.x > 856 &&
                    coords.y < 178 &&
                    coords.y > 50 &&
                    !turnStatus.equals("start")) {
            if (gameStatus == 1) {
                gameStatus = 2;
                turnStatus = "start";
            } else if (gameStatus == 2) {
                gameStatus = 1;
                turnStatus = "start";
                playTurn();
            }
        }

        // Start turn button
        else if (Gdx.input.isTouched() &&
                    coords.x < 832 &&
                    coords.x > 608 &&
                    coords.y < 384 &&
                    coords.y > 350 &&
                    turnStatus.equals("start")) {
            if (turn != 0) {
                System.out.println("test");
                turnStatus = "replay";
                startTime = TimeUtils.millis();
            } else {
                turnStatus = "play";
            }
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        shipManager.dispose();
        mapManager.dispose();
        replayManager.dispose();
        rm.dispose();
        hudManager.dispose();
    }

    public static int getGameStatus() {
        return gameStatus;
    }
}
