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

    public GameScreen(Main game){
        this.main = game;
    }

    private Main main;

    //Sets the x, y, w, h, and colour of the player and enemy
    //The static final allows the initial values to be set and not changed/reset everytime when the class gets switched from MainMenuScreen and the GameScreen
    //The values can still be changed as they are set in the Enemy or Player class from which getN.enemy/player can be used to get a value of either or
    // setN.enemy/player to set the new value in the Enemy/Player class
    private static final Player player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 25, 25, Color.LIME);
    private static final Enemy enemy = new Enemy(960, 340, 25, 25, Color.DARK_GRAY);

    //Declares the variables which are used later in the code
    //The declared variables are then also set and put as static to avoid them being reset to the default values when initially made
    //E.g. CurrentTextureX is set at 2 to begin with and then later increased or decreased by one and keeps it as the new updated value rather than getting it back to 2
    // when the program changed from the GameScreen to the MainMenuScreen.
    private static Texture[][] BackgroundTexture = new Texture[5][5];
    private static boolean TexturesInitialised = false;

    //Declared and set variables which allow the enemy, player, and world images to be displayed
    ShapeRenderer sr = new ShapeRenderer();
    SpriteBatch batch = new SpriteBatch();

    //Collision to pass through values into collision class to check if the next player move is valid or not
    Collision collision = new Collision();
    TileMap tileMap = new TileMap();

    @Override
    public void show() {
        //Checks weather the TextureInitialised boolean flag is set to false (technically not true which is what !TexturesInitialised means)
        if (!TexturesInitialised){
            //Jumps to the BackgroundTextureSetting method bellow and sets the 5x5 grid with random world images
            tileMap.create();
            //Once the method has finished running, it returns here and then sets the TextureInitialised boolean flag to true which prevents the method from running again
            // and redoing all the images which results in a different world being put in the [2,2] position when going from the GameScreen and MainMenuScreen. This boolean
            // flag prevents the 5x5 grid from resetting
            TexturesInitialised = true;
        }
    }

    @Override
    public void render(float delta) {
        //The render loops constantly repeats which gives the effect of the program running at high fps while at it does is repeat code very quickly.
        tileMap.render();
        RenderPlayer();
        RenderEnemy();
        PlayerMovement();
        GoToMainMenuOnEsc();
    }

    //This method is in charge of rendering in the player as a cube on the users screen
    public void RenderPlayer(){
        //Calls shape renderer and tells it it's going to be a filled in shape
        sr.begin(ShapeRenderer.ShapeType.Filled);
        //Gets the colour of the player which was set earlier in the class when the player was initialled with the chosen values for x, y, w, h, and colour
        sr.setColor(player.getColour());
        //Tells shape renderer that the filled in shape with N colour will be a rectangle drawn at X and Y in W and H for its size (all in pixels)
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
    }

    //This method is in charge of rendering in the enemy as a cube on the users screen
    //Basically identical procedure is followed for enemy as for the player which was explained earlier in the player method
    public void RenderEnemy(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(enemy.getColour());
        sr.rect(enemy.getX(), enemy.getY(), enemy.getW(), enemy.getH());
        sr.end();
    }

    //This method is in charge of all the player movement and checks whether the next move is valid or not
    public void PlayerMovement(){
        //Checks which key is pressed and executes the correct if statement
        //.isKeyPressed allows the player to move on one press or while holding the key, isKeyJustPressed would only move the player per key input
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            //Passes through next playerX/Y coordinate into the CheckValidMove method in the Collision class
            //If true is returned the move is blocked and the player doesn't move
            //If false is returned then the move is valid and the move is carried and the player is moved to the next space
            //DeltaTime allows for a consistent moving speed no matter what fps the program may be running at as without it at locked 60 it would move at one speed but
            // doubling the fps to 120 the player will end up moving at double the speed which isn't ideal.
            if (!collision.CheckValidMove(player.getX(), player.getY() + (500 * Gdx.graphics.getDeltaTime()))) {
                player.setY(player.getY() + (500 * Gdx.graphics.getDeltaTime()));
            }
        }

        //Same but for different coordinates is applied to all the other key inputs
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

    //This method is in charge of detection the key press of ESC and taking the user back to the MainMenuScreen from where they can either press play to continue or to quit
    //The option button currently does nothing
    public void GoToMainMenuOnEsc() {
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

    //Disposes of unneeded things which would in a way clog us the system and reduce performance as they would be loaded for no reason hence removed
    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        for (Texture[] textures : BackgroundTexture) {
            for (Texture texture : textures) {
                texture.dispose();
            }
        }
        tileMap.dispose();
    }
}
