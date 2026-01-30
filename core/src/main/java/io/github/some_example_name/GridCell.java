package io.github.some_example_name;

public class GridCell {
    int gridX, gridY, LevelIndex;
    int[][] Level2DArray;

    public GridCell(int gridX, int gridY, int levelIndex, int[][] level2DArray) {
        this.gridX = gridX;
        this.gridY = gridY;
        LevelIndex = levelIndex;
        Level2DArray = level2DArray;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public int getLevelIndex() {
        return LevelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        LevelIndex = levelIndex;
    }

    public int[][] getLevel2DArray() {
        return Level2DArray;
    }

    public void setLevel2DArray(int[][] level2DArray) {
        Level2DArray = level2DArray;
    }

    public boolean DoCordsMatch(int x, int y){
        return this.gridX == x && this.gridY == y;
    }

    @Override
    public String toString() {
        return "GridCell at (" + gridX + ", " + gridY + ") with level " + LevelIndex;

    }
}
