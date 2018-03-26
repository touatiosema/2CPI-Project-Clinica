package controllers;

import core.App;
import core.Auth;
import core.exceptions.UserDeactivatedException;
import core.exceptions.UserNotFoundException;
import core.exceptions.WrongPasswordException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class LoginController extends Controller {

    @FXML
    TextField username_field;

    @FXML
    PasswordField password_field;

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
        min_height = 400;
        max_height = 400;
        title = "Authentification";
    }

    public void init() {
        username_field.setOnAction(e -> login());
        password_field.setOnAction(e -> login());
        login_button.setOnAction(e -> login());
    }

    private void login() {
        clearStatus();

        String username = username_field.getText();
        String password = password_field.getText();

        if (username == null || username.length() == 0) {
            alert("Le nom d'utilisateur est vide", username_field);
            return;
        }

        if (password == null || password.length() == 0) {
            alert("Le mot de passe est vide", password_field);
            return;
        }

        try {
            Auth.login(username, password);
        }

        catch (UserNotFoundException e) {
            alert("Nom d'utilisateur invalid", username_field);
        }

        catch (WrongPasswordException e) {
            alert("Mot de passe erroné", password_field);
        }

        catch (UserDeactivatedException e) {
            alert("Compte désactivé", username_field);
        }

        if (Auth.isLoggedIn()) {
            App.setView("Acceuil");
        }

    }

    private void alert(String msg, Node element) {
        alert_icon.setVisible(true);
        status_label.setText(msg);
        element.setStyle("-fx-background-color: #9a100c; -fx-text-fill: white");
    }

    private void clearStatus() {
        status_label.setText("");
        alert_icon.setVisible(false);
        username_field.setStyle("-fx-border-color: #b4b4b4; -fx-border-radius: 2px; -fx-background-color: white; -fx-text-fill: black");
        password_field.setStyle("-fx-border-color: #b4b4b4; -fx-border-radius: 2px; -fx-background-color: white; -fx-text-fill: black");
    }
}
