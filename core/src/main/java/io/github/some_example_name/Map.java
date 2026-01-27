package io.github.some_example_name;

import com.badlogic.gdx.math.GridPoint2;

public class Map {

    int[][] LevelArray;
    GridPoint2 LevelArrayPosition;

    public Map(int[][] levelArray, GridPoint2 levelArrayPosition) {
        LevelArray = levelArray;
        LevelArrayPosition = levelArrayPosition;
    }

    public int[][] getLevelArray() {
        return LevelArray;
    }

    public void setLevelArray(int[][] levelArray) {
        LevelArray = levelArray;
    }

    public GridPoint2 getLevelArrayPosition() {
        return LevelArrayPosition;
    }

    public void setLevelArrayPosition(GridPoint2 levelArrayPosition) {
        LevelArrayPosition = levelArrayPosition;
    }
}
