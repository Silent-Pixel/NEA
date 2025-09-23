package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    //creating variables
    ShapeRenderer sr;
    Player player;
    Enemy enemy;
    Random random;
    SpriteBatch batch;
    Collision collision;
    Texture[][] BackgroundTexture;
    int CurrentTextureX, CurrentTextureY;
    boolean HasTransitioned;

    @Override
    public void create() {
        //setting variables
        sr = new ShapeRenderer();
        player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 25, 25, Color.LIME);
        enemy = new Enemy(960, 340, 25, 25, Color.DARK_GRAY);
        random = new Random();
        batch = new SpriteBatch();
        collision = new Collision();
        BackgroundTexture = new Texture[5][5];
        CurrentTextureX = 2;
        CurrentTextureY = 2;

        BackgroundTextureSetting();

        setScreen(new MainMenuScreen());
    }

    public void BackgroundTextureSetting(){
        //for loop going through a 2d 5x5 array/grid and setting a random world to each coordinate
        //this is temporary and will be replaced with an algorithm that makes a proper path with different world segments rather than randomly picking through premade ones.
        for (int i = 0; i < BackgroundTexture.length; i++){
            for (int j = 0; j < BackgroundTexture[i].length; j++){
                int RandomNum = random.nextInt(5) + 1;
                BackgroundTexture[i][j] = new Texture("worlds/world_" + RandomNum + ".png");
            }
        }
    }

    public void StartGame(){
        setScreen(new GameScreen(this));
    }

    public ShapeRenderer getSr(){
        return sr;
    }

    public Player getPlayer(){
        return player;
    }

    public Enemy getEnemy(){
        return enemy;
    }

    public SpriteBatch getBatch(){
        return batch;
    }

    public Collision getCollision(){
        return collision;
    }

    public Texture[][] getBackgroundTexture(){
        return BackgroundTexture;
    }

    public int getCurrentTextureX(){
        return CurrentTextureX;
    }

    public int getCurrentTextureY(){
        return CurrentTextureY;
    }

    public void setCurrentTextureX(int x){
        CurrentTextureX = x;
    }

    public void setCurrentTextureY(int y){
        CurrentTextureY = y;
    }

    public boolean getHasTransitioned(){
        return HasTransitioned;
    }

    public void setHasTransitioned(boolean hasTransitioned){
        HasTransitioned = hasTransitioned;
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
        batch.dispose();
        for (Texture[] textures : BackgroundTexture) {
            for (Texture texture : textures) {
                texture.dispose();
            }
        }
    }
}
