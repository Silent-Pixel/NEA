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

    float IdleTime, WalkTime, AttackTime, Attack01CooldownTimer;
    boolean IsMoveKeyPressed, IsAttacking, IsAttack01OnCooldown;
    TextureRegion CurrentFrame;
    TileMap TileMap;
    private final Player Player;

    public PlayerAnimation(Player Player){
        this.Player = Player;
    }

    @Override
    public void create() {
        TileMap = new TileMap();
        batch = new SpriteBatch();

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
        IdleTime = 0f;

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
        WalkTime = 0f;

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
        AttackTime = 0f;
        IsAttacking = false;
        IsAttack01OnCooldown = false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        IdleTime += Gdx.graphics.getDeltaTime();
        WalkTime += Gdx.graphics.getDeltaTime();
        IsMoveKeyPressed = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            Player.setY(Player.getY() + Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            Player.setX(Player.getX() - Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            Player.setY(Player.getY() - Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            Player.setX(Player.getX() + Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            IsAttacking = true;
            AttackTime = 0f;
        }

        if (IsAttack01OnCooldown){
            Attack01CooldownTimer += Gdx.graphics.getDeltaTime();
            if (Attack01CooldownTimer > 2f){
                IsAttack01OnCooldown = false;
                Attack01CooldownTimer = 0f;
            }
        }

        if (IsAttack01OnCooldown){
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
        }

        else if (IsAttacking){
            AttackTime += Gdx.graphics.getDeltaTime();
            CurrentFrame = PlayerAttack01Animation.getKeyFrame(AttackTime, false);
            if (PlayerAttack01Animation.isAnimationFinished(AttackTime)){
                IsAttack01OnCooldown = true;
                AttackTime = 0f;
            }
        }

        else if (IsMoveKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(WalkTime, true);
            AttackTime = 0f;
        }

        else{
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
            AttackTime = 0f;
        }

        batch.begin();
        batch.draw(CurrentFrame, Player.getX(), Player.getY(), (float) (3.5 * 17), (float) (3.5 * 22));
        batch.end();



        /*if (IsKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(WalkTime, true);
        }

        else if (IsAttacking){
            CurrentFrame = PlayerAttack01Animation.getKeyFrame(AttackTime, true);

            if (PlayerAttack01Animation.isAnimationFinished(AttackTime)){
                IsAttacking = false;
            }
        }

        else {
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
        }

        batch.begin();
        if (IsAttacking && !IsKeyPressed){
            batch.draw(CurrentFrame, Player.getX() - 25, Player.getY(), (float) (3.5 * 34), (float) (3.5 * 27));
        }
        else {
            batch.draw(CurrentFrame, Player.getX(), Player.getY(), (float) (3.5 * 17), (float) (3.5 * 22));
        }
        batch.end(); */

        TestingClass();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
