package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class ShipManager {
    private Texture destroyer;
    private Texture target;
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

        Vector3 worldMouseLocation = DynamicCamera.get().unproject(mouseLocation);
//        if ((int)(worldMouseLocation.x / 128) >= 2 && (int)(worldMouseLocation.x / 128) < map.getWidth() -2 &&
//                (int)(worldMouseLocation.y / 128) >= 2 && (int)(worldMouseLocation.y / 128) < map.getHeight() -2)
        selectedShip = player1Ships.get(0);
        if (selectedShip != null)
        batch.draw(target, selectedShip.getX() * 128 + 32, selectedShip.getY() * 128 + 32, 64, 64);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        destroyer.dispose();
        target.dispose();
    }
}
