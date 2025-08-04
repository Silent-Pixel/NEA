package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    ShapeRenderer sr;
    Player player;
    Maps[][] maps;

    SpriteBatch batch;
    Texture backgroundTexture;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        maps = new Maps[5][5];

        batch = new SpriteBatch();
        backgroundTexture = new Texture("worlds/world_1.png");

        //create_grid();

        player = new Player(960, 540, 25, 25, Color.LIME);
    }

    public void create_grid(){
        for (int i = 0; i < maps.length; i++){
            for (int j = 0; j < maps[i].length; j++){
                maps[i][j] = new Maps(0, 0, false);
            }
        }
    }

    @Override
    public void render() {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        //Rendering
        render_player();

        //Movement
        player_movement();

        quit_on_esc();
    }

    public void render_player(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(player.getColour());
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
    }

    public void player_movement(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.setY(player.getY() + (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.setX(player.getX() - (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.setY(player.getY() - (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setX(player.getX() + (250 * Gdx.graphics.getDeltaTime()));
        }
    }

    public void quit_on_esc() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(-1);
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        backgroundTexture.dispose();

    }
}
