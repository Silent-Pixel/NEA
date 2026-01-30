//Once the program is launched it begins here

package io.github.some_example_name;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }

    public void StartGame(){
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
