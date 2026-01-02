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

    Player Player = new Player();
    TileMap TileMap = new TileMap();
    PlayerAnimation PlayerAnimation = new PlayerAnimation(Player);
    EnemyAnimation EnemyAnimation = new EnemyAnimation(Player, TileMap);

    @Override
    public void show() {

        if (!IsInitialised){
            TileMap.create();
            PlayerAnimation.create();
            EnemyAnimation.create();
            IsInitialised = true;
        }
    }

    @Override
    public void render(float delta) {
        TileMap.render();
        PlayerAnimation.render();
        EnemyAnimation.render();
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
        TileMap.dispose();
        PlayerAnimation.dispose();
        EnemyAnimation.dispose();
    }
}
