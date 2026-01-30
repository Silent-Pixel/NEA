package io.github.some_example_name;

import java.util.ArrayList;

public class LevelGrid {
    ArrayList<GridCell> Cells;
    ArrayList<int[][]> AllLevels;
    int NextLevelIndex;

    public LevelGrid(ArrayList<int[][]> allLevels) {
        this.Cells = new ArrayList<>();
        this.AllLevels = allLevels;
        this.NextLevelIndex = 0;

        if (!AllLevels.isEmpty()){
            GridCell StartCell = new GridCell(0, 0, 0, AllLevels.get(0));
            Cells.add(StartCell);
            NextLevelIndex = 1;
        }
    }

    public GridCell FindCell(int gridX, int gridY){
        for (GridCell Cell: Cells){
            if (Cell.DoCordsMatch(gridX, gridY)){
                return Cell;
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
        int LevelIndex = NextLevelIndex % AllLevels.size();
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
        return FindCell(gridX, gridY) != null;
    }
}
