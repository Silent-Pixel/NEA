package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class EnemyAnimation implements ApplicationListener {

    Animation<TextureRegion> EnemyIdleAnimation;
    Texture EnemyIdleTile;

    Animation<TextureRegion> EnemyWalkAnimation;
    Texture EnemyWalkTile;

    Animation<TextureRegion> EnemyAttackAnimation;
    Texture EnemyAttackTile;

    SpriteBatch batch;
    ShapeRenderer sr;
    TextureRegion CurrentFrame;
    Enemy Enemy;
    float IdleTime, WalkTime, Attack01Time, Attack01CooldownTimer;
    boolean IsAttackOnCooldown;

    private final Player Player;
    private final TileMap TileMap;

    public EnemyAnimation(Player Player, TileMap TileMap){
        this.Player = Player;
        this.TileMap = TileMap;
    }

    @Override
    public void create() {
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        int[][] CurrentMap = TileMap.getCurrentLevel();
        DijkstrasPathfinding dijkstrasPathfinding = new DijkstrasPathfinding(CurrentMap);
        Enemy = new Enemy(dijkstrasPathfinding);

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
        IdleTime = 0f;

        EnemyWalkTile = new Texture(Gdx.files.internal("assets/Enemy/Slime_JumpWalk_Angry.png"));
        TextureRegion[][] EnemyWalkTextureRegion = TextureRegion.split(EnemyWalkTile, EnemyWalkTile.getWidth() / 5, EnemyWalkTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[5];
        int WalkIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                WalkFrame[WalkIndex++] = EnemyWalkTextureRegion[i][j];
            }
        }
        EnemyWalkAnimation = new Animation<>(0.09f, WalkFrame);
        WalkTime = 0f;

        EnemyAttackTile = new Texture(Gdx.files.internal("assets/Enemy/Slime_Attack_Angry.png"));
        TextureRegion[][] EnemyAttackTextureRegion = TextureRegion.split(EnemyAttackTile, EnemyAttackTile.getWidth() / 5, EnemyAttackTile.getHeight());
        TextureRegion[] AttackFrame = new TextureRegion[5];
        int AttackIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                AttackFrame[AttackIndex++] = EnemyAttackTextureRegion[i][j];
            }
        }
        EnemyAttackAnimation = new Animation<>(0.09f, AttackFrame);
        Attack01Time = 0f;
        Attack01CooldownTimer = 0f;
        IsAttackOnCooldown = false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        IdleTime += Gdx.graphics.getDeltaTime();
        WalkTime += Gdx.graphics.getDeltaTime();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1, 1, 1, 0.1f));
        sr.circle(Player.getX() + 33, Player.getY() + 35, 70);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        Circle PlayerCircle = new Circle(Player.getX() + 33, Player.getY() + 35, 70);

        if (IsAttackOnCooldown){
            Attack01CooldownTimer += Gdx.graphics.getDeltaTime();
            if (Attack01CooldownTimer > 1f){
                IsAttackOnCooldown = false;
                Attack01CooldownTimer = 0f;
            }
        }

        if (IsAttackOnCooldown){
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
        }

        else if (PlayerCircle.contains(Enemy.getX() + 28, Enemy.getY() + 35)){
            Attack01Time += Gdx.graphics.getDeltaTime();
            CurrentFrame = EnemyAttackAnimation.getKeyFrame(Attack01Time, false);
            if (EnemyAttackAnimation.isAnimationFinished(Attack01Time)){
                IsAttackOnCooldown = true;
                Attack01Time = 0f;
            }
        }

        else if (!PlayerCircle.contains(Enemy.getX() + 28, Enemy.getY() + 35)){
            Enemy.UpdatePath(TileMap.getCurrentLevel(), (int)Player.getX(), (int)Player.getY());
            Enemy.FollowPath();
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Attack01Time = 0f;
        }



        /*else if (Enemy.getX() > Player.getX() - 50 && Enemy.getX() < Player.getX() + 50 && Enemy.getY() > Player.getY() - 50 && Enemy.getY() < Player.getY() + 50){
            Attack01Time += Gdx.graphics.getDeltaTime();
            CurrentFrame = EnemyAttackAnimation.getKeyFrame(Attack01Time, false);
            if (EnemyAttackAnimation.isAnimationFinished(Attack01Time)){
                IsAttackOnCooldown = true;
                Attack01Time = 0f;
            }
        }

        else if (Player.getX() != Enemy.getX() && Player.getY() != Enemy.getY()){
            Enemy.UpdatePath(TileMap.getCurrentLevel(), (int)Player.getX(), (int)Player.getY());
            Enemy.FollowPath();
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
            Attack01Time = 0f;
        }*/

        else{
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
            Attack01Time = 0f;
        }

        batch.begin();
        batch.draw(CurrentFrame, Enemy.getX(), Enemy.getY(), 2 * 32, 2 * 32);
        batch.end();




        /*IdleTime += Gdx.graphics.getDeltaTime();
        WalkTime += Gdx.graphics.getDeltaTime();
        Attack01Time += Gdx.graphics.getDeltaTime();
        InAttackRange = false;



        if (Enemy.getX() > Player.getX() - 50 && Enemy.getX() < Player.getX() + 50 && Enemy.getY() > Player.getY() - 50 && Enemy.getY() < Player.getY() + 50){
            InAttackRange = true;
        }



        if (InAttackRange){
            CurrentFrame = EnemyAttackAnimation.getKeyFrame(Attack01Time, true);

            if (EnemyAttackAnimation.isAnimationFinished(Attack01Time)){
                InAttackRange = false;
            }
        }
        else if (Player.getX() != Enemy.getX() && Player.getY() != Enemy.getY()) {
            Enemy.UpdatePath(TileMap.getCurrentLevel(), (int)Player.getX(), (int)Player.getY());
            Enemy.FollowPath();
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
        }

        else {
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
        }

        batch.begin();
        batch.draw(CurrentFrame, Enemy.getX(), Enemy.getY(), 2 * 32, 2 * 32);
        batch.end(); */
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
        EnemyIdleTile.dispose();
        EnemyWalkTile.dispose();
    }
}
