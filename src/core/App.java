package core;

import controllers.Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Medecin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private static App app;
    private static HashSet<Stage> windows;
    private static Stage window;

    public static final String entry = "Login";
    private static final String app_name = "Doc";
    public static final String app_foldername = ".docapp";

    @Override
    public void start(Stage primaryStage) {
        windows = new HashSet<Stage>();
        app = this;
        window = primaryStage;
        windows.add(window);
        if (Setup.underSetup())
            Setup.setup_admin();
        else
            App.setView(entry);
        App.show();
    }

    @Override
    public void stop() {
        DB.stop();
    }

    private static Controller load(String name) {
        return load(window, name);
    }

    private static Controller load(Stage window, String name) {
        FXMLLoader loader = new FXMLLoader(app.getClass().getResource("../views/" + name + ".fxml"));
        Controller controller = null;

        try {
            Parent root = loader.load();
            controller = loader.getController();

            if (controller == null) {
                System.out.println("[ERROR] View file " + name + " does not have a Controller");
            }

            window.setMinHeight(controller.getMin_height()  + 40);
            window.setMaxHeight(controller.getMax_height());
            window.setMinWidth(controller.getMin_width() + 10);
            window.setMaxWidth(controller.getMax_width());
            controller.setWindow(window);
            window.setScene(new Scene(root, controller.getWidth(), controller.getHeight()));
            window.setTitle(app_name + " - " + controller.getTitle());
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
        setView(window, name);
    }

    public static void setView(String name, HashMap args) {
        setView(window, name, args);
    }

    public static void setView(Stage window, String name) {
        Controller controller = load(window, name);
        controller.init();
    }

    public static void setView(Stage window, String name, HashMap args) {
        Controller controller = load(window, name);
        controller.init(args);
    }

    public static Stage newWindow(String name) {
        Stage window = new Stage();
        setView(window, name);
        window.show();
        windows.add(window);
        return window;
    }

    public static Stage newWindow(String name, HashMap args) {
        Stage window = new Stage();
        setView(window, name, args);
        window.show();
        windows.add(window);
        return window;
    }

    public static void clearWindows() {
        for (Stage w : windows) {
            if (w != window) {
                w.close();
            }
        }

        windows.clear();
        windows.add(window);
    }

    public static void logout() {
        Auth.logout();
        App.clearWindows();
        App.setView("Login");
    }

    public static void main(String[] args) {
        if (!Setup.check()) {
            Setup.start();
            launch(args);
        }

        else {
            DB.start();
            launch(args);
        }
    }
}