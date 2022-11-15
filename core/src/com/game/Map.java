package com.game;

import java.io.File;
import java.util.Scanner;

public class Map {
    private char[][] map;
    private int height;
    private int width;

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
}
