package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.xml.soap.Text;

public class MapManager {
    private Texture spriteSheet;
    private Map map;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture danger;
    public int dangerX1;
    public int dangerX2;
    public int dangerY1;
    public int dangerY2;

    public MapManager() {
        create();
    }

    public void create() {
        loadMap("maps/map2.txt");
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.spriteSheet = new Texture("sprites/TerrainSpritesheet2.png");
        this.danger = new Texture("sprites/danger.png");
    }

    public void loadMap(String path) {
        this.map = new Map(path);
        dangerX1 = 2;
        dangerX2 = map.getWidth() - 3;
        dangerY1 = 2;
        dangerY2 = map.getHeight() - 3;
    }

    public void renderMap() {
        DynamicCamera.get().update();
        batch.setProjectionMatrix(DynamicCamera.get().combined);
        batch.begin();
        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (drawMap[y][x] == 'D')
                    batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 0.25f, 0.25f, 0);
                else if (drawMap[y][x] == 'S')
                    batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.25f, 0.25f, 0.5f, 0);
                else if (drawMap[y][x] == 'L')
                    batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 0.25f, 0.75f, 0);
                else if (drawMap[y][x] == 'M')
                    batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.75f, 0.25f, 1, 0);
            }
        }
        batch.end();
    }

    public void renderGrid() {
        Gdx.gl.glLineWidth(3);
        shapeRenderer.setProjectionMatrix(DynamicCamera.get().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        for (int i = -1; i < map.getHeight(); i++) {
            shapeRenderer.line(0, 128 + i * 128, map.getWidth() * 128, 128 + i * 128);
        }
        for (int i = -1; i < map.getWidth(); i++) {
            shapeRenderer.line(128 + i * 128, 0, 128 + i * 128, map.getHeight() * 128);
        }
        shapeRenderer.end();
    }

    public void renderFog(int player, ShipManager sm) {
        batch.begin();
        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                boolean vision = false;
                for (Ship ship : sm.getShips()) {
                    if (ship.player == player - 1) {
                        if (ship.getAfterimage() == null) {
                            if (x <= ship.getLocation().x + 3 && x >= ship.getLocation().x - 3 &&
                                    y <= map.getHeight() - ship.getLocation().y + 2 && y >= map.getHeight() - ship.getLocation().y - 4)
                                vision = true;
                        } else {
                            if (x <= ship.getAfterimage().x + 3 && x >= ship.getAfterimage().x - 3 &&
                                    y <= map.getHeight() - ship.getAfterimage().y + 2 && y >= map.getHeight() - ship.getAfterimage().y - 4)
                                vision = true;
                        }
                    }
                }
                if (!vision) {
                    if (drawMap[y][x] == 'D')
                        batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 0.5f, 0.25f, 0.25f);
                    else if (drawMap[y][x] == 'S')
                        batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.25f, 0.5f, 0.5f, 0.25f);
                    else if (drawMap[y][x] == 'L')
                        batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 0.5f, 0.75f, 0.25f);
                    else if (drawMap[y][x] == 'M')
                        batch.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.75f, 0.5f, 1, 0.25f);
                }
            }
        }
        batch.end();
    }

    public void renderBorder() {
        batch.begin();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (i < dangerX1 || i > dangerX2 || j < dangerY1 || j > dangerY2) {
                    batch.draw(danger, i * 128, j * 128, 128, 128);
                }
            }
        }
        batch.end();
    }

    public void dispose() {
        spriteSheet.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        danger.dispose();
    }
}