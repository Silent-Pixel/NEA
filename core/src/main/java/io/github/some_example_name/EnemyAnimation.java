package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyAnimation extends ApplicationAdapter {

    Animation<TextureRegion> EnemyIdleAnimation;
    Texture EnemyIdleTile;

    Animation<TextureRegion> EnemyWalkAnimation;
    Texture EnemyWalkTile;

    SpriteBatch batch;
    TextureRegion CurrentFrame;
    Enemy Enemy;
    Player Player;
    float IdleTime, WalkTime;

    @Override
    public void create() {
        Enemy = new Enemy(500, 500, 350, 350);

        EnemyIdleTile = new Texture(Gdx.files.internal("assets/Enemy/Slime_Idle_Angry.png"));
        TextureRegion[][] EnemyIdleTextureRegion = TextureRegion.split(EnemyIdleTile, EnemyIdleTile.getWidth() / 4, EnemyIdleTile.getHeight());
        TextureRegion[] IdleFrame = new TextureRegion[4];
        int IdleIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 4; j++){
                IdleFrame[IdleIndex++] = EnemyIdleTextureRegion[i][j];
            }
        }
        EnemyIdleAnimation = new Animation<>(0.2f, IdleFrame);

        EnemyWalkTile = new Texture(Gdx.files.internal("assets/Enemy/Slime_jumpWalk_Angry.png"));
        TextureRegion[][] EnemyWalkTextureRegion = TextureRegion.split(EnemyWalkTile, EnemyWalkTile.getWidth() / 5, EnemyWalkTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[5];
        int WalkIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                WalkFrame[WalkIndex++] = EnemyWalkTextureRegion[i][j];
            }
        }
        EnemyWalkAnimation = new Animation<>(0.09f, WalkFrame);

        batch = new SpriteBatch();
        IdleTime = 0f;
        WalkTime = 0f;
    }

    public EnemyAnimation(Player Player){
        this.Player = Player;
    }

    @Override
    public void render() {
        IdleTime += Gdx.graphics.getDeltaTime();
        WalkTime += Gdx.graphics.getDeltaTime();

        /*batch.begin();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setX(Enemy.getX() + Enemy.getSpeed());
        }
        else{
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
        }

        batch.draw(CurrentFrame, Enemy.getX(), Enemy.getY(), 2 * 32, 2 * 32);
        batch.end(); */

        VeryBasicMovement();
    }

    public void VeryBasicMovement(){
        batch.begin();
        if (Enemy.getX() < Player.getX()){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setX(Enemy.getX() + Enemy.getSpeed());
        }
        batch.draw(CurrentFrame, Enemy.getX(), Enemy.getY(), 2 * 32, 2 * 32);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        EnemyIdleTile.dispose();
        EnemyWalkTile.dispose();
    }
}
