package controllers;

public class Controller {
    private final String title = "Application";
    private final int width = 600;
    private final int height = 450;

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
    public void init(Object ...args){}
}
