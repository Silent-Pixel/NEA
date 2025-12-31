package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;
import java.util.Map;

public class TileMap extends ApplicationAdapter {

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture tileset;
    TextureRegion[][] tiles;
    int[][] BaseLevel = new int[][]{
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
        {20, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 11, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
        {20, 106, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 111, 105},
        {120, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 121, 125}
    };;

    Map<GridPoint2, int[][]> LevelMap = new HashMap<>();
    GridPoint2 CurrentLevelCord = new GridPoint2(0, 0);;
    int[][] CurrentLevel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        tileset = new Texture(Gdx.files.internal("assets/AssetsForMaps.png"));
        tiles = TextureRegion.split(tileset, 32, 32);

        LevelMap.put(CurrentLevelCord, BaseLevel);
        CurrentLevel = LevelMap.get(CurrentLevelCord);

        if (LevelMap.containsKey(CurrentLevelCord)){
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("0 0 already exists");
        }
        else {
            LevelMap.put(CurrentLevelCord, BaseLevel);
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("0 0 was made again");
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int i = 0; i < CurrentLevel.length; i++){
            for (int j = 0; j < CurrentLevel[i].length; j++){
                int tileX = CurrentLevel[i][j] % tiles[0].length; // column in the tileset
                int tileY = CurrentLevel[i][j] / tiles[0].length; // row in the tileset

                batch.draw(tiles[tileY][tileX], j * 64, (CurrentLevel.length - 1 - i) * 64, 64, 64);
            }
        }
        batch.end();
    }

    public void TestingClassUp(){
        CurrentLevelCord.add(0, 1);
        if (LevelMap.containsKey(CurrentLevelCord)){
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("Up if statement run");
            System.out.println("Currently in: " + CurrentLevelCord);
        }
        else {
            int[][] NewLevel = BaseLevel;
            LevelMap.put(CurrentLevelCord, NewLevel);
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("Up else statement run");
            System.out.println("Currently in: " + CurrentLevelCord);
        }
    }

    public void TestingClassDown(){
        CurrentLevelCord.add(0, -1);
        if (LevelMap.containsKey(CurrentLevelCord)){
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("Down if statement run");
            System.out.println("Currently in: " + CurrentLevelCord);
        }
        else {
            int[][] NewLevel = BaseLevel;
            LevelMap.put(CurrentLevelCord, NewLevel);
            CurrentLevel = LevelMap.get(CurrentLevelCord);
            System.out.println("Down else statement run");
            System.out.println("Currently in: " + CurrentLevelCord);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        tileset.dispose();
    }
}
