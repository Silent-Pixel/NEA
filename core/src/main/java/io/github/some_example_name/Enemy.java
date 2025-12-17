package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;

public class Enemy {

    //Creates the variables for the enemy
    float x, y;
    int w, h;
    Color Colour;

    //Receives the enemy x and y position, the width and height of the monster shape, and the colour from GameScreen class
    public Enemy(float x, float y, int w, int h, Color colour) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        Colour = colour;
    }

    //The getters and setters allows the enemy x, y, w, h, and colour elements to get read and used for what may need be
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Color getColour() {
        return Colour;
    }

    public void setColour(Color colour) {
        Colour = colour;
    }
}
