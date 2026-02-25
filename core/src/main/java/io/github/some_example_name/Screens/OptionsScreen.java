package io.github.some_example_name.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OptionsScreen implements Screen {

    public OptionsScreen(MainMenuScreen game){
        this.mainMenuScreen = game;
    }

    private final MainMenuScreen mainMenuScreen;

    private Stage stage;
    private Skin skin;
    private Table table;

    @Override
    public void show() {
        stage = new Stage((new ScreenViewport()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("PlainJamesUI/skin/plain-james-ui.json"));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        CreateMenu();
    }

    public void CreateMenu(){
        Label TitleLabel = new Label("Options", skin);
        table.add(TitleLabel).padBottom(50).row();

        Slider VolumeSlider = new Slider(0, 100, 2, false, skin);
        VolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //something
            }
        });
        table.add(VolumeSlider).width(230).height(100).padBottom(20).row();

        TextButton BackToMainMenu = new TextButton("Back", skin);
        BackToMainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuScreen.setScreen(mainMenuScreen);
            }
        });
        table.add(BackToMainMenu).width(230).height(100).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
