package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation extends ApplicationAdapter {
    //FrameCols = 6
    //FrameRows = 1

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;
    SpriteBatch batch;

    float StateTime;

    @Override
    public void create() {
        PlayerIdleTile = new Texture(Gdx.files.internal("assets/Soldier-Idle.png"));
        TextureRegion[][] tmp = TextureRegion.split(PlayerIdleTile, PlayerIdleTile.getWidth() / 6, PlayerIdleTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[6];
        int index = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j ++){
                WalkFrame[index++] = tmp[i][j];
            }
        }

        PlayerIdleAnimation = new Animation<>(0.2f, WalkFrame);
        batch = new SpriteBatch();
        StateTime = 0f;
    }

    @Override
    public void render() {
        StateTime += Gdx.graphics.getDeltaTime();
        TextureRegion CurrentFrame = PlayerIdleAnimation.getKeyFrame(StateTime, true);
        batch.begin();
        batch.draw(CurrentFrame, 250, 250 , 350, 350);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        PlayerIdleTile.dispose();
    }
}
