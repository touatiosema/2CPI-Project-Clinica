package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Medecin;
import models.Personne;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

public class ShowAccountController extends Controller {
    private int medecin_id;
    private ManageAccountsController main_window;

    @FXML
    TextField username_f;

    @FXML
    TextField password_f;

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

    public ShowAccountController() {
        title = "Compte";
        min_width = width = max_width = 350;
        min_height = height = max_height = 350;
    }

    public void init(HashMap args) {
        main_window = (ManageAccountsController) args.get("main_window");
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

    public void edit() {
        App.setView(getWindow(), "EditAccount", new HashMap() {{
            put("main_window", main_window);
            put("id", medecin_id);
        }});
    }
}
