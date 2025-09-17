package io.github.some_example_name;

public class Collision {
    //receives player x and y from the main class
    public boolean CheckValidMove(float player_x, float player_y){
        //checks if the player will be in this zone
        if (player_x > 0 && player_x < 700 && player_y > 0 && player_y < 200){
            //returns true which means no move is allowed
            return true;
        }
        //if false returned then move is allowed
        return false;
    }
}
