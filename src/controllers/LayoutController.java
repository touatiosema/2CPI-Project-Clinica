package controllers;

import com.jfoenix.controls.JFXButton;
import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class LayoutController extends Controller {
    @FXML
    JFXButton hamburger_btn;

    @FXML
    VBox sidebar;

    @FXML
    FontAwesomeIconView hamburger_icon;

    @FXML
    VBox content;

    @FXML
    JFXButton patients_btn;

    @FXML
    JFXButton doctors_btn;

    @FXML
    JFXButton settings_btn;

    @FXML
    JFXButton logout_btn;

    @FXML
    JFXButton agenda_btn;

    @FXML
    JFXButton notifications_btn;


    boolean sidebar_open = true;

    public int content_min_width;
    public int content_width;
    public int content_max_width;

    public void initialize() {
        hamburger_btn.setOnAction(e -> update());
        patients_btn.setOnAction(e -> App.setView("Patients"));
        doctors_btn.setOnAction(e -> App.setView("ManageAccounts"));

        if (!Auth.isAdmin()) {
            settings_btn.setOnAction(e -> App.newWindow("EditAccount", new HashMap() {{
                put("id", Auth.getUserID());
                put("modify", true);
            }}));
        }

        else {
            settings_btn.setOnAction(e -> App.newWindow("CabinetSetup", new HashMap() {{
                put("isSetup", false);
            }}));
        }

        notifications_btn.setOnAction(e -> App.setView("NotificationList"));

        logout_btn.setOnAction(e -> {
            App.logout();
        });

        agenda_btn.setOnAction(e -> App.setView("Agenda"));

        if (!Auth.isAdmin()) {
            sidebar.getChildren().remove(doctors_btn);
        }

        update();
    }

    public void setContent(Node p, String name) {
        VBox.setVgrow(p, Priority.ALWAYS);
        content.getChildren().setAll(p);
/*
        patients_btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        doctors_btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        agenda_btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");


        if (name.equals("Patients")) {
            patients_btn.setStyle("-fx-background-color: #297da9; -fx-cursor: hand;");
        }

        else if (name.equals("ManageAccounts")) {
            doctors_btn.setStyle("-fx-background-color: #297da9; -fx-cursor: hand;");

        }

        else if (name.equals("Agenda")) {
            agenda_btn.setStyle("-fx-background-color: #297da9; -fx-cursor: hand;");
        }
*/
    }

    public void setContentWidth(int min_w, int w, int max_w) {
        content_min_width = min_w;
        content_width = w;
        content_max_width = max_w;
    }

    private void update() {
        int padding_right = 20, width = 200;

        if (sidebar_open) {
            padding_right = 0;
            width = 54;
        }

        if (getWindow() != null) update_window_width(width);

        sidebar.setMinWidth(width);
        sidebar.setPrefWidth(width);

        if (sidebar_open) {
            hamburger_btn.setRipplerFill(Color.valueOf("#309bd1"));
            hamburger_icon.setIcon(FontAwesomeIcon.BARS);
        }

        else {
            hamburger_btn.setRipplerFill(Color.valueOf("#FFF"));
            hamburger_icon.setIcon(FontAwesomeIcon.ARROW_LEFT);
        }

        for (Node item : sidebar.getChildren()) {
            JFXButton btn = (JFXButton) item;

            if (sidebar_open) {
                btn.getGraphic().setId("g_" + btn.getText());
                btn.setText(" ");
            }

            else {
                btn.setText(btn.getGraphic().getId().substring(2));
            }


            btn.setPrefWidth(width);
            btn.setPadding(new Insets(15, padding_right, 15, 5));
        }

        sidebar_open = !sidebar_open;
    }

    private void update_window_width(int width) {

        getWindow().setMinWidth(content_min_width + width);
        getWindow().setMaxWidth(content_max_width + width);

    }
}

