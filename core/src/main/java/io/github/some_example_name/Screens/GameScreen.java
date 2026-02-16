package io.github.some_example_name.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import io.github.some_example_name.Characters.Enemy;
import io.github.some_example_name.Characters.EnemyAnimation;
import io.github.some_example_name.Characters.Player;
import io.github.some_example_name.Characters.PlayerAnimation;
import io.github.some_example_name.Levels.LevelSystem;

public class GameScreen extends Game implements Screen {

    public GameScreen(MainMenuScreen game, PlayerDeathScreen game1){
        this.mainMenuScreen = game;
        this.playerDeathScreen = game1;
    }

    private final MainMenuScreen mainMenuScreen;
    private final PlayerDeathScreen playerDeathScreen;

    private boolean IsInitialised = false;

    Player Player = new Player(800, 250);
    Enemy[] Enemies = {
        new Enemy(500, 500),
        new Enemy(700, 500)
    };
    LevelSystem LevelSystem = new LevelSystem(Player);
    PlayerAnimation PlayerAnimation = new PlayerAnimation(Player, Enemies);
    EnemyAnimation EnemyAnimation = new EnemyAnimation(Player, LevelSystem, PlayerAnimation, Enemies);

    @Override
    public void show() {

        if (!IsInitialised){
            PlayerAnimation.setEnemyAnimation(EnemyAnimation);
            LevelSystem.create();
            PlayerAnimation.create();
            EnemyAnimation.create();
            IsInitialised = true;
        }
    }

    @Override
    public void render(float delta) {
        LevelSystem.render();
        if (Player.getHealth() <= 0){
            mainMenuScreen.setScreen(new PlayerDeathScreen(mainMenuScreen, this, Player));
        }
        else {
            PlayerAnimation.render();
        }
        EnemyAnimation.render();
        GoToMainMenuOnEsc();
        LevelSystem.LevelBoundaries();
    }

    public void GoToMainMenuOnEsc() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            mainMenuScreen.setScreen(mainMenuScreen);
        }
    }

    @Override
    public void create() {

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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }
}
