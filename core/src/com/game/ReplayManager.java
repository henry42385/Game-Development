package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ReplayManager {
    private ArrayList<MoveReplay> moves;
    private ArrayList<AttackReplay> attacks;
    private Texture destroyer;
    private Texture attack;
    private SpriteBatch batch;
    private int moveTime = 2000;
    private int attackTime = 3000;

    public ReplayManager() {
        create();
    }

    public void create() {
        destroyer = new Texture("sprites/Destroyer.png");
        attack = new Texture("sprites/Attack.png");
        batch = new SpriteBatch();
    }

    public void setReplay(ArrayList<Ship> team1Ships, ArrayList<Ship> team2Ships) {
        moves = new ArrayList<>();
        attacks = new ArrayList<>();

        for (Ship ship : team1Ships) {
            if (ship.getAfterimage() != null) {
                moves.add(new MoveReplay(ship.getAfterimage().cpy(), ship.getLocation().cpy(), ship.getAfterimageDirection(),ship.getDirection(), 1));
            } else {
                moves.add(new MoveReplay(ship.getLocation().cpy(), ship.getLocation().cpy(), ship.getDirection(), ship.getDirection(), 1));
            }
        } for (Ship ship : team2Ships) {
            if (ship.getAfterimage() != null) {
                moves.add(new MoveReplay(ship.getAfterimage().cpy(), ship.getLocation().cpy(), ship.getAfterimageDirection(), ship.getDirection(), 2));
            } else {
                moves.add(new MoveReplay(ship.getLocation().cpy(), ship.getLocation().cpy(), ship.getDirection(), ship.getDirection(), 2));
            }
        } for (Ship ship : team1Ships) {
            if (ship.getAttack() != null) {
                attacks.add(new AttackReplay(ship.getLocation().cpy(), ship.getAttack().cpy()));
            }
        } for (Ship ship : team2Ships) {
            if (ship.getAttack() != null) {
                attacks.add(new AttackReplay(ship.getLocation().cpy(), ship.getAttack().cpy()));
            }
        }
    }

    public void render(int player, MapManager mapManager, long timeElapsed) {
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);
        mapManager.renderMap();
        batch.begin();
        if (timeElapsed >= 0 && timeElapsed < moveTime) {
            for (MoveReplay move : moves) {
                if (timeElapsed < moveTime / 2)
                    batch.draw(destroyer, move.start.x * 128 + ((move.end.x - move.start.x) * 128) / moveTime * timeElapsed, move.start.y * 128 + ((move.end.y - move.start.y) * 128) / moveTime * timeElapsed, 128, 128, (float) 1 / 8 * move.startDirection, 0.5f * move.team, (float) 1 / 8 * (move.startDirection + 1), 0.5f * (move.team - 1));
                else
                    batch.draw(destroyer, move.start.x * 128 + ((move.end.x - move.start.x) * 128) / moveTime * timeElapsed, move.start.y * 128 + ((move.end.y - move.start.y) * 128) / moveTime * timeElapsed, 128, 128, (float) 1 / 8 * move.endDirection, 0.5f * move.team, (float) 1 / 8 * (move.endDirection + 1), 0.5f * (move.team - 1));
            }
        } if (timeElapsed >= moveTime && timeElapsed < attackTime) {
            for (MoveReplay move : moves) {
                batch.draw(destroyer, move.end.x * 128, move.end.y * 128, 128, 128, (float) 1 / 8 * move.endDirection, 0.5f * move.team, (float) 1 / 8 * (move.endDirection + 1), 0.5f * (move.team - 1));
            } for (AttackReplay attackReplay : attacks) {
                System.out.println(attackReplay.start.y);
                batch.draw(attack, attackReplay.start.x * 128 + ((attackReplay.end.x - attackReplay.start.x) * 128) / (attackTime - moveTime) * (timeElapsed - moveTime),
                        attackReplay.start.y * 128 + ((attackReplay.end.y - attackReplay.start.y) * 128) / (attackTime - moveTime) * (timeElapsed - moveTime), 128, 128);
            }
        }
        batch.end();
        mapManager.renderFog(player);
        mapManager.renderGrid();

    }

    public void dispose() {
        destroyer.dispose();
        batch.dispose();
        attack.dispose();
    }
}
