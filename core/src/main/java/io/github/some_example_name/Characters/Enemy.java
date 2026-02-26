package io.github.some_example_name.Characters;

import com.badlogic.gdx.Gdx;
import io.github.some_example_name.Pathfinding.DijkstraPathfinding;

import java.util.ArrayList;
import java.util.Map;

public class Enemy {

    float x, y, speed = 150, health = 100, CurrentX, CurrentY, NextX, NextY;
    ArrayList<int[]> CurrentPath = new ArrayList<>();
    boolean HasPath = false;
    public DijkstraPathfinding DijkstraPathfinding;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Enemy(DijkstraPathfinding DijkstraPathfinding){
        this.DijkstraPathfinding = DijkstraPathfinding;
    }

    public void UpdatePath(int[][] map, int PlayerX, int PlayerY){
        DijkstraPathfinding.map = map;

        int EnemyTileX = (int)((x + 32) / 64);
        int EnemyTileY = (int)((y + 32) / 64);

        int PlayerTileX = (PlayerX / 64);
        int PlayerTileY = (PlayerY / 64);

        if (EnemyTileX != PlayerTileX || EnemyTileY != PlayerTileY){
            CurrentPath = DijkstraPathfinding.FindPath(EnemyTileX, EnemyTileY, PlayerTileX, PlayerTileY);
            if (!CurrentPath.isEmpty()){
                HasPath = true;
            }
            else {
                HasPath = false;
            }
        }
    }

    public void FollowPath(){
        if (!HasPath || CurrentPath.isEmpty()){
            return;
        }

        int[] NextTile = CurrentPath.get(1);
        NextX = NextTile[0] * 64;
        NextY = NextTile[1] * 64;

        if (Math.abs(NextX - getX()) < getSpeed() + 1 && Math.abs(NextY - getY()) < getSpeed() + 1){
            setX(NextX);
            setY(NextY);
            CurrentPath.remove(0);
            if (CurrentPath.size() < 2){
                HasPath = false;
            }
            return;
        }

        if (Math.abs(NextX - getX()) > Math.abs(NextY - getY())){
            if (NextX - getX() > 0){
                setX(getX() + getSpeed());
            }
            else{
                setX(getX() - getSpeed());
            }
        }
        else{
            if(NextY - getY() > 0){
                setY(getY() + getSpeed());
            }
            else{
                setY(getY() - getSpeed());
            }
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
        this.health = health;
    }

    public ArrayList<int[]> getCurrentPath() {
        return CurrentPath;
    }
}
