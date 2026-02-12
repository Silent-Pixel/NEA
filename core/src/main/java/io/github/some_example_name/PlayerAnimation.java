package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

public class PlayerAnimation implements ApplicationListener {

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;

    Animation<TextureRegion> PlayerWalkAnimation;
    Texture PlayerWalkTile;

    Animation<TextureRegion> PlayerAttack01Animation;
    Texture PlayerAttack01Tile;

    Animation<TextureRegion> PlayerDamageAnimation;
    Texture PlayerDamageTile;

    SpriteBatch batch;

    float IdleTime, WalkTime, AttackTime, Attack01CooldownTimer, DamageTime;
    boolean IsMoveKeyPressed, IsAttacking, IsAttack01OnCooldown, IsDamageTaken;
    TextureRegion CurrentFrame;
    private final Player Player;
    String LastDirection;
    Enemy[] Enemies;

    public PlayerAnimation(Player Player, Enemy[] Enemies){
        this.Player = Player;
        this.Enemies = Enemies;
    }

    public void setIsDamageTaken(boolean IsDamageTaken) {
        this.IsDamageTaken = IsDamageTaken;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        LastDirection = "Right";

        PlayerIdleTile = new Texture(Gdx.files.internal("Soldier/Soldier-Idle.png"));
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

        PlayerWalkTile = new Texture(Gdx.files.internal("Soldier/Soldier-Walk.png"));
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

        PlayerAttack01Tile = new Texture(Gdx.files.internal("Soldier/Soldier-Attack01.png"));
        TextureRegion[][] PlayerAttack01TextureRegion = TextureRegion.split(PlayerAttack01Tile, PlayerAttack01Tile.getWidth() / 6, PlayerAttack01Tile.getHeight());
        TextureRegion[] Attack01Frame = new TextureRegion[6];
        int Attack01Index = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 6; j++){
                Attack01Frame[Attack01Index++] = PlayerAttack01TextureRegion[i][j];
            }
        }
        PlayerAttack01Animation = new Animation<>(0.1f, Attack01Frame);

        PlayerDamageTile = new Texture(Gdx.files.internal("Soldier/Soldier-Hurt.png"));
        TextureRegion[][] PlayerDamageTextureRegion = TextureRegion.split(PlayerDamageTile, PlayerDamageTile.getWidth() / 4, PlayerDamageTile.getHeight());
        TextureRegion[] DamageFrame = new TextureRegion[4];
        int DamageIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 4; j++){
                DamageFrame[DamageIndex++] = PlayerDamageTextureRegion[i][j];
            }
        }
        PlayerDamageAnimation = new Animation<>(0.1f, DamageFrame);
        DamageTime = 0f;

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
            LastDirection = "Left";
            Player.setX(Player.getX() - Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            Player.setY(Player.getY() - Player.getSpeed());
            IsMoveKeyPressed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            LastDirection = "Right";
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
                IsAttacking = false;
                IsAttack01OnCooldown = true;
                AttackTime = 0f;

            }
        }

        else if (IsMoveKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(WalkTime, true);
            AttackTime = 0f;
        }

        else if (IsDamageTaken){
            DamageTime += Gdx.graphics.getDeltaTime();
            CurrentFrame = PlayerDamageAnimation.getKeyFrame(DamageTime, false);
            if (PlayerDamageAnimation.isAnimationFinished(DamageTime)){
                IsDamageTaken = false;
                DamageTime = 0f;
            }
        }

        else{
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
            AttackTime = 0f;
        }

        batch.begin();
        if (Objects.equals(LastDirection, "Right")){
            if (IsAttacking && !IsAttack01OnCooldown){
                batch.draw(CurrentFrame, Player.getX() - 25, Player.getY(), (float) (3.5 * 34), (float) (3.5 * 27));
            }
            else{
                batch.draw(CurrentFrame, Player.getX(), Player.getY(), (float) (3.5 * 17), (float) (3.5 * 22));
            }
        }
        if (Objects.equals(LastDirection, "Left")) {
            if (IsAttacking && !IsAttack01OnCooldown){
                batch.draw(CurrentFrame, Player.getX() + 90, Player.getY(), (float) -(3.5 * 34), (float) (3.5 * 27));
            }
            else {
                batch.draw(CurrentFrame, Player.getX() + 67, Player.getY(), (float) -(3.5 * 17), (float) (3.5 * 22));
            }
        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }


}
