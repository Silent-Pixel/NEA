package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.some_example_name.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        //Stets the title to "NEA" of the current running window/program
        configuration.setTitle("NEA");
        //Gets the refresh rate of the primary monitor and caps the application fps to the displays refresh rate
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        //Disables virtual synchronisation, enabled by default and caps fps but adds input delay
        configuration.useVsync(false);
        //Sets the application to run in windows mode in the configured resolution, that being 1920x180 pixels
        configuration.setWindowedMode(1920, 1080);
        //Sets the icon of the running application which can be seen in the taskbar and top left of the running window
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
