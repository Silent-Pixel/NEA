package io.github.some_example_name.Characters;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.some_example_name.Levels.LevelSystem;
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
    ShapeRenderer sr;
    float IdleTime, WalkTime, AttackTime, Attack01CooldownTimer, DamageTime;
    boolean IsMoveKeyPressed, IsAttacking, IsAttack01OnCooldown, IsDamageTaken;
    TextureRegion CurrentFrame;
    private final Player Player;
    private EnemyAnimation EnemyAnimation;
    String LastDirection;
    Enemy[] Enemies;
    LevelSystem LevelSystem;

    public PlayerAnimation(Player Player, Enemy[] Enemies){
        this.Player = Player;
        this.Enemies = Enemies;
    }

    public void setEnemies(Enemy[] Enemies){
        this.Enemies = Enemies;
    }

    public void setEnemyAnimation(EnemyAnimation EnemyAnimation){
        this.EnemyAnimation = EnemyAnimation;
    }

    public void setLevelSystem(LevelSystem LevelSystem){
        this.LevelSystem = LevelSystem;
    }

    public void setIsDamageTaken(boolean IsDamageTaken) {
        this.IsDamageTaken = IsDamageTaken;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        LastDirection = "Right";

        //Animations made from the assets
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

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1, 1, 1, 0.1f));
        sr.circle(Player.getX() + 33, Player.getY() + 35, 70);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        IdleTime += Gdx.graphics.getDeltaTime();
        WalkTime += Gdx.graphics.getDeltaTime();
        IsMoveKeyPressed = false;

        int PlayerTileX = (int)((Player.getX() + 20) / 64);
        int PlayerTileY = (int)((Player.getY() + 20) / 64);

        PlayerMovement(PlayerTileX, PlayerTileY);
        PlayerAnimationDetermination(PlayerTileX, PlayerTileY);
        PlayerAnimationRendering();
        PlayerHealthBar();
    }

    //Checks for WASD inputs from the user and moves in the respective direction as long as the next tile is a valid tile.
    public void PlayerMovement(int PlayerTileX, int PlayerTileY){

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            if (CheckValidMove(PlayerTileX, PlayerTileY + 1)){
                Player.setY(Player.getY() + Player.getSpeed());
                IsMoveKeyPressed = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            LastDirection = "Left";
            if (CheckValidMove(PlayerTileX - 1, PlayerTileY)){
                Player.setX(Player.getX() - Player.getSpeed());
                IsMoveKeyPressed = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            if (CheckValidMove(PlayerTileX, PlayerTileY - 1)){
                Player.setY(Player.getY() - Player.getSpeed());
                IsMoveKeyPressed = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            LastDirection = "Right";
            if (CheckValidMove(PlayerTileX + 1, PlayerTileY)){
                Player.setX(Player.getX() + Player.getSpeed());
                IsMoveKeyPressed = true;
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            IsAttacking = true;
            AttackTime = 0f;
        }
    }

    //Determines which animation should be played
    //Idle only when there are no inputs being put it
    //Walk only when WASD are being pressed
    //Attack only when the left mouse button is pressed
    //Damaged only when the enemy lands a successful hit assuming they are within range to attack
    public void PlayerAnimationDetermination(int PlayerTileX, int PlayerTileY){

        if (IsDamageTaken){
            DamageTime += Gdx.graphics.getDeltaTime();
            CurrentFrame = PlayerDamageAnimation.getKeyFrame(DamageTime, false);
            if (PlayerDamageAnimation.isAnimationFinished(DamageTime)){
                IsDamageTaken = false;
                DamageTime = 0f;
            }
            return;
        }

        if (PlayerHitSpike(PlayerTileX, PlayerTileY)){
            DamageTime += Gdx.graphics.getDeltaTime();
            CurrentFrame = PlayerDamageAnimation.getKeyFrame(DamageTime, false);
            if (PlayerDamageAnimation.isAnimationFinished(DamageTime)){
                Player.setHealth(Player.getHealth() - 5);
                DamageTime = 0f;
            }
            return;
        }

        if (IsAttacking){
            AttackTime += Gdx.graphics.getDeltaTime();
            boolean hitEnemy = false;
            for (int  i = 0; i < Enemies.length; i++){
                if (Enemies[i].getHealth() <= 0){
                    continue;
                }
                if (

                    Math.sqrt(Math.pow(Enemies[i].getX() - Player.getX(), 2) + Math.pow(Enemies[i].getY() - Player.getY(), 2)) <= 70

                ){
                    CurrentFrame = PlayerAttack01Animation.getKeyFrame(AttackTime, false);
                    if (PlayerAttack01Animation.isAnimationFinished(AttackTime) && !IsAttack01OnCooldown){
                        EnemyAnimation.setIsDamageTaken(i, true);
                        IsAttacking = false;
                        IsAttack01OnCooldown = true;
                        AttackTime = 0f;
                        Enemies[i].setHealth(Enemies[i].getHealth() - 50);
                        if (Player.getHealth() <= 85){
                            Player.setHealth(Player.getHealth() + 15);
                        }
                        hitEnemy = true;
                        break;
                    }
                    hitEnemy = true;
                    break;
                }
            }
            if (hitEnemy){
                CurrentFrame = PlayerAttack01Animation.getKeyFrame(AttackTime, false);
            }
        }

        if (IsAttacking && !IsAttack01OnCooldown){
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
            IsAttack01OnCooldown = false;
        }

        else if (IsAttack01OnCooldown){
            Attack01CooldownTimer += Gdx.graphics.getDeltaTime();
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
            if (Attack01CooldownTimer > 0.5f){
                IsAttack01OnCooldown = false;
                Attack01CooldownTimer = 0f;
            }
        }

        else{
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(IdleTime, true);
            AttackTime = 0f;
        }
    }

    //Renders the correct size for the player
    //Attack and other animations are different sized assets so different draws required for them
    //Draws in  last looked direction by making width negative to look left
    public void PlayerAnimationRendering(){
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

    //Check to see if the next tile is a valid move, output false
    //Else true
    public boolean CheckValidMove(int TileX, int TileY){

        int[][] map = LevelSystem.getCurrentLevel();

        if (map == null){
            return true;
        }

        if (TileY < 0 || TileY >= map.length || TileX < 0 || TileX >= map[0].length){
            return true;
        }

        if( map[TileY][TileX] == 1 ||
            map[TileY][TileX] == 20 ||
            map[TileY][TileX] == 121 ||
            map[TileY][TileX] == 105 ||
            map[TileY][TileX] == 76 ||
            map[TileY][TileX] == 77 ||
            map[TileY][TileX] == 78 ||
            map[TileY][TileX] == 79 ||
            map[TileY][TileX] == 92 ||
            map[TileY][TileX] == 112 ||
            map[TileY][TileX] == 132 ||
            map[TileY][TileX] == 152){
            return false;
        }

        else{
            return true;
        }
    }

    //Check to see if the next tile is a spike, output true
    //Else false
    public boolean PlayerHitSpike(int TileX, int TileY){

        int[][] map = LevelSystem.getCurrentLevel();

        if (map == null){
            return false;
        }

        if (TileY < 0 || TileY >= map.length || TileX < 0 || TileX >= map[0].length){
            return false;
        }

        if (map[TileY][TileX] == 12){
            return true;
        }
        else{
            return false;
        }
    }

    //Renders same type of health bar as the enemies just for player
    public void PlayerHealthBar(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(0.2f, 0f, 0f, 0.8f);
        sr.rect(Player.getX() + 2, Player.getY() + 80, 60f, 5f);

        sr.setColor(1f, 0f, 0f, 0.9f);
        sr.rect(Player.getX() + 2, Player.getY() + 80, 60f * Player.getHealth() / 100, 5f);

        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
        PlayerIdleTile.dispose();
        PlayerWalkTile.dispose();
        PlayerAttack01Tile.dispose();
        PlayerDamageTile.dispose();
    }


}
