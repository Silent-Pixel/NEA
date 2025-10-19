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
    private static Random random = new Random();
    private static Texture[][] BackgroundTexture = new Texture[5][5];
    private static int CurrentTextureX = 2, CurrentTextureY = 2;
    private static boolean HasTransitioned;
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
            TestingFunction();
            //BackgroundTextureSetting();
            //Once the method has finished running, it returns here and then sets the TextureInitialised boolean flag to true which prevents the method from running again
            // and redoing all the images which results in a different world being put in the [2,2] position when going from the GameScreen and MainMenuScreen. This boolean
            // flag prevents the 5x5 grid from resetting
            TexturesInitialised = true;
        }
    }

    private void TestingFunction(){
        tileMap.create();
    }

    private void BackgroundTextureSetting(){
        //The grid in which are the textures are loaded looks like this:
        //[0 1 2 3 4]
        //[0 1 2 3 4]
        //[0 1 2 3 4]
        //[0 1 2 3 4]
        //[0 1 2 3 4]
        //So from i (x coordinates), it will go through every j (y coordinates)
        //E.g. starting at i=0 it will do i=0 then j=0, j=1, etc.
        //At every cord it will choose a random number and then put a world with that number into that slot
        for (int i = 0; i < BackgroundTexture.length; i++){
            for (int j = 0; j < BackgroundTexture[i].length; j++){
                int RandomNum = random.nextInt(9) + 1;
                BackgroundTexture[i][j] = new Texture("worlds/world_" + RandomNum + ".png");
            }
        }
    }

    @Override
    public void render(float delta) {
        //The render loops constantly repeats which gives the effect of the program running at high fps while at it does is repeat code very quickly.
        //BackgroundTextureChanging();
        tileMap.render();
        RenderPlayer();
        RenderEnemy();
        //PlayerMovement();
        //BackgroundTextureChangeDetection();
        GoToMainMenuOnEsc();
    }

    //This method is in charge of drawing the current world map depending on where the player currently is
    public void BackgroundTextureChanging(){
        //Clears the screen, removes what was drawn in the previous frame so that the drawn images don't overlap each other.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Draws the image located in BackgroundTexture 2d array based on the CurrentTextureX/Y which is changed depending on whether the player moves left/right or up/down
        //Draws the image at the origin of the screen (being 0,0 or the bottom left) in the resolution of 1920x100 pixels.
        batch.draw(BackgroundTexture[CurrentTextureX][CurrentTextureY], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
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

    //This method is in charge of detecting when the player has reached the left, right, top or bottom side of the world and then move onto the next one.
    public void BackgroundTextureChangeDetection(){
        //So that it isn't constantly changing HasTransitioned flag is set to false
        HasTransitioned = false;
        //Checks if the player position is greater than a certain value, here its for top transition, doesn't allow for anymore transitions if the player is at the edge of the
        // 2D array
        if (player.getY() > 980 && !HasTransitioned && CurrentTextureY <= 3) {
            //The Y texture is increased by one with the player getting sent back to the centre of the screen, obviously once tiled maps are made it would be stood next to
            // where the player walked in rather than getting teleported
            CurrentTextureY++;
            player.setX(960);
            player.setY(540);
            //HasTransitioned flag then gets set to true so that the map doesn't scroll through multiple times when at the detection area
            HasTransitioned = true;
        }

        //Same is applied to the rest of this method just different values are set for the appropriate X and Y values
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
