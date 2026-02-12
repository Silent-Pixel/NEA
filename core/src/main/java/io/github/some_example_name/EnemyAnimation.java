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
    float[] IdleTime, WalkTime, Attack01Time, Attack01CooldownTimer;
    boolean[] IsAttackOnCooldown;

    Enemy[] Enemies;
    private final Player Player;
    private final LevelSystem LevelSystem;
    private final PlayerAnimation PlayerAnimation;

    public EnemyAnimation(Player Player, LevelSystem LevelSystem, PlayerAnimation PlayerAnimation, Enemy[] Enemies){
        this.Player = Player;
        this.LevelSystem = LevelSystem;
        this.PlayerAnimation = PlayerAnimation;
        this.Enemies = Enemies;
    }

    @Override
    public void create() {
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        int[][] CurrentMap = LevelSystem.getCurrentLevel();
        DijkstraPathfinding DijkstraPathfinding = new DijkstraPathfinding(CurrentMap);

        IdleTime = new float[Enemies.length];
        WalkTime = new float[Enemies.length];
        Attack01Time = new float[Enemies.length];
        Attack01CooldownTimer = new float[Enemies.length];
        IsAttackOnCooldown = new boolean[Enemies.length];

        for (Enemy Enemy : Enemies){
            Enemy.DijkstraPathfinding = DijkstraPathfinding;
        }

        EnemyIdleTile = new Texture(Gdx.files.internal("Enemy/Slime_Idle_Angry.png"));
        TextureRegion[][] EnemyIdleTextureRegion = TextureRegion.split(EnemyIdleTile, EnemyIdleTile.getWidth() / 4, EnemyIdleTile.getHeight());
        TextureRegion[] IdleFrame = new TextureRegion[4];
        int IdleIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 4; j++){
                IdleFrame[IdleIndex++] = EnemyIdleTextureRegion[i][j];
            }
        }
        EnemyIdleAnimation = new Animation<>(0.2f, IdleFrame);

        EnemyWalkTile = new Texture(Gdx.files.internal("Enemy/Slime_JumpWalk_Angry.png"));
        TextureRegion[][] EnemyWalkTextureRegion = TextureRegion.split(EnemyWalkTile, EnemyWalkTile.getWidth() / 5, EnemyWalkTile.getHeight());
        TextureRegion[] WalkFrame = new TextureRegion[5];
        int WalkIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                WalkFrame[WalkIndex++] = EnemyWalkTextureRegion[i][j];
            }
        }
        EnemyWalkAnimation = new Animation<>(0.09f, WalkFrame);

        EnemyAttackTile = new Texture(Gdx.files.internal("Enemy/Slime_Attack_Angry.png"));
        TextureRegion[][] EnemyAttackTextureRegion = TextureRegion.split(EnemyAttackTile, EnemyAttackTile.getWidth() / 5, EnemyAttackTile.getHeight());
        TextureRegion[] AttackFrame = new TextureRegion[5];
        int AttackIndex = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                AttackFrame[AttackIndex++] = EnemyAttackTextureRegion[i][j];
            }
        }
        EnemyAttackAnimation = new Animation<>(0.09f, AttackFrame);
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

        drawPathfindingLines();

        Circle PlayerCircle = new Circle(Player.getX() + 33, Player.getY() + 35, 70);

        batch.begin();
        for (int i = 0; i < Enemies.length; i++) {

            IdleTime[i] += Gdx.graphics.getDeltaTime();
            WalkTime[i] += Gdx.graphics.getDeltaTime();

            if (PlayerCircle.contains(Enemies[i].getX() + 28, Enemies[i].getY() + 35) && PlayerAnimation.IsAttacking){
                Enemies[i].setHealth(Enemies[i].getHealth() - 60);
            }

            if (IsAttackOnCooldown[i]) {
                Attack01CooldownTimer[i] += Gdx.graphics.getDeltaTime();
                if (Attack01CooldownTimer[i] > 1f) {
                    IsAttackOnCooldown[i] = false;
                    Attack01CooldownTimer[i] = 0f;
                }
            }

            if (IsAttackOnCooldown[i]) {
                CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime[i], true);
            }

            else if (PlayerCircle.contains(Enemies[i].getX() + 28, Enemies[i].getY() + 35)) {
                Attack01Time[i] += Gdx.graphics.getDeltaTime();
                CurrentFrame = EnemyAttackAnimation.getKeyFrame(Attack01Time[i], false);
                if (EnemyAttackAnimation.isAnimationFinished(Attack01Time[i])) {
                    PlayerAnimation.setIsDamageTaken(true);
                    IsAttackOnCooldown[i] = true;
                    Attack01Time[i] = 0f;
                    Player.setHealth(Player.getHealth() - 20);
                    System.out.println(Player.getHealth());
                }
            }

            else if (!PlayerCircle.contains(Enemies[i].getX() + 28, Enemies[i].getY() + 35)) {
                Enemies[i].UpdatePath(LevelSystem.getCurrentLevel(), (int) Player.getX(), (int) Player.getY());
                Enemies[i].FollowPath();
                CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime[i], true);
                Attack01Time[i] = 0f;
            }

            else {
                CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime[i], true);
                Attack01Time[i] = 0f;
            }

            if (Player.getX() > Enemies[i].getX()){
                batch.draw(CurrentFrame, Enemies[i].getX(), Enemies[i].getY(), 2 * 32, 2 * 32);
            }
            else {
                batch.draw(CurrentFrame, Enemies[i].getX() + 64, Enemies[i].getY(), -2 * 32, 2 * 32);
            }
        }
        batch.end();
    }

    private void drawPathfindingLines() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Line);

        // Different colors for each enemy
        Color[] pathColors = {
            new Color(1, 0, 0, 0.7f),    // Red for enemy 0
            new Color(0, 1, 0, 0.7f),    // Green for enemy 1
            new Color(0, 0, 1, 0.7f),    // Blue for enemy 2
            new Color(1, 1, 0, 0.7f),    // Yellow for enemy 3
            new Color(1, 0, 1, 0.7f),    // Magenta for enemy 4
            new Color(0, 1, 1, 0.7f)     // Cyan for enemy 5
        };

        for (int i = 0; i < Enemies.length; i++) {
            java.util.ArrayList<int[]> path = Enemies[i].getCurrentPath();

            if (path != null && !path.isEmpty()) {
                // Set color for this enemy's path
                sr.setColor(pathColors[i % pathColors.length]);

                // Draw lines between each waypoint
                for (int j = 0; j < path.size() - 1; j++) {
                    int[] current = path.get(j);
                    int[] next = path.get(j + 1);

                    // Convert tile coordinates to pixel coordinates (center of tiles)
                    float x1 = current[0] * 64 + 32;
                    float y1 = current[1] * 64 + 32;
                    float x2 = next[0] * 64 + 32;
                    float y2 = next[1] * 64 + 32;

                    // Draw a thick line (draw multiple times for thickness)
                    sr.line(x1, y1, x2, y2);
                    sr.line(x1 + 1, y1, x2 + 1, y2);
                    sr.line(x1, y1 + 1, x2, y2 + 1);
                    sr.line(x1 + 1, y1 + 1, x2 + 1, y2 + 1);
                }
            }
        }

        sr.end();

        // Draw waypoint circles
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < Enemies.length; i++) {
            java.util.ArrayList<int[]> path = Enemies[i].getCurrentPath();

            if (path != null && !path.isEmpty()) {
                sr.setColor(pathColors[i % pathColors.length]);

                // Draw circles at each waypoint for clarity
                for (int[] waypoint : path) {
                    float x = waypoint[0] * 64 + 32;
                    float y = waypoint[1] * 64 + 32;
                    sr.circle(x, y, 5);
                }
            }
        }
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
    }
}
