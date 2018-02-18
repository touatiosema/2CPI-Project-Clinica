package controllers;

import java.util.HashMap;

public abstract class Controller {
    protected String title = "Application";
    protected int width = 600;
    protected int height = 450;

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
}