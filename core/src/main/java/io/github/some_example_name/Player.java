package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;

public class Player {

    private int x, y, w, h;
    private Color Colour;

    public Player(int x, int y, int w, int h, Color colour) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        Colour = colour;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
