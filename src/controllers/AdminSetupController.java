package controllers;

import core.App;
import core.Setup;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import models.Medecin;
import models.Personne;
import utils.Timer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class AdminSetupController extends Controller {

    @FXML
    TextField username_f;

    @FXML
    PasswordField password_f;

    @FXML
    TextField nom_f;

    @FXML
    TextField prenom_f;

    @FXML
    TextField adresse_f;

    @FXML
    TextField telephone_f;

    @FXML
    ChoiceBox genre_f;

    @FXML
    DatePicker birthdate_f;

    @FXML
    Label alert_lab;

    public AdminSetupController() {
        title = "Compte administrateur";
        min_width = width = max_width = 350;
        min_height = height = max_height = 350;
    }

    public void initialize() {
        genre_f.getItems().setAll("M", "F");
        genre_f.setValue("M");
    }

    public void save() {

        String username = username_f.getText();
        String password = password_f.getText();
        String nom = nom_f.getText();
        String prenom = prenom_f.getText();
        String adresse = adresse_f.getText();
        String telephone = telephone_f.getText();
        String genre = (String) genre_f.getValue();
        LocalDate birthdate = birthdate_f.getValue();

        if (username == null || username.equals("")) {
            alert("Le nom d'utlisateur est vide");
            return;
        }

        if (password == null || password.equals("")) {
            alert("Le mot de passe est vide");
            return;
        }

        if (nom == null || nom.equals("")) {
            alert("Le nom est vide");
            return;
        }

        if (prenom == null || nom.equals("")) {
            alert("Le prenom est vide");
            return;
        }

        if (adresse == null || adresse.equals("")) {
            alert("L'adresse est vide");
            return;
        }

        if (telephone == null || telephone.equals("")) {
            alert("Le telephone est vide");
            return;
        }

        if (genre == null) genre = "M";
        if (genre.equals("")) {
            alert("Veuillez selectionner un genre");
            return;
        }

        if (birthdate == null) {
            alert("La date de naissance est vide");
            return;
        }

        getWindow().setTitle("Veuillez patientier...");

        char g = genre.charAt(0);

        Setup.setup_database();

        Personne person = new Personne(0, nom, prenom, adresse, telephone, g, Date.valueOf(birthdate));
        person.save();

        Medecin medecin = new Medecin(0, person.getId(), username, password, true, nom, prenom, adresse, telephone, g, Date.valueOf(birthdate));
        medecin.save();

        App.setView(App.entry);

    }

    private void alert(String msg) {
        alert_lab.setText(msg);
        alert_lab.setVisible(true);

        alert_lab.setTextFill(Color.web("#A42626"));
    }


}
