package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import core.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Medecin;
import models.Personne;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

public class EditAccountController extends Controller {
    private int medecin_id;
    private ManageAccountsController main_window = null;

    @FXML
    JFXTextField username_f;

    @FXML
    JFXTextField password_f;

    @FXML
    JFXTextField nom_f;

    @FXML
    JFXTextField prenom_f;

    @FXML
    JFXTextField adresse_f;

    @FXML
    JFXTextField telephone_f;

    @FXML
    JFXComboBox genre_f;

    @FXML
    JFXDatePicker birthdate_f;

    @FXML
    Label alert_lab;

    public EditAccountController() {
        title = "Modifier Compte";
        min_width = width = max_width = 400;
        min_height = height = max_height = 560;
    }

    public void init(HashMap args) {
        if (args.containsKey("main_window"))
        main_window = (ManageAccountsController) args.get("main_window");
        boolean modify = (boolean) args.get("modify");

        genre_f.getItems().setAll("M", "F");
        genre_f.setValue("M");

        if (modify) {
            medecin_id = (Integer) args.get("id");
            Medecin medecin = new Medecin(medecin_id);
            genre_f.setValue(medecin.getGenre() + "");
            username_f.setText(medecin.getUsername());
            password_f.setText(medecin.getPassword());
            nom_f.setText(medecin.getNom());
            prenom_f.setText(medecin.getPrenom());
            adresse_f.setText(medecin.getAddress());
            telephone_f.setText(medecin.getTelephone());
            genre_f.setValue(medecin.getGenre());
            birthdate_f.setValue(medecin.getDateDeNaissance().toLocalDate());

            if (!Auth.isAdmin()) {
                genre_f.setDisable(true);
                nom_f.setDisable(true);
                prenom_f.setDisable(true);
                adresse_f.setDisable(true);
                telephone_f.setDisable(true);
                birthdate_f.setDisable(true);
            }
        }
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

        Medecin medecin;
        if (medecin_id !=0) {
             medecin = new Medecin(medecin_id);
        }

        else {
            medecin = new Medecin();
        }


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


        if (medecin_id != 0) {
            medecin = new Medecin(medecin_id, medecin.getId_personne(), username, password, true, nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
            medecin.save();

            Personne person = new Personne(medecin.getId_personne(), nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
            person.save();
        }

        else {
            Personne person = new Personne(0, nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
            person.save();

            medecin = new Medecin(0, person.getId(), username, password, true, nom, prenom, adresse, telephone, genre.charAt(0), Date.valueOf(birthdate));
            medecin.save();

        }

        if (main_window != null)
            main_window.update();
        getWindow().close();
    }

    private void alert(String msg) {
        alert_lab.setText(msg);
        alert_lab.setVisible(true);
    }
}
