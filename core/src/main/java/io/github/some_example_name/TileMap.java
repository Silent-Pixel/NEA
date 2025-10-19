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

    private int[][] leveltest = {
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
        {20},
        {20},
        {20},
        {20},
        {20},
        {20},
        {20}
    };

    private final int TileSize = 32;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        tileset = new Texture(Gdx.files.internal("assets/AssetsForMaps.png"));
        tiles = TextureRegion.split(tileset, TileSize, TileSize);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < leveltest.length; i++){
            for (int j = 0; j < leveltest[i].length; j++){
                batch.draw(tiles[i][j], j * TileSize, (leveltest.length - 1 - i) * TileSize);
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
