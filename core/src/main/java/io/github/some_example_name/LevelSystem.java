package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelSystem extends ApplicationAdapter {

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture tileset;
    TextureRegion[][] tiles;
    int[][] CurrentLevel;
    ArrayList<int[][]> AllLevels;
    LevelGrid LevelGrid;
    GridCell GridCell;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        tileset = new Texture(Gdx.files.internal("assets/AssetsForMaps.png"));
        tiles = TextureRegion.split(tileset, 32, 32);
        AllLevels = ReadInLevelsFromFile("assets/Level2DArrays.txt");
        LevelGrid = new LevelGrid(AllLevels);
        GridCell = LevelGrid.FindCell(0, 0);
        if (GridCell != null){
            CurrentLevel = GridCell.getLevel2DArray();
        }
    }

    public ArrayList<int[][]> ReadInLevelsFromFile(String FileName){
        ArrayList<int[][]> Levels = new ArrayList<>();
        try {
            Scanner sc = new Scanner(Gdx.files.internal(FileName).read());
            while (sc.hasNextInt()){
                ArrayList<int[]> rows = new ArrayList<>();
                while (sc.hasNextInt()){
                    String line = sc.nextLine().trim();
                    if (line.isEmpty()){
                        break;
                    }

                    String[] tokens = line.split("\\s+");
                    int[] row = new int[tokens.length];
                    for (int i = 0; i < tokens.length; i++){
                        row[i] = Integer.parseInt(tokens[i]);
                    }
                    rows.add(row);
                }
                if (!rows.isEmpty()){
                    int[][] level = new int[rows.size()][];
                    for (int i = 0; i < rows.size(); i++){
                        level[i] = rows.get(i);
                    }
                    Levels.add(level);
                }
            }
            sc.close();
            System.out.println("Loaded " + Levels.size() + " levels from file");
        }
        catch (Exception e){
            System.err.println("Error loading levels from file: " + e.getMessage());
            e.printStackTrace();
        }
        return Levels;
    }

    public void SetLevel(int LevelIndex){
        if (LevelIndex >= 0 && LevelIndex < AllLevels.size()){
            CurrentLevel = AllLevels.get(LevelIndex);
            System.out.println("Switched to level " + LevelIndex);
        }
        else {
            System.err.println("Invalid Level index: " + LevelIndex);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        if (CurrentLevel != null) {
            for (int i = 0; i < CurrentLevel.length; i++) {
                for (int j = 0; j < CurrentLevel[i].length; j++) {
                    int tileX = CurrentLevel[i][j] % tiles[0].length; // column in the tileset
                    int tileY = CurrentLevel[i][j] / tiles[0].length; // row in the tileset

                    batch.draw(tiles[tileY][tileX], j * 64, (CurrentLevel.length - 1 - i) * 64, 64, 64);
                }
            }
        }
        batch.end();
    }

    public int[][] getCurrentLevel(){
        return CurrentLevel;
    }

    @Override
    public void dispose() {
        batch.dispose();
        tileset.dispose();
    }
}
