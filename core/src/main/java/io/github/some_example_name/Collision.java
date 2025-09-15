package io.github.some_example_name;

public class Collision {
    public boolean CheckValidMove(float player_x, float player_y){
        if (player_x > 0 && player_x < 700 && player_y > 0 && player_y < 200){
            return true;
        }

        return false;
    }
}
