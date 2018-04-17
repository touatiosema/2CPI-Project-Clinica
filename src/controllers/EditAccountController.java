package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Medecin;
import models.Personne;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

public class EditAccountController extends Controller {
    private int medecin_id;
    private ManageAccountsController main_window;

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

    public EditAccountController() {
        title = "Modifier Compte";
        min_width = width = max_width = 350;
        min_height = height = max_height = 350;
    }

    public void init(HashMap args) {
        main_window = (ManageAccountsController) args.get("main_window");

        genre_f.getItems().setAll("M", "F");
        genre_f.setValue("M");

        medecin_id = (Integer) args.get("id");
        Medecin medecin = new Medecin(medecin_id);

        genre_f.getItems().setAll("M", "F");
        genre_f.setValue(medecin.getGenre() + "");
        username_f.setText(medecin.getUsername());
        password_f.setText(medecin.getPassword());
        nom_f.setText(medecin.getNom());
        prenom_f.setText(medecin.getPrenom());
        adresse_f.setText(medecin.getAddress());
        telephone_f.setText(medecin.getTelephone());
        genre_f.setValue(medecin.getGenre());
        birthdate_f.setValue(medecin.getDateDeNaissance().toLocalDate());
    }

    public void save() {
        String username = username_f.getText();
        String password = password_f.getText();
        String nom = nom_f.getText();
        String prenom = prenom_f.getText();
        String adresse = adresse_f.getText();
        String telephone = telephone_f.getText();
        String genre = "" + genre_f.getValue();
        LocalDate birthdate = birthdate_f.getValue();

        Medecin medecin = new Medecin(medecin_id);


        if (username == null || username.equals("")) {
            alert("Le nom d'utlisateur est vide");
            return;
        }

        if ((new Medecin(username)).getId() > 0 && !username.equals(medecin.getUsername())) {
            alert("Le nom d'utilisateur existe deja");
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
            alert("Le telphone est vide");
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


        medecin = new Medecin(medecin_id, medecin.getId_personne(), username, password, true, nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
        medecin.save();

        Personne person = new Personne(medecin.getId_personne(), nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
        person.save();

        main_window.update();
    }

    private void alert(String msg) {
        alert_lab.setText(msg);
        alert_lab.setVisible(true);
    }
}
