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
    LevelSystem LevelSystem = new LevelSystem();
    PlayerAnimation PlayerAnimation = new PlayerAnimation(Player);
    EnemyAnimation EnemyAnimation = new EnemyAnimation(Player, LevelSystem);

    @Override
    public void show() {

        if (!IsInitialised){
            LevelSystem.create();
            PlayerAnimation.create();
            EnemyAnimation.create();
            IsInitialised = true;
        }
    }

    @Override
    public void render(float delta) {
        LevelSystem.render();
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
        LevelSystem.dispose();
        PlayerAnimation.dispose();
        EnemyAnimation.dispose();
    }
}
