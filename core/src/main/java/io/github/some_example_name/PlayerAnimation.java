package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation implements ApplicationListener {

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;

    Animation<TextureRegion> PlayerWalkAnimation;
    Texture PlayerWalkTile;

    Animation<TextureRegion> PlayerAttack01Animation;
    Texture PlayerAttack01Tile;

    SpriteBatch batch;

    float WalkTime, AttackTime, PlayerSpeed;
    boolean IsKeyPressed, IsAttacking;
    TextureRegion CurrentFrame;
    TileMap TileMap;
    private final Player Player;

    public PlayerAnimation(Player Player){
        this.Player = Player;
    }

    @Override
    public void create() {
        TileMap = new TileMap();

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

        PlayerAttack01Tile = new Texture(Gdx.files.internal("assets/Soldier/Soldier-Attack01.png"));
        TextureRegion[][] PlayerAttack01TextureRegion = TextureRegion.split(PlayerAttack01Tile, PlayerAttack01Tile.getWidth() / 6, PlayerAttack01Tile.getHeight());
        TextureRegion[] Attack01Frame = new TextureRegion[6];
        int Attack01Index = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j++){
                Attack01Frame[Attack01Index++] = PlayerAttack01TextureRegion[i][j];
            }
        }
        PlayerAttack01Animation = new Animation<>(0.1f, Attack01Frame);


        batch = new SpriteBatch();
        WalkTime = 0f;
        AttackTime = 0f;
        IsAttacking = false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        Movement();
        TestingClass();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void Movement(){
        WalkTime += Gdx.graphics.getDeltaTime();
        AttackTime += Gdx.graphics.getDeltaTime();
        IsKeyPressed = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            Player.setY(Player.getY() + Player.getSpeed());
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            Player.setX(Player.getX() - Player.getSpeed());
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            Player.setY(Player.getY() - Player.getSpeed());
            IsKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            Player.setX(Player.getX() + Player.getSpeed());
            IsKeyPressed = true;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            IsAttacking = true;
            AttackTime = 0f;
        }

        if (IsKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(WalkTime, true);
        }

        else if (IsAttacking){
            CurrentFrame = PlayerAttack01Animation.getKeyFrame(AttackTime, true);

            if (PlayerAttack01Animation.isAnimationFinished(AttackTime)){
                IsAttacking = false;
            }
        }

        else {
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(WalkTime, true);
        }

        batch.begin();
        if (IsAttacking && !IsKeyPressed){
            batch.draw(CurrentFrame, Player.getX() - 25, Player.getY(), (float) (3.5 * 34), (float) (3.5 * 27));
        }
        else {
            batch.draw(CurrentFrame, Player.getX(), Player.getY(), (float) (3.5 * 17), (float) (3.5 * 22));
        }
        batch.end();
    }

    public void TestingClass(){
        if (Player.getY() + 77 > Gdx.graphics.getHeight()) {
            TileMap.TestingClassUp();
            Player.setY(100);
        }

        else if (Player.getY() < 0){
            TileMap.TestingClassDown();
            Player.setY(900);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        PlayerIdleTile.dispose();
        PlayerWalkTile.dispose();
    }


}
