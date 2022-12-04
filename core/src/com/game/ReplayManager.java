package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ReplayManager {
    private List<MoveReplay> moves;
    private List<AttackReplay> attacks;
    private Texture ships;
    private Texture attack;
    private Texture explosion;
    private Texture hp;
    private SpriteBatch batch;
    private int moveTime = 1000;
    private int attackTime = 1500;
    private int explosionTime = 2000;

    public ReplayManager() {
        create();
    }

    public void create() {
        ships = new Texture("sprites/Ships.png");
        attack = new Texture("sprites/Attack.png");
        explosion = new Texture("sprites/Explosion.png");
        hp = new Texture("sprites/HP.png");
        batch = new SpriteBatch();
    }

    public void setReplay(ArrayList<Ship> ships) {
        moves = new ArrayList<>();
        attacks = new ArrayList<>();

        for (Ship ship : ships) {
            // Ship movements
            if (ship.getAfterimage() != null) {
                moves.add(new MoveReplay(ship.getAfterimage().cpy(), ship.getLocation().cpy(), ship.getAfterimageDirection(), ship.getDirection(), ship.player, ship.getHp(), ship.spriteID));
            } else {
                moves.add(new MoveReplay(ship.getLocation().cpy(), ship.getLocation().cpy(), ship.getDirection(), ship.getDirection(), ship.player, ship.getHp(), ship.spriteID));
            }

            // Ship attacks
            if (ship.getAttack() != null) {
                attacks.add(new AttackReplay(ship.getLocation().cpy(), ship.getAttack().cpy()));
                if (ship instanceof MissileShip) {
                    Vector2 extraTarget = new Vector2();
                    if (ship instanceof MissileShip) {
                        if (Math.abs(ship.attack.x - ship.location.x) == 2 || Math.abs(ship.attack.y - ship.location.y) == 2) {
                            extraTarget = ship.attack.cpy().add(ship.location.cpy()).scl(0.5f);
                        } else if (Math.abs(ship.attack.x - ship.location.x) == 1 || Math.abs(ship.attack.y - ship.location.y) == 1) {
                            extraTarget.x = ship.attack.x - ship.location.x + ship.attack.x;
                            extraTarget.y = ship.attack.y - ship.location.y + ship.attack.y;
                        }
                    }
                    attacks.add(new AttackReplay(ship.getLocation().cpy(), extraTarget));
                }
            }
        }
    }

    public void render(int player, MapManager mapManager, long timeElapsed) {
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);

        batch.begin();
        if (timeElapsed >= 0 && timeElapsed < moveTime) {
            for (MoveReplay move : moves) {
                if (timeElapsed < moveTime / 2) {
                    batch.draw(ships, move.start.x * 128 + ((move.end.x - move.start.x) * 128) / moveTime * timeElapsed, move.start.y * 128 + ((move.end.y - move.start.y) * 128) / moveTime * timeElapsed, 128, 128, (float) 1 / 8 * move.startDirection, 0.25f + 0.25f * move.team + 0.5f * move.spriteID, (float) 1 / 8 * (move.startDirection + 1), 0.25f * move.team + 0.5f * move.spriteID);
                } else {
                    batch.draw(ships, move.start.x * 128 + ((move.end.x - move.start.x) * 128) / moveTime * timeElapsed, move.start.y * 128 + ((move.end.y - move.start.y) * 128) / moveTime * timeElapsed, 128, 128, (float) 1 / 8 * move.endDirection, 0.25f + 0.25f * move.team + 0.5f * move.spriteID, (float) 1 / 8 * (move.endDirection + 1), 0.25f * move.team + 0.5f * move.spriteID);
                }
                batch.draw(hp, move.start.x * 128 + ((move.end.x - move.start.x) * 128) / moveTime * timeElapsed, move.start.y * 128 + ((move.end.y - move.start.y) * 128) / moveTime * timeElapsed + 96, 128, 32, (float) 1/3 * (move.hp - 1), 0.5f * move.team, (float) 1/3 * move.hp, 0.5f * (move.team - 1));

            }
        } else if (timeElapsed >= moveTime && timeElapsed < attackTime) {
            for (MoveReplay move : moves) {
                batch.draw(ships, move.end.x * 128, move.end.y * 128, 128, 128, (float) 1 / 8 * move.endDirection, 0.25f + 0.25f * move.team + 0.5f * move.spriteID, (float) 1 / 8 * (move.endDirection + 1), 0.25f * move.team + 0.5f * move.spriteID);
                batch.draw(hp, move.end.x * 128, move.end.y * 128 + 96, 128, 32, (float) 1/3 * (move.hp - 1), 0.5f * move.team, (float) 1/3 * move.hp, 0.5f * (move.team - 1));
            } for (AttackReplay attackReplay : attacks) {
                batch.draw(attack, attackReplay.start.x * 128 + ((attackReplay.end.x - attackReplay.start.x) * 128) / (attackTime - moveTime) * (timeElapsed - moveTime),
                        attackReplay.start.y * 128 + ((attackReplay.end.y - attackReplay.start.y) * 128) / (attackTime - moveTime) * (timeElapsed - moveTime), 128, 128);
            }
        } else if (timeElapsed >= attackTime && timeElapsed < explosionTime) {
            for (MoveReplay move : moves) {
                batch.draw(ships, move.end.x * 128, move.end.y * 128, 128, 128, (float) 1 / 8 * move.endDirection, 0.25f + 0.25f * move.team + 0.5f * move.spriteID, (float) 1 / 8 * (move.endDirection + 1), 0.25f * move.team + 0.5f * move.spriteID);
                batch.draw(hp, move.end.x * 128, move.end.y * 128 + 96, 128, 32, (float) 1/3 * (move.hp - 1), 0.5f * move.team, (float) 1/3 * move.hp, 0.5f * (move.team - 1));
            } for (AttackReplay attackReplay : attacks) {
                batch.draw(explosion, attackReplay.end.x * 128, attackReplay.end.y * 128, 128, 128);
            }
        }
        batch.end();
    }

    public void dispose() {
        ships.dispose();
        batch.dispose();
        attack.dispose();
        explosion.dispose();
        hp.dispose();
    }
}
