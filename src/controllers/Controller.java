package controllers;

import javafx.stage.Stage;

import java.util.HashMap;

public abstract class Controller {
    protected String title = "Application";
    protected int width = 600;
    protected int height = 450;
    protected int min_width = 0;
    protected int min_height = 0;
    protected int max_width = Integer.MAX_VALUE - 200;
    protected int max_height = Integer.MAX_VALUE - 200;
    protected Stage window;

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void init() {}

    public void init(HashMap data) {}

    public int getMin_width() {
        return min_width;
    }

    public int getMax_width() {
        return max_width;
    }

    public int getMin_height() {
        return min_height;
    }

    public int getMax_height() {
        return max_height;
    }
}