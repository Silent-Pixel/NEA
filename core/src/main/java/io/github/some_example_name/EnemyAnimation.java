package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnemyAnimation implements ApplicationListener {

    Animation<TextureRegion> EnemyIdleAnimation;
    Texture EnemyIdleTile;

    Animation<TextureRegion> EnemyWalkAnimation;
    Texture EnemyWalkTile;

    SpriteBatch batch;
    ShapeRenderer sr;
    TextureRegion CurrentFrame;
    Enemy Enemy;
    float IdleTime, WalkTime;

    private final Player Player;
    private final TileMap TileMap;

    public EnemyAnimation(Player Player, TileMap TileMap){
        this.Player = Player;
        this.TileMap = TileMap;
    }

    @Override
    public void create() {
        sr = new ShapeRenderer();
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

        Enemy.UpdatePath(TileMap.getCurrentLevel(), (int)Player.getX(), (int)Player.getY());
        Enemy.FollowPath();

        drawPath();

        batch.begin();
        if (Math.abs(Enemy.getX() - Player.getX()) > 10 || Math.abs(Enemy.getY() - Player.getY()) > 10){
            CurrentFrame = EnemyWalkAnimation.getKeyFrame(WalkTime, true);
        }
        else {
            CurrentFrame = EnemyIdleAnimation.getKeyFrame(IdleTime, true);
        }
        batch.draw(CurrentFrame, Enemy.getX(), Enemy.getY(), 2 * 32, 2 * 32);
        batch.end();
    }

    private void drawPath() {
        if (Enemy.CurrentPath.isEmpty() || !Enemy.HasPath) {
            return;
        }

        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Draw circles at each waypoint
        sr.setColor(Color.YELLOW);
        for (int i = 0; i < Enemy.CurrentPath.size(); i++) {
            int[] tile = Enemy.CurrentPath.get(i);
            float centerX = tile[0] * 64 + 32;
            float centerY = tile[1] * 64 + 32;
            sr.circle(centerX, centerY, 8);
        }

        sr.end();

        // Draw lines connecting waypoints
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        Gdx.gl.glLineWidth(3);

        for (int i = 0; i < Enemy.CurrentPath.size() - 1; i++) {
            int[] currentTile = Enemy.CurrentPath.get(i);
            int[] nextTile = Enemy.CurrentPath.get(i + 1);

            float x1 = currentTile[0] * 64 + 32;
            float y1 = currentTile[1] * 64 + 32;
            float x2 = nextTile[0] * 64 + 32;
            float y2 = nextTile[1] * 64 + 32;

            sr.line(x1, y1, x2, y2);

            if (Enemy.getX() != x2){
                Enemy.setX(Enemy.getX() + (Enemy.getSpeed() * Gdx.graphics.getDeltaTime()));
            }
        }

        Gdx.gl.glLineWidth(1);
        sr.end();
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
