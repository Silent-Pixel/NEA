package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileMap extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture tileset;
    private TextureRegion[][] tiles;
    private int[][] BaseLevel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        tileset = new Texture(Gdx.files.internal("assets/AssetsForMaps.png"));
        tiles = TextureRegion.split(tileset, 32, 32);
        BaseLevel = new int[][]{
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 42,43, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
            {20, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 62, 63, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 11, 105},
            {20, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 82, 83, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 51, 105},
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
        };

        /*for (int i = 0; i < BaseLevel.length; i++){
            for (int j = 0; j < BaseLevel[i].length; j++){
                if (BaseLevel[i][j] == 47){
                    BaseLevel[i][j] = 12;
                }
            }
        }*/
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int i = 0; i < BaseLevel.length; i++){
            for (int j = 0; j < BaseLevel[i].length; j++){
                int tileX = BaseLevel[i][j] % tiles[0].length; // column in the tileset
                int tileY = BaseLevel[i][j] / tiles[0].length; // row in the tileset

                batch.draw(tiles[tileY][tileX], j * 64, (BaseLevel.length - 1 - i) * 64, 64, 64);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tileset.dispose();
    }
}
