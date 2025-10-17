package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class GameScreen implements Screen {

    private Main main;
    private static final Player player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 25, 25, Color.LIME);
    ShapeRenderer sr = new ShapeRenderer();
    private static final Enemy enemy = new Enemy(960, 340, 25, 25, Color.DARK_GRAY);
    private static Random random = new Random();
    SpriteBatch batch = new SpriteBatch();
    Collision collision = new Collision();
    private static Texture[][] BackgroundTexture = new Texture[5][5];
    private static int CurrentTextureX = 2, CurrentTextureY = 2;
    private static boolean HasTransitioned;

    private static boolean TextureInit = false;

    public GameScreen(Main game){
        this.main = game;
    }

    @Override
    public void show() {
        if (!TextureInit){
            BackgroundTextureSetting();
            TextureInit = true;
        }

    }

    private void BackgroundTextureSetting(){
        //for loop going through a 2d 5x5 array/grid and setting a random world to each coordinate
        //this is temporary and will be replaced with an algorithm that makes a proper path with different world segments rather than randomly picking through premade ones.
        for (int i = 0; i < BackgroundTexture.length; i++){
            for (int j = 0; j < BackgroundTexture[i].length; j++){
                int RandomNum = random.nextInt(5) + 1;
                BackgroundTexture[i][j] = new Texture("worlds/world_" + RandomNum + ".png");
            }
        }
    }

    @Override
    public void render(float delta) {
        BackgroundTextureChanging();
        RenderPlayer();
        RenderEnemy();
        PlayerMovement();
        BackgroundTextureChangeDetection();
        GoToMainMenuOnEsc();
    }

    public void BackgroundTextureChanging(){
        //draws the current loaded world in 2,2 onto the players screen at 0,0 on the screen (bottom left) in a resolution of 1920x1080
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(BackgroundTexture[CurrentTextureX][CurrentTextureY], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void RenderPlayer(){
        //renders the player as a cube on the screen
        //temporary and will be replaced with an actual character of some sorts
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(player.getColour());
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
    }

    public void RenderEnemy(){
        //renders the monster as a cube on the screen
        //temporary and will be replaced with an actual character of some sorts
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(enemy.getColour());
        sr.rect(enemy.getX(), enemy.getY(), enemy.getW(), enemy.getH());
        sr.end();
    }

    public void PlayerMovement(){
        //movement section, moving the player, up, down, left, and right on the screen
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            //checks weather the next move is valid to move in a free space and not a blocked area
            //blocked areas per world/section will be listed in the collisions class and player position sent there to check if the move is valid or not
            //passes the current player x and new player y coordinate (or verse versa) into the collisions class
            if (!collision.CheckValidMove(player.getX(), player.getY() + (500 * Gdx.graphics.getDeltaTime()))) {
                //executes the move
                player.setY(player.getY() + (500 * Gdx.graphics.getDeltaTime()));
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            if (!collision.CheckValidMove(player.getX() - (500 * Gdx.graphics.getDeltaTime()), player.getY())) {
                player.setX(player.getX() - (500 * Gdx.graphics.getDeltaTime()));
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            if (!collision.CheckValidMove(player.getX(), player.getY() - (500 * Gdx.graphics.getDeltaTime()))) {
                player.setY(player.getY() - (500 * Gdx.graphics.getDeltaTime()));
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if (!collision.CheckValidMove(player.getX() + (500 * Gdx.graphics.getDeltaTime()), player.getY())) {
                player.setX(player.getX() + (500 * Gdx.graphics.getDeltaTime()));
            }
        }
    }

    public void BackgroundTextureChangeDetection(){
        HasTransitioned = false;
        //checks if the player reaches a certain point on the screen
        //proper boundaries to be set once complete worlds are made
        if (player.getY() > 980 && !HasTransitioned && CurrentTextureY <= 3) {
            //changes the current texture on corresponding axis to where the player moves
            CurrentTextureY++;
            //resets player location to centre of screen
            //once final worlds are made the position will be set to correct sides
            //e.g. enters on left to player will be on right side on next screen
            player.setX(960);
            player.setY(540);
            //allows the screen to change
            HasTransitioned = true;
        }

        else if (player.getY() < 100 && !HasTransitioned && CurrentTextureY >= 1){
            CurrentTextureY--;
            player.setX(960);
            player.setY(540);
            HasTransitioned = true;
        }

        else if (player.getX() > 1820 && !HasTransitioned && CurrentTextureX <= 3){
            CurrentTextureX++;
            player.setX(960);
            player.setY(540);
            HasTransitioned = true;
        }

        else if (player.getX() < 100 && !HasTransitioned && CurrentTextureX >= 1){
            CurrentTextureX--;
            player.setX(960);
            player.setY(540);
            HasTransitioned = true;
        }
    }

    public void GoToMainMenuOnEsc() {
        //program will go to main menu when player presses esc
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            main.setScreen(new MainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
    }
}
