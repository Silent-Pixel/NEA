package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

public class PlayerAnimation extends ApplicationAdapter {

    Animation<TextureRegion> PlayerIdleAnimation;
    Texture PlayerIdleTile;

    Animation<TextureRegion> PlayerWalkAnimation;
    Texture PlayerWalkTile;

    Animation<TextureRegion> PlayerAttack01Animation;
    Texture PlayerAttack01Tile;

    SpriteBatch batch;

    float StateTime, PlayerSpeed;
    boolean IsKeyPressed;
    TextureRegion CurrentFrame;

    Player Player;

    @Override
    public void create() {
        Player = new Player(250, 250, 350, 350);
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
        StateTime = 0f;
    }

    @Override
    public void render() {
        StateTime += Gdx.graphics.getDeltaTime();
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

        if (IsKeyPressed){
            CurrentFrame = PlayerWalkAnimation.getKeyFrame(StateTime, true);
        }
        else {
            CurrentFrame = PlayerIdleAnimation.getKeyFrame(StateTime, true);
        }

        batch.begin();
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            batch.draw(CurrentFrame, Player.getX() + 51, Player.getY(), (float) -(3.5 * 17), (float) (3.5 * 22));
        }
        else {
            batch.draw(CurrentFrame, Player.getX(), Player.getY(), (float) (3.5 * 17), (float) (3.5 * 22));
        }
        batch.end();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.begin();
                batch.draw(PlayerAttack01Animation.getKeyFrame(StateTime, true), Player.getX(), Player.getY(), 350, 350);
                batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        PlayerIdleTile.dispose();
        PlayerWalkTile.dispose();
    }


}
