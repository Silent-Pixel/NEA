package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation extends ApplicationAdapter {

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;

    Animation<TextureRegion> PlayerWalkAnimation;
    Texture PlayerWalkTile;

    SpriteBatch batch;

    float StateTime, PlayerX, PlayerY, PlayerSpeed;
    int PlayerW;
    boolean IsKeyPressed;
    TextureRegion CurrentFrame;

    @Override
    public void create() {
        PlayerIdleTile = new Texture(Gdx.files.internal("assets/Soldier/Soldier-Idle.png"));
        TextureRegion[][] PlayerIdleTextureRegion = TextureRegion.split(PlayerIdleTile, PlayerIdleTile.getWidth() / 6, PlayerIdleTile.getHeight());
        TextureRegion[] IdleFrame = new TextureRegion[6];
        int IdleIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j ++){
                IdleFrame[IdleIndex++] = PlayerIdleTextureRegion[i][j];
            }
        }
        PlayerIdleAnimation = new Animation<>(0.2f, IdleFrame);

        PlayerWalkTile = new Texture(Gdx.files.internal("assets/Soldier/Soldier-Walk.png"));
        TextureRegion[][] PlayerWalkTextureRegion = TextureRegion.split(PlayerWalkTile, PlayerWalkTile.getWidth() / 8, PlayerWalkTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[8];
        int WalkIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 8; j++){
                WalkFrame[WalkIndex++] = PlayerWalkTextureRegion[i][j];
            }
        }
        PlayerWalkAnimation = new Animation<>(0.08f, WalkFrame);

        batch = new SpriteBatch();
        StateTime = 0f;
        PlayerX = 250;
        PlayerY = 250;
        PlayerW = 350;
    }

    @Override
    public void render() {
        StateTime += Gdx.graphics.getDeltaTime();
        PlayerSpeed = 300 * Gdx.graphics.getDeltaTime();
        IsKeyPressed = false;


        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            PlayerY += PlayerSpeed;
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            PlayerX -= PlayerSpeed;
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            PlayerY -= PlayerSpeed;
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            PlayerX += PlayerSpeed;
            IsKeyPressed = true;
        }

        if (IsKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
        }
        else {
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(StateTime, true);
        }

        batch.begin();
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            batch.draw(CurrentFrame, PlayerX + 51, PlayerY, (float) -(3.5 * 17), (float) (3.5 * 22));
        }
        else {
            batch.draw(CurrentFrame, PlayerX, PlayerY, (float) (3.5 * 17), (float) (3.5 * 22));
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        PlayerIdleTile.dispose();
        PlayerWalkTile.dispose();
    }


}
