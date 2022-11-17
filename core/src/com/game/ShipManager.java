package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class ShipManager {
    private Texture destroyer;
    private Texture target;
    private Texture red;
    private SpriteBatch batch;
    private ArrayList<Ship> player1Ships = new ArrayList<>();
    private ArrayList<Ship> player2Ships = new ArrayList<>();
    private Ship selectedShip;
    private Vector3 mouseLocation;

    public ShipManager() {
        create();
        player1Ships.add(new Destroyer(4, 4, 2));
        player1Ships.add(new Destroyer(4, 6, 1));
        player2Ships.add(new Destroyer(16, 4, 6));
    }

    public void create() {
        mouseLocation = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        batch = new SpriteBatch();
        destroyer = new Texture("sprites/Destroyer.png");
        target = new Texture("sprites/Target.png");
        red = new Texture("sprites/TerrainSpritesheet2.png");
    }

    public ArrayList<Ship> getPlayer1Ships() {
        return player1Ships;
    }

    public ArrayList<Ship> getPlayer2Ships() {
        return player2Ships;
    }

    public void render() {
        mouseLocation.x = Gdx.input.getX();
        mouseLocation.y = Gdx.input.getY();
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);
        batch.begin();
        for (Ship ship : player1Ships) {
            batch.draw(destroyer, ship.getX() * 128, ship.getY() * 128, 128, 128, (float)1/8 * ship.getDirection(), 0.5f, (float)1/8 * (ship.getDirection() + 1), 0);
        }
        for (Ship ship : player2Ships) {
            batch.draw(destroyer, ship.getX() * 128, ship.getY() * 128, 128, 128, (float)1/8 * ship.getDirection(), 0.5f, (float)1/8 * (ship.getDirection() + 1), 0);
        }

//        if ((int)(worldMouseLocation.x / 128) >= 2 && (int)(worldMouseLocation.x / 128) < map.getWidth() -2 &&
//                (int)(worldMouseLocation.y / 128) >= 2 && (int)(worldMouseLocation.y / 128) < map.getHeight() -2)

        if (selectedShip != null) {
            if (selectedShip.getStatus().equals("move")) {
                for (Vector2 location : selectedShip.generateMoves()) {
                    batch.draw(red, location.x * 128, location.y * 128, 128, 128, 0, 0.5f, 0.25f, 0.25f);
                }
            } else if (selectedShip.getStatus().equals("attack")) {
                batch.draw(target, MouseHandler.grid.x * 128 + 32, MouseHandler.grid.y * 128 + 32, 64, 64);
                for (int i = selectedShip.getX() - 2; i <= selectedShip.getX() + 2; i++) {
                    for (int j = selectedShip.getY() - 2; j <= selectedShip.getY() + 2; j++) {
                        batch.draw(red, i * 128, j * 128, 128, 128, 0, 0.5f, 0.25f, 0.25f);
                    }
                }
            }
        }


        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            updateSelected();

        batch.end();
    }

    public void updateSelected() {
        if (selectedShip == null || selectedShip.getStatus().equals("complete")) {
            if (GameScreen.getPlayerTurn() == 0) {
                for (Ship ship : player1Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y) {
                        selectedShip = ship;
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
        } else if (selectedShip.getStatus().equals("move")){
            // Select another
            if (GameScreen.getPlayerTurn() == 0) {
                for (Ship ship : player1Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
            // Select move option
            int directionIndex = -1;
            for (Vector2 location : selectedShip.generateMoves()) {
                if (location.x == MouseHandler.grid.x && location.y == MouseHandler.grid.y) {
                    selectedShip.move(location, directionIndex);
                    selectedShip.setStatus("attack");
                    return;
                } directionIndex++;
            }
            // Other options
            this.selectedShip = null;
        } else if (selectedShip.getStatus().equals("attack")) {
            // Select another
            if (GameScreen.getPlayerTurn() == 0) {
                for (Ship ship : player1Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getX() == MouseHandler.grid.x && ship.getY() == MouseHandler.grid.y && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
            // Select attack option
            if (MouseHandler.grid.x >= selectedShip.getX() - 2 && MouseHandler.grid.x <= selectedShip.getX() + 2 &&
                MouseHandler.grid.y >= selectedShip.getY() - 2 && MouseHandler.grid.y <= selectedShip.getY() + 2) {
                selectedShip.setStatus("complete");
                return;
            }

//            int directionIndex = -1;
//            for (Vector2 location : selectedShip.generateMoves()) {
//                if (location.x == MouseHandler.grid.x && location.y == MouseHandler.grid.y) {
//                    selectedShip.move(location, directionIndex);
//                    selectedShip.setStatus("complete");
//                    break;
//                } directionIndex++;
//            }
            // Other options
            this.selectedShip = null;
        }


//        if (selectedShip.getStatus().equals("move")) {
//            selectedShip.move();
//        }


    }

    public void dispose() {
        batch.dispose();
        destroyer.dispose();
        target.dispose();
        red.dispose();
    }
}
