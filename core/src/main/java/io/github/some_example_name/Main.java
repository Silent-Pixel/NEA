package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    ShapeRenderer sr;

    Player player;

    @Override
    public void create() {
        sr = new ShapeRenderer();

        player = new Player(960, 540, 25, 25, Color.LIME);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        render_player();

        quit_on_esc();
    }

    public void render_player(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(player.getColour());
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
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
    }
}
