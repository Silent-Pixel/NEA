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

    float StateTime, PlayerX, PlayerY;

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
    }

    @Override
    public void render() {
        StateTime += Gdx.graphics.getDeltaTime();

        batch.begin();

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            TextureRegion CurrentWalkFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
            batch.draw(CurrentWalkFrame, PlayerX, PlayerY, 350, 350);
            PlayerX = PlayerX + (300 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            TextureRegion CurrentWalkFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
            batch.draw(CurrentWalkFrame, PlayerX, PlayerY, 350, 350);
            PlayerX = PlayerX - (300 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            TextureRegion CurrentWalkFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
            batch.draw(CurrentWalkFrame, PlayerX, PlayerY, 350, 350);
            PlayerY = PlayerY + (300 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            TextureRegion CurrentWalkFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
            batch.draw(CurrentWalkFrame, PlayerX, PlayerY, 350, 350);
            PlayerY = PlayerY - (300 * Gdx.graphics.getDeltaTime());
        }

        else {
            TextureRegion CurrentIdleFrame = PlayerIdleAnimation.getKeyFrame(StateTime, true);
            batch.draw(CurrentIdleFrame, PlayerX, PlayerY , 350, 350);
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
