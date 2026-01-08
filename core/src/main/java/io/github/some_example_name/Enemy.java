package io.github.some_example_name;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Enemy {

    float x = 500, y = 500, w, h, speed = 150, health;
    ArrayList<int[]> CurrentPath = new ArrayList<>();
    int CurrentPathIndex = 0;
    boolean HasPath = false;
    DijkstrasPathfinding DijkstrasPathfinding;
    float CurrentX, CurrentY, NextX, NextY;

    public Enemy(DijkstrasPathfinding DijkstrasPathfinding){
        this.DijkstrasPathfinding = DijkstrasPathfinding;
    }

    public void UpdatePath(int[][] map, int PlayerX, int PlayerY){
        DijkstrasPathfinding.map = map;

        int EnemyTileX = (int)(x / 64);
        int EnemyTileY = (int)(y / 64);

        int PlayerTileX = (PlayerX / 64);
        int PlayerTileY = (PlayerY / 64);

        if (EnemyTileX != PlayerTileX && EnemyTileY != PlayerTileY){
            CurrentPath = DijkstrasPathfinding.FindPath(EnemyTileX, EnemyTileY, PlayerTileX, PlayerTileY);
            if (!CurrentPath.isEmpty()){
                HasPath = true;
                CurrentPathIndex = 0;
            }
            else {
                HasPath = false;
            }
        }
    }

    public void FollowPath(){
        if (!HasPath || CurrentPath.isEmpty() || CurrentPathIndex >= CurrentPath.size()){
            return;
        }

        for (int i = 0; i < CurrentPath.size() - 1; i++){
            int[] CurrentTile = CurrentPath.get(i);
            int[] NextTile = CurrentPath.get(i + 1);

            CurrentX = CurrentTile[0] * 64 + 32;
            CurrentY = CurrentTile[1] * 64 + 32;
            NextX = NextTile[0] * 64 + 32;
            NextY = NextTile[1] * 64 + 32;
        }

        if (CurrentX < NextX){
            setX(getX() + getSpeed());
        }
        if (CurrentX > NextX){
            setX(getX() - getSpeed());
        }
        if (CurrentY < NextY){
            setY(getY() + getSpeed());
        }
        if (CurrentY > NextY) {
            setY(getY() - getSpeed());
        }
    }

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

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getSpeed() {
        return speed * Gdx.graphics.getDeltaTime();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = 100;
    }
}
