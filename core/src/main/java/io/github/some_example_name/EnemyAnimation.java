package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyAnimation implements ApplicationListener {

    Animation<TextureRegion> EnemyIdleAnimation;
    Texture EnemyIdleTile;

    Animation<TextureRegion> EnemyWalkAnimation;
    Texture EnemyWalkTile;

    SpriteBatch batch;
    TextureRegion CurrentFrame;
    Enemy Enemy;
    float IdleTime, WalkTime;

    private Player Player;

    public EnemyAnimation(Player Player){
        this.Player = Player;
    }

    @Override
    public void create() {
        Enemy = new Enemy();

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

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void VeryBasicMovement(){
        batch.begin();
        if (Enemy.getX() < Player.getX()- 10){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setX(Enemy.getX() + Enemy.getSpeed());
        }
        if (Enemy.getX() > Player.getX() + 10){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setX(Enemy.getX() - Enemy.getSpeed());
        }
        if (Enemy.getY() < Player.getY() - 10){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setY(Enemy.getY() + Enemy.getSpeed());
        }
        if (Enemy.getY() > Player.getY() + 10){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Enemy.setY(Enemy.getY() - Enemy.getSpeed());
        }
        else {
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
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
