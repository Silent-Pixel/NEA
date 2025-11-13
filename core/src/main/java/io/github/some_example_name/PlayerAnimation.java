package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import org.w3c.dom.Text;

public class PlayerAnimation extends ApplicationAdapter {
    //FrameCols = 6
    //FrameRows = 1

    Animation<TextureRegion> PlayerWalkAnimation;
    Texture WalkingTile;
    SpriteBatch batch;

    float StateTime;

    @Override
    public void create() {
        WalkingTile = new Texture(Gdx.files.internal("assets/Soldier-Idle.png"));
        TextureRegion[][] tmp = TextureRegion.split(WalkingTile, WalkingTile.getWidth() / 6, WalkingTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[6];
        int index = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j ++){
                WalkFrame[index++] = tmp[i][j];
            }
        }

        PlayerWalkAnimation = new Animation<TextureRegion>(0.2f, WalkFrame);
        batch = new SpriteBatch();
        StateTime = 0f;
    }

    @Override
    public void render() {
        StateTime += Gdx.graphics.getDeltaTime();
        TextureRegion CurrentFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
        batch.begin();
        batch.draw(CurrentFrame, 250, 250 , 350, 350);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        WalkingTile.dispose();
    }
}
