package io.github.some_example_name.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends Game implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private GameScreen gameScreen;
    private SpriteBatch batch;
    private Texture BackgroundImage;
    private ShapeRenderer sr;

    @Override
    public void create() {
        show();
        setScreen(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("PlainJamesUI/skin/plain-james-ui.json"));
        BackgroundImage = new Texture(Gdx.files.internal("MainMenuBackground.png"));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        CreateMenu();
    }

    private void CreateMenu() {

        Label TitleLabel = new Label("Pixel Crawler", skin);
        TitleLabel.setFontScale(3);
        table.add(TitleLabel).padBottom(50).row();

        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameScreen == null || gameScreen.Player.getHealth() <= 0){
                    gameScreen = new GameScreen(MainMenuScreen.this, null);
                }
                setScreen(gameScreen);

            }
        });
        table.add(playButton).width(230).height(100).padBottom(20).row();

        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new OptionsScreen(MainMenuScreen.this));
            }
        });
        table.add(optionsButton).width(230).height(100).padBottom(20).row();

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).width(230).height(100).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(BackgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1, 1, 1, 0.2f));
        sr.rect(710, 0, 500, 1080);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
