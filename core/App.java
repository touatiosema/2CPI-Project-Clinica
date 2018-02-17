package core;

import controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class App extends Application {
    private static App app;
    private static HashMap scenes;
    private static Stage window;

    private static final String entry = "Doctors";

    @Override
    public void start(Stage primaryStage) throws Exception {
        app = this;
        window = primaryStage;
        App.setView(entry);
        App.show();
    }

    @Override
    public void stop() {
        DB.stop();
    }

    private static Controller load(String name) {
        FXMLLoader loader = new FXMLLoader(app.getClass().getResource("../views/" + name + ".fxml"));
        Controller controller = null;

        try {
            Parent root = loader.load();
            controller = loader.getController();

            if (controller == null) {
                System.out.println("[ERROR] View file " + name + " does not have a Controller");
            }

            window.setScene(new Scene(root, controller.getWidth(), controller.getHeight()));
            window.setTitle(controller.getTitle());
        }

        catch(IOException e) {
            System.out.println("[ERROR] Could not load fxml file : " + name);
        }

        return controller;
    }

    public static void show() {
        window.show();
    }

    public static void hide() {
        window.hide();
    }

    public static void setView(String name) {
        Controller controller = load(name);
        controller.init();
    }

    public static void setView(String name, Object ...args) {
        Controller controller = load(name);
        controller.init(args);
    }

    public static void main(String[] args) {
        DB.start();
        launch(args);
    }
}