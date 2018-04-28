package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.utils.JFXNodeUtils;
import core.App;
import core.Auth;
import core.exceptions.UserDeactivatedException;
import core.exceptions.UserNotFoundException;
import core.exceptions.WrongPasswordException;
import core.notification.Notification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoginController extends Controller {

    @FXML
    JFXTextField username_field;

    @FXML
    JFXPasswordField password_field;

    @FXML
    Button login_button;

    @FXML
    Label status_label;

    @FXML
    FontAwesomeIconView alert_icon;

    @FXML
    AnchorPane body;

    public LoginController() {
        min_width = 600;
        max_width = 600;
        min_height = 450;
        max_height = 450;
        title = "Authentification";
    }

    public void init() {
        username_field.setOnAction(e -> login());
        password_field.setOnAction(e -> login());
        login_button.setOnAction(e -> login());
    }

    private void login() {
        String username = username_field.getText();
        String password = password_field.getText();

        if (username == null || username.length() == 0) {
            alert("Le nom d'utilisateur est vide");
            return;
        }

        if (password == null || password.length() == 0) {
            alert("Le mot de passe est vide");
            return;
        }

        try {
            Auth.login(username, password);
        }

        catch (UserNotFoundException e) {
            alert("Nom d'utilisateur invalid");
        }

        catch (WrongPasswordException e) {
            alert("Mot de passe erroné");
        }

        catch (UserDeactivatedException e) {
            alert("Compte désactivé");
        }

        if (Auth.isLoggedIn()) {
            if (!App.isNotifying()) {
                Notification notification =  new Notification();
                notification.setDelay(Duration.seconds(5));
                notification.setPeriod(Duration.seconds(20));
                notification.start();

                App.setNotifier(notification);
            }

            if (Auth.isAdmin()) App.setView("ManageAccounts");
            else App.setView("NotificationList");
        }

    }

    private void alert(String msg) {
        alert_icon.setVisible(true);
        status_label.setText(msg);
    }
}
