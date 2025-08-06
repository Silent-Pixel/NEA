package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    ShapeRenderer sr;
    Player player;
    Random random;
    SpriteBatch batch;
    Texture[][] BackgroundTexture;

    int CurrentTextureX, CurrentTextureY;
    boolean HasTransisioned;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        random = new Random();
        BackgroundTexture = new Texture[5][5];
        player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 25, 25, Color.LIME);

        CurrentTextureX = 2;
        CurrentTextureY = 2;

        BackgroundTextureSetting();
    }

    public void BackgroundTextureSetting(){
        for (int i = 0; i < BackgroundTexture.length; i++){
            for (int j = 0; j < BackgroundTexture[i].length; j++){
                int RandomNum = random.nextInt(5) + 1;
                BackgroundTexture[i][j] = new Texture("worlds/world_" + RandomNum + ".png");
            }
        }
    }

    @Override
    public void render() {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        BackgroundTextureChanging();
        RenderPlayer();
        PlayerMovement();
        BackgroundTextureChangeDetection();
        QuitOnEsc();
    }

    public void BackgroundTextureChanging(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(BackgroundTexture[CurrentTextureX][CurrentTextureY], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void RenderPlayer(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(player.getColour());
        sr.rect(player.getX(), player.getY(), player.getW(), player.getH());
        sr.end();
    }

    public void PlayerMovement(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.setY(player.getY() + (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.setX(player.getX() - (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.setY(player.getY() - (250 * Gdx.graphics.getDeltaTime()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setX(player.getX() + (250 * Gdx.graphics.getDeltaTime()));
        }
    }

    public void BackgroundTextureChangeDetection(){
        HasTransisioned = false;
        if (player.getY() > 980) {
            if (!HasTransisioned){
                CurrentTextureY++;
                player.setX(960);
                player.setY(540);
                HasTransisioned = true;
            }
        }

        else if (player.getY() < 100){
            if (!HasTransisioned){
                CurrentTextureY--;
                player.setX(960);
                player.setY(540);
                HasTransisioned = true;
            }
        }

        else if (player.getX() > 1820){
            if (!HasTransisioned){
                CurrentTextureX++;
                player.setX(960);
                player.setY(540);
                HasTransisioned = true;
            }
        }

        else if (player.getX() < 100){
            if (!HasTransisioned){
                CurrentTextureX--;
                player.setX(960);
                player.setY(540);
                HasTransisioned = true;
            }
        }
    }

    public void QuitOnEsc() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(-1);
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        for (int i = 0; i < BackgroundTexture.length; i++){
            for (int j = 0; j < BackgroundTexture[i].length; j++){
                BackgroundTexture[i][j].dispose();
            }
        }
    }
}
