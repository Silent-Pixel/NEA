package io.github.some_example_name;

import java.util.ArrayList;

public class Levels {

    ArrayList<Integer[][]> level;
    int PositionX, PositionY, NeighbourX, NeighbourY;

    public Levels(ArrayList<Integer[][]> level, int positionX, int positionY, int neighbourX, int neighbourY) {
        this.level = level;
        PositionX = positionX;
        PositionY = positionY;
        NeighbourX = neighbourX;
        NeighbourY = neighbourY;
    }

    public ArrayList<Integer[][]> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<Integer[][]> level) {
        this.level = level;
    }

    public int getPositionX() {
        return PositionX;
    }

    public void setPositionX(int positionX) {
        PositionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int positionY) {
        PositionY = positionY;
    }

    public int getNeighbourX() {
        return NeighbourX;
    }

    public void setNeighbourX(int neighbourX) {
        NeighbourX = neighbourX;
    }

    public int getNeighbourY() {
        return NeighbourY;
    }

    public void setNeighbourY(int neighbourY) {
        NeighbourY = neighbourY;
    }
}
