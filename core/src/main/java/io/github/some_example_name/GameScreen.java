package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {

    private Main game;

    public GameScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {

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
        SpriteBatch batch = game.getBatch();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(game.getBackgroundTexture()[game.getCurrentTextureX()][game.getCurrentTextureY()], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void RenderPlayer(){
        //renders the player as a cube on the screen
        //temporary and will be replaced with an actual character of some sorts
        ShapeRenderer sr = game.getSr();
        Player player = game.getPlayer();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(player.getColour());
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
    }

    public void RenderEnemy(){
        //renders the monster as a cube on the screen
        //temporary and will be replaced with an actual character of some sorts
        ShapeRenderer sr = game.getSr();
        Enemy enemy = game.getEnemy();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(enemy.getColour());
        sr.rect(enemy.getX(), enemy.getY(), enemy.getW(), enemy.getH());
        sr.end();
    }

    public void PlayerMovement(){
        //movement section, moving the player, up, down, left, and right on the screen
        Player player = game.getPlayer();
        Collision collision = game.getCollision();
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
        Player player = game.getPlayer();
        game.setHasTransitioned(false);
        //checks if the player reaches a certain point on the screen
        //proper boundaries to be set once complete worlds are made
        if (player.getY() > 980 && !game.HasTransitioned && game.getCurrentTextureY() <= 3) {
            //changes the current texture on corresponding axis to where the player moves
            game.setCurrentTextureY(game.getCurrentTextureX() + 1);
            //resets player location to centre of screen
            //once final worlds are made the position will be set to correct sides
            //e.g. enters on left to player will be on right side on next screen
            player.setX(960);
            player.setY(540);
            //allows the screen to change
            game.setHasTransitioned(true);
        }

        else if (player.getY() < 100 && !game.getHasTransitioned() && game.getCurrentTextureY() >= 1){
            game.setCurrentTextureY(game.getCurrentTextureY() - 1);
            player.setX(960);
            player.setY(540);
            game.setHasTransitioned(true);
        }

        else if (player.getX() > 1820 && !game.getHasTransitioned() && game.getCurrentTextureX() <= 3){
            game.setCurrentTextureX(game.getCurrentTextureX() + 1);
            player.setX(960);
            player.setY(540);
            game.setHasTransitioned(true);
        }

        else if (player.getX() < 100 && !game.getHasTransitioned() && game.getCurrentTextureX() >= 1){
            game.setCurrentTextureX(game.getCurrentTextureX() - 1);
            player.setX(960);
            player.setY(540);
            game.setHasTransitioned(true);
        }
    }

    public void GoToMainMenuOnEsc() {
        //program quits once esc is pressed on the keyboard by the user
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen());
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

    }
}
