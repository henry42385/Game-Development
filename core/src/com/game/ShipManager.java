package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class ShipManager {
    private Texture destroyer;
    private Texture target;
    private Texture red;
    private Texture arrow;
    private SpriteBatch batch;
    private ArrayList<Ship> player1Ships = new ArrayList<>();
    private ArrayList<Ship> player2Ships = new ArrayList<>();
    private Ship selectedShip;

    public ShipManager() {
        create();
        player1Ships.add(new Destroyer(new Vector2(5, 4), 2));
        player1Ships.add(new Destroyer(new Vector2(7, 10), 1));
        player2Ships.add(new Destroyer(new Vector2(10, 4), 6));
    }

    public void create() {
        batch = new SpriteBatch();
        destroyer = new Texture("sprites/Destroyer.png");
        target = new Texture("sprites/Target.png");
        red = new Texture("sprites/Red.png");
        arrow = new Texture("sprites/Arrow.png");
    }

    public ArrayList<Ship> getPlayer1Ships() {
        return player1Ships;
    }

    public ArrayList<Ship> getPlayer2Ships() {
        return player2Ships;
    }

    public void render() {
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);
        batch.begin();
        if (GameScreen.getPlayerTurn() == 0) {
            for (Ship ship : player2Ships) {
                if (ship.getAfterimage() == null)
                    batch.draw(destroyer, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 1, (float) 1 / 8 * (ship.getDirection() + 1), 0.5f);
                else
                    batch.draw(destroyer, ship.getAfterimage().x * 128, ship.getAfterimage().y * 128, 128, 128, (float) 1 / 8 * ship.getAfterimageDirection(), 1, (float) 1 / 8 * (ship.getAfterimageDirection() + 1), 0.5f);
            }
            for (Ship ship : player1Ships) {
                if (ship.getAfterimage() != null) {
                    batch.draw(arrow, (ship.getAfterimage().x - 1) * 128, (ship.getAfterimage().y - 1) * 128, 384, 384, (float) 1/8 * (ship.getDirection()), 1, (float) 1/8 * (ship.getDirection() + 1), 0);
                    if (ship.getAttack() != null) {
                        batch.draw(target, ship.getAttack().x * 128 + 32, ship.getAttack().y * 128 + 32, 64, 64);
                    }
                } batch.draw(destroyer, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 0.5f, (float) 1 / 8 * (ship.getDirection() + 1), 0);
            }
        } else {
            for (Ship ship : player1Ships) {
                if (ship.getAfterimage() == null)
                    batch.draw(destroyer, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 0.5f, (float) 1 / 8 * (ship.getDirection() + 1), 0);
                else
                    batch.draw(destroyer, ship.getAfterimage().x * 128, ship.getAfterimage().y * 128, 128, 128, (float) 1 / 8 * ship.getAfterimageDirection(), 0.5f, (float) 1 / 8 * (ship.getAfterimageDirection() + 1), 0);
            }
            for (Ship ship : player2Ships) {
                if (ship.getAfterimage() != null) {
                    batch.draw(arrow, (ship.getAfterimage().x - 1) * 128, (ship.getAfterimage().y - 1) * 128, 384, 384, (float) 1/8 * (ship.getDirection()), 1, (float) 1/8 * (ship.getDirection() + 1), 0);
                    if (ship.getAttack() != null) {
                        batch.draw(target, ship.getAttack().x * 128 + 32, ship.getAttack().y * 128 + 32, 64, 64);
                    }
                } batch.draw(destroyer, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 1, (float) 1 / 8 * (ship.getDirection() + 1), 0.5f);
            }
        }

        if (selectedShip != null) {
            if (selectedShip.getStatus().equals("move")) {
                for (Vector2 location : selectedShip.generateMoves()) {
                    batch.draw(red, location.x * 128, location.y * 128, 128, 128);
                }
            } else if (selectedShip.getStatus().equals("attack")) {
                if (MouseHandler.grid.x >= selectedShip.getLocation().x - 2 && MouseHandler.grid.x <= selectedShip.getLocation().x + 2 &&
                        MouseHandler.grid.y >= selectedShip.getLocation().y - 2 && MouseHandler.grid.y <= selectedShip.getLocation().y + 2 &&
                        (MouseHandler.grid.x != selectedShip.getLocation().x || MouseHandler.grid.y != selectedShip.getLocation().y))
                batch.draw(target, MouseHandler.grid.x * 128 + 32, MouseHandler.grid.y * 128 + 32, 64, 64);
                for (int i = (int)selectedShip.getLocation().x - 2; i <= (int)selectedShip.getLocation().x + 2; i++) {
                    for (int j = (int)selectedShip.getLocation().y - 2; j <= (int)selectedShip.getLocation().y + 2; j++) {
                        batch.draw(red, i * 128, j * 128, 128, 128);
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
                    if (ship.getLocation().equals(MouseHandler.grid)) {
                        selectedShip = ship;
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getLocation().equals(MouseHandler.grid)) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
        } else if (selectedShip.getStatus().equals("move")){
            // Select another
            if (GameScreen.getPlayerTurn() == 0) {
                for (Ship ship : player1Ships) {
                    if (ship.getLocation().equals(MouseHandler.grid) && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getLocation().equals(MouseHandler.grid) && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
            // Select move option
            int directionIndex = -1;
            for (Vector2 location : selectedShip.generateMoves()) {
                if (location.equals(MouseHandler.grid)) {
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
                    if (ship.getLocation().equals(MouseHandler.grid) && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        System.out.println("returned");
                        return;
                    }
                }
            } else {
                for (Ship ship : player2Ships) {
                    if (ship.getLocation().equals(MouseHandler.grid) && !selectedShip.equals(ship)) {
                        selectedShip = ship;
                        return;
                    }
                }
            }
            // Select attack option
            if (MouseHandler.grid.x >= selectedShip.getLocation().x - 2 && MouseHandler.grid.x <= selectedShip.getLocation().x + 2 &&
                MouseHandler.grid.y >= selectedShip.getLocation().y - 2 && MouseHandler.grid.y <= selectedShip.getLocation().y + 2 &&
                    (MouseHandler.grid.x != selectedShip.getLocation().x || MouseHandler.grid.y != selectedShip.getLocation().y)) {
                selectedShip.setStatus("complete");
                selectedShip.attack(new Vector2(MouseHandler.grid.x, MouseHandler.grid.y));
                return;
            }
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

    public void setSelectedShip(Ship selectedShip) {
        this.selectedShip = selectedShip;
    }
}
