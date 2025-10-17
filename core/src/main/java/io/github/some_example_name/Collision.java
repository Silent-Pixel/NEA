package io.github.some_example_name;

public class Collision {
    //Receives the X and Y position coordinates of the player from the GameScreen class
    public boolean CheckValidMove(float player_x, float player_y){
        //Checks if the player is about to enter the following zone
        if (player_x > 0 && player_x < 700 && player_y > 0 && player_y < 200){
            //If the player is going to enter that zone then true is returned which declines the player move in GameScreen and then checks the next player
            return true;
        }
        //If the player is not going to enter that zone then false is returned which allows the GameScreen to carry out the rest of the code to allow the player to move
        return false;
    }
}
