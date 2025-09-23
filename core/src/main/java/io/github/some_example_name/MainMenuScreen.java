package io.github.some_example_name;

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

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("glassy-ui.json"));

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        createMenu();
    }

    private void createMenu() {
        //Displays the game name (currently only "my game") on top of the buttons
        Label titleLabel = new Label("My Game", skin);
        table.add(titleLabel).padBottom(50).row();

        //Creating the play button and starts the game screen class which has the code for the game
        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Changed to the gamescreen to allow the game to start
                ((Main)Gdx.app.getApplicationListener()).StartGame();
            }
        });
        table.add(playButton).width(200).height(60).padBottom(20).row();

        //Creating the ptions button (currently does nothing)
        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Options button clicked!");
            }
        });
        table.add(optionsButton).width(200).height(60).padBottom(20).row();

        //Creating exit button which takes user back to screen when the esc key is pressed and then quits program when the exit button is pressed
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).width(200).height(60).row();
    }

    @Override
    public void render(float delta) {
        //Clears the screen
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
