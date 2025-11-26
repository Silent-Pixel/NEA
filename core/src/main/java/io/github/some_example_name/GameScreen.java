package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {

    public GameScreen(Main game){
        this.main = game;
    }

    private Main main;

    private static boolean IsInitialised = false;

    ShapeRenderer sr = new ShapeRenderer();

    TileMap tileMap = new TileMap();
    PlayerAnimation playerAnimation = new PlayerAnimation();

    @Override
    public void show() {

        if (!IsInitialised){
            tileMap.create();
            playerAnimation.create();
            IsInitialised = true;
        }
    }

    @Override
    public void render(float delta) {

        tileMap.render();
        playerAnimation.render();
        GoToMainMenuOnEsc();
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
        tileMap.dispose();
        playerAnimation.dispose();
    }
}
