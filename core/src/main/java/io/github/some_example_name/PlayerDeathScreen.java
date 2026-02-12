package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerDeathScreen extends Game implements Screen  {

    private final MainMenuScreen mainMenuScreen;
    private final GameScreen gameScreen;
    private final Player Player;

    public PlayerDeathScreen(MainMenuScreen game, GameScreen game1, Player player){
        this.mainMenuScreen = game;
        this.gameScreen = game1;
        Player = player;
    }

    private Stage stage;
    private Skin skin;
    private Table table;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("PlainJamesUI/skin/plain-james-ui.json"));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        CreateMenu();
    }

    private void CreateMenu() {
        Label TitleLabel = new Label("You Died!", skin);
        table.add(TitleLabel).padBottom(50).row();

        TextButton PlayAgainButton = new TextButton("Play Again", skin);
        PlayAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuScreen.setScreen(mainMenuScreen);
            }
        });
        table.add(PlayAgainButton).width(230).height(100).padBottom(20).row();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
