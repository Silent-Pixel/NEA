package io.github.some_example_name;

import java.util.ArrayList;

public class Maps {

    private int map_x, map_y;
    private boolean loaded_area;

    public Maps(int map_x, int map_y, boolean loaded_area) {
        this.map_x = map_x;
        this.map_y = map_y;
        this.loaded_area = loaded_area;
    }

    public int getMap_x() {
        return map_x;
    }

    public void setMap_x(int map_x) {
        this.map_x = map_x;
    }

    public int getMap_y() {
        return map_y;
    }

    public void setMap_y(int map_y) {
        this.map_y = map_y;
    }

    public boolean isLoaded_area() {
        return loaded_area;
    }

    public void setLoaded_area(boolean loaded_area) {
        this.loaded_area = loaded_area;
    }
}
