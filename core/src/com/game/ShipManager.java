package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ShipManager {
    private Texture shipTextures;
    private Texture target;
    private Texture red;
    private Texture arrow;
    private Texture hp;
    private SpriteBatch batch;
    private ArrayList<Ship> ships = new ArrayList<>();
    private Ship selectedShip;

    public ShipManager() {
        create();
        ships.add(new MissileShip(new Vector2(6, 6), 2, 0));
        ships.add(new Destroyer(new Vector2(4, 4), 3, 0));
        ships.add(new Destroyer(new Vector2(10, 4), 6, 1));
        ships.add(new MissileShip(new Vector2(10, 5), 7, 1));
    }

    public void create() {
        batch = new SpriteBatch();
        shipTextures = new Texture(Gdx.files.internal("sprites/Ships.png"));
        target = new Texture("sprites/Target.png");
        red = new Texture("sprites/Red.png");
        arrow = new Texture("sprites/Arrow.png");
        hp = new Texture("sprites/hp.png");
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void render() {
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);
        batch.begin();

        // Draw all ships
        for (Ship ship : ships) {

            // Your Ships
            if (ship.player == GameScreen.getGameStatus() - 1) {
                if (ship.getAfterimage() != null) {
                    batch.draw(arrow, (ship.getAfterimage().x - 1) * 128, (ship.getAfterimage().y - 1) * 128, 384, 384, (float) 1/8 * (ship.getDirection()), 1, (float) 1/8 * (ship.getDirection() + 1), 0);
                    if (ship.getAttack() != null) {
                        // Destroyer options
                        if (selectedShip instanceof Destroyer) {
                            batch.draw(target, ship.attack.x * 128 + 32, ship.attack.y * 128 + 32, 64, 64);
                        }

                        // Missile ship options
                        else if (selectedShip instanceof MissileShip) {
                            batch.draw(target, ship.attack.x * 128 + 32, ship.attack.y * 128 + 32, 64, 64);
                            Vector2 extraTarget = new Vector2();
                            if (Math.abs(selectedShip.attack.x - selectedShip.location.x) == 2 || Math.abs(selectedShip.attack.y - selectedShip.location.y) == 2) {
                                extraTarget = selectedShip.attack.cpy().add(selectedShip.location.cpy()).scl(0.5f);
                            } else if (Math.abs(selectedShip.attack.x - selectedShip.location.x) == 1 || Math.abs(selectedShip.attack.y - selectedShip.location.y) == 1) {
                                extraTarget.x = selectedShip.attack.x - selectedShip.location.x + selectedShip.attack.x;
                                extraTarget.y = selectedShip.attack.y - selectedShip.location.y + selectedShip.attack.y;
                            }
                            batch.draw(target, extraTarget.x * 128 + 32, extraTarget.y * 128 + 32, 64, 64);
                        }
                    }
                }
                batch.draw(shipTextures, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 0.25f + 0.5f * ship.spriteID, (float) 1 / 8 * (ship.getDirection() + 1), 0.5f * ship.spriteID);
                batch.draw(hp, ship.getLocation().x * 128, ship.getLocation().y * 128 + 96, 128, 32, (float) 1/3 * (ship.getHp() - 1), 0.5f, (float) 1/3 * ship.getHp(), 0);
            }

            // Enemy Ships
            else {
                if (ship.getAfterimage() == null) {
                    batch.draw(shipTextures, ship.getLocation().x * 128, ship.getLocation().y * 128, 128, 128, (float) 1 / 8 * ship.getDirection(), 0.5f + 0.5f * ship.spriteID, (float) 1 / 8 * (ship.getDirection() + 1), 0.25f + 0.5f * ship.spriteID);
                    batch.draw(hp, ship.getLocation().x * 128, ship.getLocation().y * 128 + 96, 128, 32, (float) 1 / 3 * (ship.getHp() - 1), 1, (float) 1 / 3 * ship.getHp(), 0.5f);
                } else {
                    batch.draw(shipTextures, ship.getAfterimage().x * 128, ship.getAfterimage().y * 128, 128, 128, (float) 1 / 8 * ship.getAfterimageDirection(), 0.5f + 0.5f * ship.spriteID, (float) 1 / 8 * (ship.getAfterimageDirection() + 1), 0.25f + 0.5f * ship.spriteID);
                    batch.draw(hp, ship.getAfterimage().x * 128, ship.getAfterimage().y * 128 + 96, 128, 32, (float) 1 / 3 * (ship.getHp() - 1), 1, (float) 1 / 3 * ship.getHp(), 0.5f);
                }
            }
        }

        // Draw move and attack options
        if (selectedShip != null) {
            if (selectedShip.getStatus().equals("move")) {
                for (Vector2 location : selectedShip.generateMoves()) {
                    batch.draw(red, location.x * 128, location.y * 128, 128, 128);
                }
            } else if (selectedShip.getStatus().equals("attack")) {
                for (Vector2 location : selectedShip.generateAttacks()) {
                    batch.draw(red, location.x * 128, location.y * 128, 128, 128);

                    // Destroyer options
                    if (MouseHandler.grid.equals(location) && !MouseHandler.grid.equals(selectedShip.getLocation()) && selectedShip instanceof Destroyer) {
                        batch.draw(target, MouseHandler.grid.x * 128 + 32, MouseHandler.grid.y * 128 + 32, 64, 64);
                    }

                    // Missile ship options
                    else if (MouseHandler.grid.equals(location) && !MouseHandler.grid.equals(selectedShip.getLocation()) && selectedShip instanceof MissileShip) {
                        batch.draw(target, MouseHandler.grid.x * 128 + 32, MouseHandler.grid.y * 128 + 32, 64, 64);
                        Vector2 extraTarget = new Vector2();
                        if (Math.abs(MouseHandler.grid.x - selectedShip.location.x) == 2 || Math.abs(MouseHandler.grid.y - selectedShip.location.y) == 2) {
                            extraTarget = location.add(selectedShip.location).scl(0.5f);
                        } else if (Math.abs(MouseHandler.grid.x - selectedShip.location.x) == 1 || Math.abs(MouseHandler.grid.y - selectedShip.location.y) == 1) {
                            extraTarget.x = location.x - selectedShip.location.x + location.x;
                            extraTarget.y = location.y - selectedShip.location.y + location.y;
                        }
                        batch.draw(target, extraTarget.x * 128 + 32, extraTarget.y * 128 + 32, 64, 64);
                    }

                }
            }
        }
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            updateSelected();
    }

    public void updateSelected() {
        // No ship selected
        if (selectedShip == null || selectedShip.getStatus().equals("complete")) {
            for (Ship ship : ships) {
                if (ship.getLocation().equals(MouseHandler.grid) && ship.player == GameScreen.getGameStatus() - 1) {
                    selectedShip = ship;
                    return;
                }
            }
        }

        // Selected ship in move stage
        else if (selectedShip.getStatus().equals("move")) {
            // Select another ship
            for (Ship ship : ships) {
                if (ship.getLocation().equals(MouseHandler.grid) && ship.player == GameScreen.getGameStatus() - 1) {
                    selectedShip = ship;
                    return;
                }
            }

            // Select move location
            int directionIndex = -1;
            for (Vector2 location : selectedShip.generateMoves()) {
                if (location.equals(MouseHandler.grid)) {
                    selectedShip.move(location, directionIndex);
                    selectedShip.setStatus("attack");
                    return;
                }
                directionIndex++;
            }

            // Click out
            this.selectedShip = null;
        }

        // Selected ship in attack stage
        else if (selectedShip.getStatus().equals("attack")) {
            // Select another ship
            for (Ship ship : ships) {
                if (ship.getLocation().equals(MouseHandler.grid) && ship.player == GameScreen.getGameStatus() - 1) {
                    selectedShip = ship;
                    return;
                }
            }

            // Select attack location
            if (selectedShip.generateAttacks().contains(MouseHandler.grid)) {
                selectedShip.setStatus("complete");
                selectedShip.attack(MouseHandler.grid.cpy());
                return;
            }

            // Click out
            this.selectedShip = null;
        }
    }

    public void dispose() {
        batch.dispose();
        target.dispose();
        red.dispose();
        shipTextures.dispose();
    }

    public void setSelectedShip(Ship selectedShip) {
        this.selectedShip = selectedShip;
    }
}
