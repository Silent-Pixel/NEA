package io.github.some_example_name.Levels;

import java.util.ArrayList;
import java.util.Random;

public class LevelGrid {
    ArrayList<GridCell> Cells;
    ArrayList<int[][]> AllLevels;
    int NextLevelIndex;

    public LevelGrid(ArrayList<int[][]> AllLevels) {
        this.Cells = new ArrayList<>();
        this.AllLevels = AllLevels;
        this.NextLevelIndex = 0;

        if (!AllLevels.isEmpty()){
            GridCell StartCell = new GridCell(0, 0, 0, AllLevels.get(0));
            Cells.add(StartCell);
            NextLevelIndex = 1;
        }
    }

    public GridCell FindCell(int gridX, int gridY){
        for (GridCell cell : Cells) {
            if (cell.DoCordsMatch(gridX, gridY)) {
                return cell;
            }
        }
        return null;
    }

    public GridCell GetOrMakeCell(int gridX, int gridY){
        GridCell ExistingCells = FindCell(gridX, gridY);
        if (ExistingCells != null){
            System.out.println("Loaded existing" + ExistingCells);
            return ExistingCells;
        }
        int LevelIndex = new Random().nextInt(AllLevels.size());
        int[][] LevelData = AllLevels.get(LevelIndex);
        GridCell NewCell = new GridCell(gridX, gridY, LevelIndex, LevelData);
        Cells.add(NewCell);
        NextLevelIndex++;
        System.out.println("Created new " + NewCell);
        return NewCell;
    }

    public int getCellCount(){
        return Cells.size();
    }

    public ArrayList<GridCell> getAllCells(){
        return Cells;
    }

    public boolean HasCells(int gridX, int gridY){
        GridCell cell = FindCell(gridX, gridY);
        if (cell != null){
            return true;
        }
        return false;
    }
}
