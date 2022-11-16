package com.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private char[][] map;
    private int height;
    private int width;
    private ArrayList<Ship> player1Ships = new ArrayList<>();
    private ArrayList<Ship> player2Ships = new ArrayList<>();

    public Map(String path) {
        try {
            File mapFile = new File(path);
            Scanner scanner = new Scanner(mapFile);
            width = Integer.parseInt(scanner.nextLine());
            height = Integer.parseInt(scanner.nextLine());
            map = new char[height][width];
            for (int i = 0; i < height; i++) {
                map[i] = scanner.nextLine().toCharArray();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        player1Ships.add(new Destroyer(4, 4, 2));
        player2Ships.add(new Destroyer(16, 4, 6));
    }

    public char[][] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Ship> getPlayer1Ships() {
        return player1Ships;
    }

    public ArrayList<Ship> getPlayer2Ships() {
        return player2Ships;
    }
}
