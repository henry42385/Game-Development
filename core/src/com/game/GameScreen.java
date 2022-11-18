package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends Screen {
    private Texture spriteSheet;
    private SpriteBatch batchStatic;
    private SpriteBatch batchDynamic;
    private ShapeRenderer shapeRenderer;
    private Map map;
    private ShipManager shipManager;
    private BitmapFont font;
    private static int playerTurn = 0;
    private int turn = 0;


    public void create() {
        spriteSheet = new Texture("sprites/TerrainSpritesheet2.png");

        batchStatic = new SpriteBatch();
        batchDynamic = new SpriteBatch();
        map = new Map("assets/maps/map1.txt");
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        shipManager = new ShipManager();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    if (playerTurn == 0) {
                        playerTurn = 1;
                    } else {
                        playerTurn = 0;
                        turn += 1;
                        playTurn();
                    } shipManager.setSelectedShip(null);
                }
                return true;
            }
        });
    }

    public void playTurn() {
        for (Ship ship : shipManager.getPlayer1Ships()) {
            ship.play();
        }
        for (Ship ship : shipManager.getPlayer2Ships()) {
            ship.play();
        }
    }

    public void render() {
        DynamicCamera.update();
        batchDynamic.setProjectionMatrix(DynamicCamera.get().combined);

        renderWorld();
        renderHUD();
        updateView();
    }

    private void renderWorld() {
        drawMap();

        shipManager.render();
        drawFog();
        drawGrid();
    }

    private void renderHUD() {
        StaticCamera.update();
        batchStatic.begin();
        font.getData().setScale(2);
        if (playerTurn == 0) {
            font.draw(batchStatic, "Player 2",1300, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 1",100, 750);
        } else {
            font.draw(batchStatic, "Player 1",100, 750);
            font.setColor(1, 0, 0, 1);
            font.draw(batchStatic, "Player 2",1300, 750);
        } font.setColor(1, 1, 1, 1);
        batchStatic.end();
    }

    private void updateView() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            DynamicCamera.moveLeft();
        } if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            DynamicCamera.moveRight();
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            DynamicCamera.moveUp();
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            DynamicCamera.moveDown();
        } if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            DynamicCamera.zoomOut();
        } if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            DynamicCamera.zoomIn();
        }
    }

    private void drawMap() {
        batchDynamic.begin();
        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (drawMap[y][x] == 'D')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 0.25f, 0.25f, 0);
                else if (drawMap[y][x] == 'S')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.25f, 0.25f, 0.5f, 0);
                else if (drawMap[y][x] == 'L')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 0.25f, 0.75f, 0);
                else if (drawMap[y][x] == 'M')
                    batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.75f, 0.25f, 1, 0);
            }
        }
        batchDynamic.end();
    }

    private void drawGrid() {
        Gdx.gl.glLineWidth(3);
        shapeRenderer.setProjectionMatrix(DynamicCamera.get().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        for (int i = 1; i < map.getHeight() - 2; i++) {
            shapeRenderer.line(256, 128 + i * 128, map.getWidth() * 128 - 256, 128 + i * 128);
        }
        for (int i = 1; i < map.getWidth() - 2; i++) {
            shapeRenderer.line(128 + i * 128, 256, 128 + i * 128, map.getHeight() * 128 - 256);
        }
        shapeRenderer.end();
    }

    private void drawFog() {
        batchDynamic.begin();
        char[][] drawMap = map.getMap();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                boolean vision = false;
                if (playerTurn == 0) {
                    for (Ship ship : shipManager.getPlayer1Ships()) {
                        if (x <= ship.getLocation().x + 3 && x >= ship.getLocation().x - 3 &&
                                y <= map.getHeight() - ship.getLocation().y + 2 && y >= map.getHeight() - ship.getLocation().y - 4)
                            vision = true;
                    }
                } else {
                    for (Ship ship : shipManager.getPlayer2Ships()) {
                        if (x <= ship.getLocation().x + 3 && x >= ship.getLocation().x - 3 &&
                                y <= map.getHeight() - ship.getLocation().y + 2 && y >= map.getHeight() - ship.getLocation().y - 4)
                            vision = true;
                    }
                }
                if (!vision) {
                    if (drawMap[y][x] == 'D')
                        batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0, 0.5f, 0.25f, 0.25f);
                    else if (drawMap[y][x] == 'S')
                        batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.25f, 0.5f, 0.5f, 0.25f);
                    else if (drawMap[y][x] == 'L')
                        batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.5f, 0.5f, 0.75f, 0.25f);
                    else if (drawMap[y][x] == 'M')
                        batchDynamic.draw(spriteSheet, x * 128, (map.getHeight() - y - 1) * 128, 128, 128, 0.75f, 0.5f, 1, 0.25f);
                }
            }
        }
        batchDynamic.end();
    }

    public void dispose() {
        spriteSheet.dispose();
        batchDynamic.dispose();
        batchStatic.dispose();
        shapeRenderer.dispose();
        font.dispose();
        shipManager.dispose();
    }

    public static int getPlayerTurn() {
        return playerTurn;
    }
}
