package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

public class GameScreen implements Screen {

    public GameScreen(Main game){
        this.main = game;
    }

    private Main main;

    private static final Enemy enemy = new Enemy(960, 340, 25, 25, Color.DARK_GRAY);

    private static Texture[][] BackgroundTexture = new Texture[5][5];
    private static boolean TexturesInitialised = false;

    ShapeRenderer sr = new ShapeRenderer();
    SpriteBatch batch = new SpriteBatch();

    TileMap tileMap = new TileMap();
    PlayerAnimation playerAnimation = new PlayerAnimation();

    @Override
    public void show() {

        if (!TexturesInitialised){
            tileMap.create();
            playerAnimation.create();
            TexturesInitialised = true;
        }
    }

    @Override
    public void render(float delta) {

        tileMap.render();
        playerAnimation.render();
        RenderEnemy();
        GoToMainMenuOnEsc();
    }

    public void RenderEnemy(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(enemy.getColour());
        sr.rect(enemy.getX(), enemy.getY(), enemy.getW(), enemy.getH());
        sr.end();
    }


    public void GoToMainMenuOnEsc() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            main.setScreen(new MainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        for (Texture[] textures : BackgroundTexture) {
            for (Texture texture : textures) {
                texture.dispose();
            }
        }
        tileMap.dispose();
        playerAnimation.dispose();
    }
}
