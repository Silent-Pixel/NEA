package io.github.some_example_name.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private OptionsScreen optionsScreen;
    private SpriteBatch batch;
    private Texture BackgroundImage;
    private ShapeRenderer sr;
    private BitmapFont font;

    Animation<TextureRegion> EnemyIdleAnimation;
    Texture EnemyIdleTile;

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;

    float PlayerIdleTime, EnemyIdleTime1, EnemyIdleTime2;

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
        font = new BitmapFont();

        CreateMenu();
        CreateAnimation();
    }

    private void CreateMenu() {

        Label TitleLabel = new Label("Pixel Crawler", skin);
        TitleLabel.setFontScale(3);
        table.add(TitleLabel).padBottom(50).row();

        TextButton playButton = new TextButton("Play", skin);
        //playButton.getLabel().setFontScale(2);
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
                if (optionsScreen == null){
                    optionsScreen = new OptionsScreen(MainMenuScreen.this);
                }
                setScreen(optionsScreen);
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

    public void CreateAnimation(){
        EnemyIdleTile = new Texture(Gdx.files.internal("Enemy/Slime_Idle_Angry.png"));
        TextureRegion[][] EnemyIdleTextureRegion = TextureRegion.split(EnemyIdleTile, EnemyIdleTile.getWidth() / 4, EnemyIdleTile.getHeight());
        TextureRegion[] EnemyIdleFrame = new TextureRegion[4];
        int EnemyIdleIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 4; j++){
                EnemyIdleFrame[EnemyIdleIndex++] = EnemyIdleTextureRegion[i][j];
            }
        }
        EnemyIdleAnimation = new Animation<>(0.2f, EnemyIdleFrame);
        EnemyIdleTime1 = 0f;
        EnemyIdleTime2 = 1f;

        PlayerIdleTile = new Texture(Gdx.files.internal("Soldier/Soldier-Idle.png"));
        TextureRegion[][] PlayerIdleTextureRegion = TextureRegion.split(PlayerIdleTile, PlayerIdleTile.getWidth() / 6, PlayerIdleTile.getHeight());
        TextureRegion[] PlayerIdleFrame = new TextureRegion[6];
        int PlayerIdleIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j ++){
                PlayerIdleFrame[PlayerIdleIndex++] = PlayerIdleTextureRegion[i][j];
            }
        }
        PlayerIdleAnimation = new Animation<>(0.2f, PlayerIdleFrame);
        PlayerIdleTime = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        PlayerIdleTime += Gdx.graphics.getDeltaTime();
        EnemyIdleTime1 += Gdx.graphics.getDeltaTime();
        EnemyIdleTime2 += Gdx.graphics.getDeltaTime();

        batch.begin();
        batch.draw(BackgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1, 1, 1, 0.2f));
        sr.rect(710, 0, 500, 1080);
        sr.rect(1920 - 400, 0, 400, 170);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        font.getData().setScale(2f);
        font.draw(batch, "CONTROLS", 1550, 145);
        font.draw(batch, "WASD: Player Movement", 1550, 100);
        font.draw(batch, "LMB: Player Attack", 1550, 65);

        batch.draw(EnemyIdleAnimation.getKeyFrame(EnemyIdleTime1, true), 400, 350, 2 * 32, 2 * 32);
        batch.draw(EnemyIdleAnimation.getKeyFrame(EnemyIdleTime2, true), 260, 610, 2 * 32, 2 * 32);
        batch.draw(PlayerIdleAnimation.getKeyFrame(PlayerIdleTime, true), 1500, 500, (float) -(3.5 * 17), (float) (3.5 * 22));
        batch.end();

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
