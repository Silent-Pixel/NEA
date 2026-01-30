package io.github.some_example_name;

public class GridCell {
    int gridX, gridY, LevelIndex;
    int[][] LevelData;

    public GridCell(int gridX, int gridY, int levelIndex, int[][] level2DArray) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.LevelIndex = levelIndex;
        this.LevelData = level2DArray;
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

    public int[][] getLevelData() {
        return LevelData;
    }

    public void setLevelData(int[][] LevelData) {
        this.LevelData = LevelData;
    }

    public boolean DoCordsMatch(int x, int y){
        if (this.gridX == x && this.gridY == y){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GridCell at (" + gridX + ", " + gridY + ") with level " + LevelIndex;

    }
}
