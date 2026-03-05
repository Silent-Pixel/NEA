package io.github.some_example_name.Pathfinding;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstraPathfinding {

    public int[][] map;
    int w, h;

    public DijkstraPathfinding(int[][] map){
        this.map = map;
        this.h = map.length;
        this.w = map[0].length;
    }

    public ArrayList<int[]> FindPath(int startX, int startY, int endX, int endY){
        int[][] distance = new int[h][w];
        int[][] previousX = new int[h][w];
        int[][] previousY = new int[h][w];

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                distance[i][j] = Integer.MAX_VALUE;
                previousX[i][j] = -1;
                previousY[i][j] = -1;
            }
        }

        PriorityQueue PriorityQueue = new PriorityQueue();
        PriorityQueue.add(startX, startY, 0);
        distance[startY][startX] = 0;

        int[] ChangeInX = {1, -1, 0, 0};
        int[] ChangeInY = {0, 0, 1, -1};

        while (!PriorityQueue.isEmpty()){
            int[] Current = PriorityQueue.poll();
            int x = Current[0];
            int y = Current[1];

            if (x == endX && y == endY){
                break;
            }

            for (int i = 0; i < 4; i++){
                int NextX = x + ChangeInX[i];
                int NextY = y + ChangeInY[i];

                if (NextX < 0 || NextY < 0 || NextX >= w || NextY >= h){
                    continue;
                }
                if (map[NextY][NextX] != 47){
                    continue;
                }
                int NewDistance = distance[y][x] + 1;

                if (NewDistance < distance[NextY][NextX]){
                    distance[NextY][NextX] = NewDistance;
                    previousX[NextY][NextX] = x;
                    previousY[NextY][NextX] = y;
                    PriorityQueue.add(NextX, NextY, NewDistance);
                }
            }
        }

        if (previousX[endY][endX] == -1){
            return new ArrayList<>();
        }

        ArrayList<int[]> ShortestPath = new ArrayList<>();
        int CurrentX = endX;
        int CurrentY = endY;

        while (!(CurrentX == startX && CurrentY == startY)){
            ShortestPath.add(new int[]{CurrentX, CurrentY});
            int PreviousX = previousX[CurrentY][CurrentX];
            int PreviousY = previousY[CurrentY][CurrentX];
            CurrentX = PreviousX;
            CurrentY = PreviousY;
        }

        ShortestPath.add(new int[]{startX, startY});
        Collections.reverse(ShortestPath);

        return ShortestPath;
    }
}
