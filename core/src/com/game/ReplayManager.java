package com.game;

import java.util.ArrayList;

public class ReplayManager {
    private ArrayList<MoveReplay> moves;
//    private ArrayList<AttackReplay> attacks;

    public ReplayManager() {
        create();
    }

    public void create() {

    }

    public void setReplay(ArrayList<Ship> team1Ships, ArrayList<Ship> team2Ships) {
        moves = new ArrayList<>();
//        attacks = new ArrayList<>();

        for (Ship ship : team1Ships) {
            if (ship.getAfterimage() != null) {
                moves.add(new MoveReplay(ship.getAfterimage(), ship.getLocation(), ship.getDirection(), 0));
            }
        } for (Ship ship : team2Ships) {
            if (ship.getAfterimage() != null) {
                moves.add(new MoveReplay(ship.getAfterimage(), ship.getLocation(), ship.getDirection(), 1));
            }
        }
    }

    public void playReplay(int player) {
        
    }

    public void render() {

    }

    public void dispose() {

    }
}
